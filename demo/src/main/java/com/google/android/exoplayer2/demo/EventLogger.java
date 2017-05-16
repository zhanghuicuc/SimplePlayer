/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.exoplayer2.demo;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;
import android.view.Surface;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataRenderer;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.GeobFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.id3.UrlLinkFrame;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import org.xmlpull.v1.XmlSerializer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Logs player events using {@link Log}.
 */
/* package */ final class EventLogger implements ExoPlayer.EventListener,
    AudioRendererEventListener, VideoRendererEventListener, AdaptiveMediaSourceEventListener,
    ExtractorMediaSource.EventListener, DefaultDrmSessionManager.EventListener,
    MetadataRenderer.Output {

  private static final String TAG = "EventLogger";
  private static final int MAX_TIMELINE_ITEM_LINES = 3;
  private static final NumberFormat TIME_FORMAT;

  private static final float q1 = 0.774f;
  private static final float q2 = 1.102f;
  private static final float q3 = 0.555f;

  private static final int TIME_OUT = 10*10000000;   //超时时间
  private static final String CHARSET = "utf-8"; //设置编码
  public static final String SUCCESS="1";
  public static final String FAILURE="0";

  public static final String ReportFilePath="sdcard/Compliance-test/";
  public static final String BufferReportFile="sdcard/Compliance-test/report_buffer.xml";
  public static final String FormatReportFile="sdcard/Compliance-test/report_format.xml";
  public static final String RequestURL = "/DASH/UploadToServer.php";

  static {
    TIME_FORMAT = NumberFormat.getInstance(Locale.US);
    TIME_FORMAT.setMinimumFractionDigits(2);
    TIME_FORMAT.setMaximumFractionDigits(2);
    TIME_FORMAT.setGroupingUsed(false);
  }

  private final MappingTrackSelector trackSelector;
  private final Timeline.Window window;
  private final Timeline.Period period;
  private final long startTimeMs;

  private String testDate;
  private String versionInfo;
  private TestReport Report;
  private String serverIp;
  private List<Float> selectedQoEs;
  private Float rebufferTime;
  private Float rebufferFreq;
  private Float rebufferStartTime = 0.0f;
  private Float rebufferEndTime = 0.0f;

  public EventLogger(MappingTrackSelector trackSelector) {
    this.trackSelector = trackSelector;
    window = new Timeline.Window();
    period = new Timeline.Period();
    startTimeMs = SystemClock.elapsedRealtime();
    testDate = new Date().toString();
    selectedQoEs = new ArrayList<>();
    rebufferTime = 0.0f;
    rebufferFreq = 0.0f;
  }

  public void setSampleNameAndAdaptAlgo(String sampleName, String adaptAlgo) {
    Report = new TestReport(sampleName, adaptAlgo);
  }

  public void setVersionInfo(String versionInfo) {
    this.versionInfo = versionInfo;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  // ExoPlayer.EventListener

  @Override
  public void onLoadingChanged(boolean isLoading) {
    Log.d(TAG, "loading [" + isLoading + "]");
  }

  @Override
  public void onPlayerStateChanged(boolean playWhenReady, int state) {
    String sessionTime = getSessionTimeString();
    if (state == ExoPlayer.STATE_BUFFERING ) {
      rebufferStartTime = Float.parseFloat(sessionTime);
      rebufferFreq++;
    } else if (state == ExoPlayer.STATE_READY ){
      rebufferEndTime = Float.parseFloat(sessionTime);
      rebufferTime += ( rebufferEndTime - rebufferStartTime );
    }
    Log.d(TAG, "state [" + sessionTime + ", " + playWhenReady + ", "
        + getStateString(state) + "]");
  }

  @Override
  public void onPositionDiscontinuity() {
    Log.d(TAG, "positionDiscontinuity");
  }

  @Override
  public void onTimelineChanged(Timeline timeline, Object manifest) {
    if (timeline == null) {
      return;
    }
    int periodCount = timeline.getPeriodCount();
    int windowCount = timeline.getWindowCount();
    Log.d(TAG, "sourceInfo [periodCount=" + periodCount + ", windowCount=" + windowCount);
    for (int i = 0; i < Math.min(periodCount, MAX_TIMELINE_ITEM_LINES); i++) {
      timeline.getPeriod(i, period);
      Log.d(TAG, "  " +  "period [" + getTimeString(period.getDurationMs()) + "]");
    }
    if (periodCount > MAX_TIMELINE_ITEM_LINES) {
      Log.d(TAG, "  ...");
    }
    for (int i = 0; i < Math.min(windowCount, MAX_TIMELINE_ITEM_LINES); i++) {
      timeline.getWindow(i, window);
      Log.d(TAG, "  " +  "window [" + getTimeString(window.getDurationMs()) + ", "
          + window.isSeekable + ", " + window.isDynamic + "]");
    }
    if (windowCount > MAX_TIMELINE_ITEM_LINES) {
      Log.d(TAG, "  ...");
    }
    Log.d(TAG, "]");
  }

  @Override
  public void onPlayerError(ExoPlaybackException e) {
    Log.e(TAG, "playerFailed [" + getSessionTimeString() + "]", e);
  }

  @Override
  public void onTracksChanged(TrackGroupArray ignored, TrackSelectionArray trackSelections) {
    MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
    if (mappedTrackInfo == null) {
      Log.d(TAG, "Tracks []");
      return;
    }
    Log.d(TAG, "Tracks [");
    // Log tracks associated to renderers.
    for (int rendererIndex = 0; rendererIndex < mappedTrackInfo.length; rendererIndex++) {
      TrackGroupArray rendererTrackGroups = mappedTrackInfo.getTrackGroups(rendererIndex);
      TrackSelection trackSelection = trackSelections.get(rendererIndex);
      if (rendererTrackGroups.length > 0) {
        Log.d(TAG, "  Renderer:" + rendererIndex + " [");
        for (int groupIndex = 0; groupIndex < rendererTrackGroups.length; groupIndex++) {
          TrackGroup trackGroup = rendererTrackGroups.get(groupIndex);
          String adaptiveSupport = getAdaptiveSupportString(trackGroup.length,
              mappedTrackInfo.getAdaptiveSupport(rendererIndex, groupIndex, false));
          Log.d(TAG, "    Group:" + groupIndex + ", adaptive_supported=" + adaptiveSupport + " [");
          for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
            String status = getTrackStatusString(trackSelection, trackGroup, trackIndex);
            String formatSupport = getFormatSupportString(
                mappedTrackInfo.getTrackFormatSupport(rendererIndex, groupIndex, trackIndex));
            Log.d(TAG, "      " + status + " Track:" + trackIndex + ", "
                + Format.toLogString(trackGroup.getFormat(trackIndex))
                + ", supported=" + formatSupport);
          }
          Log.d(TAG, "    ]");
        }
        // Log metadata for at most one of the tracks selected for the renderer.
        if (trackSelection != null) {
          for (int selectionIndex = 0; selectionIndex < trackSelection.length(); selectionIndex++) {
            Metadata metadata = trackSelection.getFormat(selectionIndex).metadata;
            if (metadata != null) {
              Log.d(TAG, "    Metadata [");
              printMetadata(metadata, "      ");
              Log.d(TAG, "    ]");
              break;
            }
          }
        }
        Log.d(TAG, "  ]");
      }
    }
    // Log tracks not associated with a renderer.
    TrackGroupArray unassociatedTrackGroups = mappedTrackInfo.getUnassociatedTrackGroups();
    if (unassociatedTrackGroups.length > 0) {
      Log.d(TAG, "  Renderer:None [");
      for (int groupIndex = 0; groupIndex < unassociatedTrackGroups.length; groupIndex++) {
        Log.d(TAG, "    Group:" + groupIndex + " [");
        TrackGroup trackGroup = unassociatedTrackGroups.get(groupIndex);
        for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
          String status = getTrackStatusString(false);
          String formatSupport = getFormatSupportString(
              RendererCapabilities.FORMAT_UNSUPPORTED_TYPE);
          Log.d(TAG, "      " + status + " Track:" + trackIndex + ", "
              + Format.toLogString(trackGroup.getFormat(trackIndex))
              + ", supported=" + formatSupport);
        }
        Log.d(TAG, "    ]");
      }
      Log.d(TAG, "  ]");
    }
    Log.d(TAG, "]");
  }

  // MetadataRenderer.Output

  @Override
  public void onMetadata(Metadata metadata) {
    Log.d(TAG, "onMetadata [");
    printMetadata(metadata, "  ");
    Log.d(TAG, "]");
  }

  // AudioRendererEventListener

  @Override
  public void onAudioEnabled(DecoderCounters counters) {
    Log.d(TAG, "audioEnabled [" + getSessionTimeString() + "]");
  }

  @Override
  public void onAudioSessionId(int audioSessionId) {
    Log.d(TAG, "audioSessionId [" + audioSessionId + "]");
  }

  @Override
  public void onAudioDecoderInitialized(String decoderName, long elapsedRealtimeMs,
      long initializationDurationMs) {
    Log.d(TAG, "audioDecoderInitialized [" + getSessionTimeString() + ", " + decoderName + "]");
  }

  @Override
  public void onAudioInputFormatChanged(Format format) {
    Log.d(TAG, "audioFormatChanged [" + getSessionTimeString() + ", " + Format.toLogString(format)
        + "]");
  }

  @Override
  public void onAudioDisabled(DecoderCounters counters) {
    Log.d(TAG, "audioDisabled [" + getSessionTimeString() + "]");
  }

  @Override
  public void onAudioTrackUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
    printInternalError("audioTrackUnderrun [" + bufferSize + ", " + bufferSizeMs + ", "
        + elapsedSinceLastFeedMs + "]", null);
  }

  // VideoRendererEventListener

  @Override
  public void onVideoEnabled(DecoderCounters counters) {
    Log.d(TAG, "videoEnabled [" + getSessionTimeString() + "]");
  }

  @Override
  public void onVideoDecoderInitialized(String decoderName, long elapsedRealtimeMs,
      long initializationDurationMs) {
    Log.d(TAG, "videoDecoderInitialized [" + getSessionTimeString() + ", " + decoderName + "]");
  }

  @Override
  public void onVideoInputFormatChanged(Format format) {
    String sessionTime = getSessionTimeString();
    Log.d(TAG, "videoFormatChanged [" + sessionTime + ", " + Format.toLogString(format)
            + "]");
  }

  @Override
  public void onVideoDisabled(DecoderCounters counters) {
    Log.d(TAG, "videoDisabled [" + getSessionTimeString() + "]");
    Double overallQoE = 0.0;
    Float rebufferCycle = period.getDurationMs() / 30000.0f;
    for(int i = 0; i < selectedQoEs.size(); i++) {
      overallQoE += selectedQoEs.get(i);
    }
    overallQoE = overallQoE / selectedQoEs.size();
    rebufferTime = rebufferTime / rebufferCycle;
    rebufferFreq = rebufferFreq / rebufferCycle;
    double dQoEreBuffering = q1 * Math.log( rebufferTime ) + q2 * Math.log( rebufferFreq ) + q3;
    if (dQoEreBuffering < 0) {
      dQoEreBuffering = 0.0f;
    }
    overallQoE = overallQoE - dQoEreBuffering;
    DecimalFormat df = new DecimalFormat("#.##");
    Report.QoE = df.format(overallQoE);

    writeBufferReportToXmlFile();
    new AsyncHttpPostTask("http://" + serverIp + RequestURL).execute(BufferReportFile);
    writeFormatReportToXmlFile();
    new AsyncHttpPostTask("http://" + serverIp + RequestURL).execute(FormatReportFile);

  }

  @Override
  public void onDroppedFrames(int count, long elapsed) {
    Log.d(TAG, "droppedFrames [" + getSessionTimeString() + ", " + count + "]");
  }

  @Override
  public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
      float pixelWidthHeightRatio) {
    // Do nothing.
  }

  @Override
  public void onRenderedFirstFrame(Surface surface) {
    // Do nothing.
  }

  // DefaultDrmSessionManager.EventListener

  @Override
  public void onDrmSessionManagerError(Exception e) {
    printInternalError("drmSessionManagerError", e);
  }

  @Override
  public void onDrmKeysRestored() {
    Log.d(TAG, "drmKeysRestored [" + getSessionTimeString() + "]");
  }

  @Override
  public void onDrmKeysRemoved() {
    Log.d(TAG, "drmKeysRemoved [" + getSessionTimeString() + "]");
  }

  @Override
  public void onDrmKeysLoaded() {
    Log.d(TAG, "drmKeysLoaded [" + getSessionTimeString() + "]");
  }

  // ExtractorMediaSource.EventListener

  @Override
  public void onLoadError(IOException error) {
    printInternalError("loadError", error);
  }

  // AdaptiveMediaSourceEventListener

  @Override
  public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
      int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
      long mediaEndTimeMs, long elapsedRealtimeMs) {
    // Do nothing.
  }

  @Override
  public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
      int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
      long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded,
      IOException error, boolean wasCanceled) {
    printInternalError("loadError", error);
  }

  @Override
  public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
      int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
      long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
    // Do nothing.
  }

  @Override
  public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
      int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
      long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
    // Do nothing.
    String sessionTime = getSessionTimeString();

    Map<String, String> selectionData = (Map<String, String>) trackSelectionData;
    if(selectionData != null) {
      if(selectionData.containsKey("selectedQoE")){
        Float selectedQoE = Float.parseFloat(selectionData.get("selectedQoE"));
        selectedQoEs.add(selectedQoE);
      }

      if (selectionData.containsKey("selectedBitrate")) {
        Integer bitrate = Integer.parseInt(selectionData.get("selectedBitrate"));
        String timeAndFormat = sessionTime + "," + String.valueOf(bitrate / 1000.0f);
        Report.formatChanges.add(timeAndFormat);
      }

      if (selectionData.containsKey("bufferedDurationUs")) {
        Long bufferDurationUs = Long.parseLong(selectionData.get("bufferedDurationUs"));
        String timeAndBuffer = sessionTime + "," + String.valueOf(bufferDurationUs / C.MICROS_PER_SECOND);
        Report.bufferDurations.add(timeAndBuffer);
      }

      float bitsPerSecond = bytesLoaded * 8000 / loadDurationMs;
      String timeAndBandwidth = sessionTime + "," + String.valueOf(bitsPerSecond / 1000.0f);
      Report.bandWidths.add(timeAndBandwidth);
    }
  }

  @Override
  public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {
    // Do nothing.
  }

  @Override
  public void onDownstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason,
      Object trackSelectionData, long mediaTimeMs) {
    // Do nothing.
  }

  // Internal methods

  private void printInternalError(String type, Exception e) {
    Log.e(TAG, "internalError [" + getSessionTimeString() + ", " + type + "]", e);
  }

  private void printMetadata(Metadata metadata, String prefix) {
    for (int i = 0; i < metadata.length(); i++) {
      Metadata.Entry entry = metadata.get(i);
      if (entry instanceof TextInformationFrame) {
        TextInformationFrame textInformationFrame = (TextInformationFrame) entry;
        Log.d(TAG, prefix + String.format("%s: value=%s", textInformationFrame.id,
            textInformationFrame.value));
      } else if (entry instanceof UrlLinkFrame) {
        UrlLinkFrame urlLinkFrame = (UrlLinkFrame) entry;
        Log.d(TAG, prefix + String.format("%s: url=%s", urlLinkFrame.id, urlLinkFrame.url));
      } else if (entry instanceof PrivFrame) {
        PrivFrame privFrame = (PrivFrame) entry;
        Log.d(TAG, prefix + String.format("%s: owner=%s", privFrame.id, privFrame.owner));
      } else if (entry instanceof GeobFrame) {
        GeobFrame geobFrame = (GeobFrame) entry;
        Log.d(TAG, prefix + String.format("%s: mimeType=%s, filename=%s, description=%s",
            geobFrame.id, geobFrame.mimeType, geobFrame.filename, geobFrame.description));
      } else if (entry instanceof ApicFrame) {
        ApicFrame apicFrame = (ApicFrame) entry;
        Log.d(TAG, prefix + String.format("%s: mimeType=%s, description=%s",
            apicFrame.id, apicFrame.mimeType, apicFrame.description));
      } else if (entry instanceof CommentFrame) {
        CommentFrame commentFrame = (CommentFrame) entry;
        Log.d(TAG, prefix + String.format("%s: language=%s, description=%s", commentFrame.id,
            commentFrame.language, commentFrame.description));
      } else if (entry instanceof Id3Frame) {
        Id3Frame id3Frame = (Id3Frame) entry;
        Log.d(TAG, prefix + String.format("%s", id3Frame.id));
      } else if (entry instanceof EventMessage) {
        EventMessage eventMessage = (EventMessage) entry;
        Log.d(TAG, prefix + String.format("EMSG: scheme=%s, id=%d, value=%s",
            eventMessage.schemeIdUri, eventMessage.id, eventMessage.value));
      }
    }
  }

  private String getSessionTimeString() {
    return getTimeString(SystemClock.elapsedRealtime() - startTimeMs);
  }

  private static String getTimeString(long timeMs) {
    return timeMs == C.TIME_UNSET ? "?" : TIME_FORMAT.format((timeMs) / 1000f);
  }

  private static String getStateString(int state) {
    switch (state) {
      case ExoPlayer.STATE_BUFFERING:
        return "B";
      case ExoPlayer.STATE_ENDED:
        return "E";
      case ExoPlayer.STATE_IDLE:
        return "I";
      case ExoPlayer.STATE_READY:
        return "R";
      default:
        return "?";
    }
  }

  private static String getFormatSupportString(int formatSupport) {
    switch (formatSupport) {
      case RendererCapabilities.FORMAT_HANDLED:
        return "YES";
      case RendererCapabilities.FORMAT_EXCEEDS_CAPABILITIES:
        return "NO_EXCEEDS_CAPABILITIES";
      case RendererCapabilities.FORMAT_UNSUPPORTED_SUBTYPE:
        return "NO_UNSUPPORTED_TYPE";
      case RendererCapabilities.FORMAT_UNSUPPORTED_TYPE:
        return "NO";
      default:
        return "?";
    }
  }

  private static String getAdaptiveSupportString(int trackCount, int adaptiveSupport) {
    if (trackCount < 2) {
      return "N/A";
    }
    switch (adaptiveSupport) {
      case RendererCapabilities.ADAPTIVE_SEAMLESS:
        return "YES";
      case RendererCapabilities.ADAPTIVE_NOT_SEAMLESS:
        return "YES_NOT_SEAMLESS";
      case RendererCapabilities.ADAPTIVE_NOT_SUPPORTED:
        return "NO";
      default:
        return "?";
    }
  }

  private static String getTrackStatusString(TrackSelection selection, TrackGroup group,
      int trackIndex) {
    return getTrackStatusString(selection != null && selection.getTrackGroup() == group
        && selection.indexOf(trackIndex) != C.INDEX_UNSET);
  }

  private static String getTrackStatusString(boolean enabled) {
    return enabled ? "[X]" : "[ ]";
  }

  private static class TestReport implements Parcelable {
    public String sampleId;
    public String adaptAlgo;
    public String QoE;
    public ArrayList<String> formatChanges;
    public ArrayList<String> bufferDurations; // in second
    public ArrayList<String> bandWidths;
    public TestReport(String sampleId, String adaptAlgo){
      this.sampleId = sampleId;
      this.adaptAlgo = adaptAlgo;
      formatChanges = new ArrayList<>();
      bufferDurations = new ArrayList<>();
      bandWidths = new ArrayList<>();
    }

    public int describeContents(){
      return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
      dest.writeString(sampleId);
      dest.writeString(adaptAlgo);
      dest.writeString(QoE);
      dest.writeInt(formatChanges.size());
      String[] formatchanges = new String[formatChanges.size()];
      for(int i = 0; i < formatChanges.size(); i++)
        formatchanges[i] = formatChanges.get(i);
      dest.writeStringArray(formatchanges);

      dest.writeInt(bufferDurations.size());
      String[] bufferdurations = new String[bufferDurations.size()];
      for(int i = 0; i < bufferDurations.size(); i++)
        bufferdurations[i] = bufferDurations.get(i);
      dest.writeStringArray(bufferdurations);

      dest.writeInt(bandWidths.size());
      String[] bandwidths = new String[bandWidths.size()];
      for(int i = 0; i < bandWidths.size(); i++)
        bandwidths[i] = bandWidths.get(i);
      dest.writeStringArray(bandwidths);
    }

    public static final Parcelable.Creator<TestReport> CREATOR=new Parcelable.Creator<TestReport>() {

      public TestReport createFromParcel(Parcel source){
        String sampleId = source.readString();
        String adaptAlgo =  source.readString();
        String QoE = source.readString();
        TestReport testReport=new TestReport(sampleId, adaptAlgo);

        int formatChangesize = source.readInt();
        String[] formatchanges = new String[formatChangesize];
        source.readStringArray(formatchanges);
        testReport.formatChanges = new ArrayList<String>(formatChangesize);
        for(String s:formatchanges)
          testReport.formatChanges.add(s);

        int bufferDurationsize = source.readInt();
        String[] bufferdurations = new String[bufferDurationsize];
        source.readStringArray(bufferdurations);
        testReport.bufferDurations = new ArrayList<String>(bufferDurationsize);
        for(String s:bufferdurations)
          testReport.bufferDurations.add(s);

        int bandWidthsize = source.readInt();
        String[] bandwidths = new String[bandWidthsize];
        source.readStringArray(bandwidths);
        testReport.bandWidths = new ArrayList<String>(bandWidthsize);
        for(String s:bandwidths)
          testReport.bandWidths.add(s);

        return testReport;
      }


      public TestReport[] newArray(int size){
        return new TestReport[size];
      }
    };
  }

  public boolean writeFormatReportToXmlFile() {
    // write report_foramt.xml and report_buffer.xml separately
    FileOutputStream fos = null;
    File file=new File(ReportFilePath);
    try {
      file = new File(FormatReportFile);
      if (!file.createNewFile()) {
        Log.d(TAG, "file already exist!");
        file.delete();
        if (!file.createNewFile()) {
          Log.d(TAG, "Create file failed!");
          return false;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      Log.d(TAG, "create xml file failed!");
    }
    try {
      fos = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    }
    XmlSerializer serializer = Xml.newSerializer();
    try {
      serializer.setOutput(fos, "UTF-8");

      serializer.setFeature(
              "http://xmlpull.org/v1/doc/features.html#indent-output",
              true);
      serializer.startDocument("UTF-8", true);
      serializer.startTag(null, "JSChart");

      serializer.attribute(null, "testDate", testDate.toString());
      serializer.attribute(null, "sysInfo", versionInfo);
      serializer.attribute(null, "smapleID", Report.sampleId);
      serializer.attribute(null, "adaptAlgo", Report.adaptAlgo);

      serializer.startTag(null, "dataset");
      serializer.attribute(null, "type", "line");
      serializer.attribute(null, "id", "formatChanges");
      if(Report.formatChanges != null) {
        for (int k = 0; k < Report.formatChanges.size(); k++) {
          String[] timeAndFormat = Report.formatChanges.get(k).split(",");
          serializer.startTag(null, "data");
          serializer.attribute(null, "unit", timeAndFormat[0]);
          serializer.attribute(null, "value", timeAndFormat[1]);
          serializer.endTag(null, "data");
        }
      }
      serializer.endTag(null, "dataset");

      serializer.startTag(null, "dataset");
      serializer.attribute(null, "type", "line");
      serializer.attribute(null, "id", "bandWidth");
      if(Report.bandWidths != null) {
        for (int k = 0; k < Report.bandWidths.size(); k++) {
          String[] timeAndBandwidth = Report.bandWidths.get(k).split(",");
          serializer.startTag(null, "data");
          serializer.attribute(null, "unit", timeAndBandwidth[0]);
          serializer.attribute(null, "value", timeAndBandwidth[1]);
          serializer.endTag(null, "data");
        }
      }
      serializer.endTag(null, "dataset");

      serializer.startTag(null, "optionset");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setSize");
      serializer.attribute(null, "value", "600, 300");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setTitle");
      serializer.attribute(null, "value", "\'" + Report.sampleId + " - " +
              Report.adaptAlgo + " - QoE: " + Report.QoE  + " - " + testDate + "\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setAxisNameX");
      serializer.attribute(null, "value", "\'Time(s)\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setAxisNameY");
      serializer.attribute(null, "value", "\'bitrate(kbps)\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setIntervalStartX");
      serializer.attribute(null, "value", "0");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setLegendShow");
      serializer.attribute(null, "value", "true");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setLegendPosition");
      serializer.attribute(null, "value", "\'right top\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setLegendForLine");
      serializer.attribute(null, "value", "\'formatChanges\', \'selectedBr\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setLegendForLine");
      serializer.attribute(null, "value", "\'bandWidth\', \'netBW\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setLegendPadding");
      serializer.attribute(null, "value", "20");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setLineColor");
      serializer.attribute(null, "value", "\'#A4D314\', \'bandWidth\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setAxisPaddingLeft");
      serializer.attribute(null, "value", "60");
      serializer.endTag(null, "option");

      serializer.endTag(null, "optionset");

      serializer.endTag(null, "JSChart");
      serializer.endDocument();

      fos.flush();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      Log.d(TAG, "IOException write xml file failed!");
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      Log.d(TAG, "Exception write xml file failed!");
      return false;
    }
    return true;
  }

  public boolean writeBufferReportToXmlFile() {
    // write report_foramt.xml and report_buffer.xml separately
    FileOutputStream fos = null;
    File file=new File(ReportFilePath);
    try {
      file = new File(BufferReportFile);
      if (!file.createNewFile()) {
        Log.d(TAG, "file already exist!");
        file.delete();
        if (!file.createNewFile()) {
          Log.d(TAG, "Create file failed!");
          return false;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      Log.d(TAG, "create xml file failed!");
    }
    try {
      fos = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    }
    XmlSerializer serializer = Xml.newSerializer();
    try {
      serializer.setOutput(fos, "UTF-8");

      serializer.setFeature(
              "http://xmlpull.org/v1/doc/features.html#indent-output",
              true);
      serializer.startDocument("UTF-8", true);
      serializer.startTag(null, "JSChart");

      serializer.attribute(null, "testDate", testDate.toString());
      serializer.attribute(null, "sysInfo", versionInfo);
      serializer.attribute(null, "smapleID", Report.sampleId);
      serializer.attribute(null, "adaptAlgo", Report.adaptAlgo);

      serializer.startTag(null, "dataset");
      serializer.attribute(null, "type", "line");
      serializer.attribute(null, "id", "bufferDuration");
      if(Report.bufferDurations != null) {
        for (int k = 0; k < Report.bufferDurations.size(); k++) {
          String[] timeAndBuffer = Report.bufferDurations.get(k).split(",");
          serializer.startTag(null, "data");
          serializer.attribute(null, "unit", timeAndBuffer[0]);
          serializer.attribute(null, "value", timeAndBuffer[1]);
          serializer.endTag(null, "data");
        }
      }
      serializer.endTag(null, "dataset");

      serializer.startTag(null, "optionset");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setSize");
      serializer.attribute(null, "value", "550, 300");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setTitle");
      serializer.attribute(null, "value", "\'" + Report.sampleId + " - " +
              Report.adaptAlgo + " - QoE: " + Report.QoE + " - " + testDate + "\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setAxisNameX");
      serializer.attribute(null, "value", "\'Time(s)\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setAxisNameY");
      serializer.attribute(null, "value", "\'BufSz(s)\'");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setIntervalStartX");
      serializer.attribute(null, "value", "0");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setIntervalStartY");
      serializer.attribute(null, "value", "0");
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setIntervalEndY");
      serializer.attribute(null, "value", getTimeString(DefaultLoadControl.DEFAULT_MAX_BUFFER_MS));
      serializer.endTag(null, "option");

      serializer.startTag(null, "option");
      serializer.attribute(null, "set", "setAxisPaddingLeft");
      serializer.attribute(null, "value", "50");
      serializer.endTag(null, "option");

      serializer.endTag(null, "optionset");

      serializer.endTag(null, "JSChart");
      serializer.endDocument();

      fos.flush();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      Log.d(TAG, "IOException write xml file failed!");
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      Log.d(TAG, "Exception write xml file failed!");
      return false;
    }
    return true;
  }

  /**
   * android上传文件到服务器
   * @param filename  需要上传的文件
   * @param RequestURL  请求的rul
   * @return  返回响应的内容
   */
  public static String uploadFile(String filename,String RequestURL)
  {
    String fileName = filename;

    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;
    File sourceFile = new File(filename);

    if (!sourceFile.isFile()) {
      Log.e(TAG, "Source File not exist");
      return FAILURE;
    }
    else
    {
      try {
        // open a URL connection to the Servlet
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        URL url = new URL(RequestURL);

        // Open a HTTP  connection to  the URL
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true); // Allow Inputs
        conn.setDoOutput(true); // Allow Outputs
        conn.setUseCaches(false); // Don't use a Cached Copy
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        conn.setRequestProperty("uploaded_file", fileName);

        dos = new DataOutputStream(conn.getOutputStream());

        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\""
                + fileName + "\"" + lineEnd);
        dos.writeBytes(lineEnd);

        // create a buffer of  maximum size
        bytesAvailable = fileInputStream.available();

        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

        // read file and write it into form...
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {

          dos.write(buffer, 0, bufferSize);
          bytesAvailable = fileInputStream.available();
          bufferSize = Math.min(bytesAvailable, maxBufferSize);
          bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        }

        // send multipart form data necesssary after file data...
        dos.writeBytes(lineEnd);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        // Responses from the server (code and message)
        int serverResponseCode = conn.getResponseCode();
        String serverResponseMessage = conn.getResponseMessage();

        Log.i(TAG, "HTTP Response is : "
                + serverResponseMessage + ": " + serverResponseCode);

        if(serverResponseCode == 200){
          return  SUCCESS;
        }

        //close the streams //
        fileInputStream.close();
        dos.flush();
        dos.close();

      } catch (MalformedURLException ex) {
        ex.printStackTrace();
        Log.e(TAG, "Upload file to server error: " + ex.getMessage(), ex);
      } catch (Exception e) {
        e.printStackTrace();
        Log.e(TAG, "Upload file to server Exception : "
                + e.getMessage(), e);
      }
      return FAILURE;
    } // End else block
}

  public class AsyncHttpPostTask extends AsyncTask<String, Void, String> {

    private String server;
    public AsyncHttpPostTask(final String server) {
      this.server = server;
    }

    @Override
    protected String doInBackground(String... params) {
      return uploadFile(params[0], server);
    }
  }
}
