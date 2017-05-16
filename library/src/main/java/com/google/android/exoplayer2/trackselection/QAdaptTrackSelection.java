package com.google.android.exoplayer2.trackselection;

/**
 * Created by zhanghui on 2017/3/25.
 */

import android.os.SystemClock;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.upstream.BandwidthMeter;

import java.util.HashMap;
import java.util.Map;

/**
 * A QoE driven adaptive {@link TrackSelection} for video, whose selected track is updated to
 * be the one which can bring best QoE given the current network conditions and the state of the buffer.
 */
public class QAdaptTrackSelection extends BaseTrackSelection{
    public static final String TAG = "QAdaptTrackSelection";
    /**
     * Factory for {@link QAdaptTrackSelection} instances.
     */
    public static final class Factory implements TrackSelection.Factory {

        private final BandwidthMeter bandwidthMeter;
        private final int maxInitialBitrate;
        private final int minBufferDurationMs;
        private final float bandwidthFraction;
        private final float sigmaFractionUsedWhenPanic;

        /**
         * @param bandwidthMeter Provides an estimate of the currently available bandwidth.
         */
        public Factory(BandwidthMeter bandwidthMeter) {
            this (bandwidthMeter, DEFAULT_MAX_INITIAL_BITRATE,
                    DEFAULT_MIN_BUFFER_DURATION_MS, DEFAULT_BANDWIDTH_FRACTION,
                    DEFAULT_SIGMA_FRACTION_USED_WHEN_PANIC);
        }

        /**
         * @param bandwidthMeter Provides an estimate of the currently available bandwidth.
         */
        public Factory(BandwidthMeter bandwidthMeter, int minBufferDurationMs) {
            this (bandwidthMeter, DEFAULT_MAX_INITIAL_BITRATE,
                    minBufferDurationMs, DEFAULT_BANDWIDTH_FRACTION,
                    DEFAULT_SIGMA_FRACTION_USED_WHEN_PANIC);
        }

        /**
         * @param bandwidthMeter Provides an estimate of the currently available bandwidth.
         * @param maxInitialBitrate The maximum bitrate in bits per second that should be assumed
         *     when a bandwidth estimate is unavailable.
         * @param minBufferDurationMs The minimum duration of buffered data required for
         *     avoiding underflow.
         * @param bandwidthFraction The fraction of the available bandwidth that the selection should
         *     consider available for use. Setting to a value less than 1 is recommended to account
         *     for inaccuracies in the bandwidth estimator.
         * @param sigmaFractionUsedWhenPanic The fraction of the available bandwidth that the selection should
         *     consider available for use when buffer level is smaller than the duration of segment.
         *     Setting to a value less than 1 is recommended.
         */
        public Factory(BandwidthMeter bandwidthMeter, int maxInitialBitrate, int minBufferDurationMs,
                       float bandwidthFraction,float sigmaFractionUsedWhenPanic) {
            this.bandwidthMeter = bandwidthMeter;
            this.maxInitialBitrate = maxInitialBitrate;
            this.minBufferDurationMs = minBufferDurationMs;
            this.bandwidthFraction = bandwidthFraction;
            this.sigmaFractionUsedWhenPanic = sigmaFractionUsedWhenPanic;
        }

        @Override
        public QAdaptTrackSelection createTrackSelection(TrackGroup group, int... tracks) {
            return new QAdaptTrackSelection(group, tracks, bandwidthMeter, maxInitialBitrate, minBufferDurationMs,
                    bandwidthFraction, sigmaFractionUsedWhenPanic);
        }

    }

    public static final int DEFAULT_MAX_INITIAL_BITRATE = 800000;
    public static final int DEFAULT_MIN_BUFFER_DURATION_MS = 12000;
    public static final float DEFAULT_BANDWIDTH_FRACTION = 0.75f;
    public static final float DEFAULT_SIGMA_FRACTION_USED_WHEN_PANIC = 0.25f;

    private final BandwidthMeter bandwidthMeter;
    private final int maxInitialBitrate;
    private final long minBufferDurationUs;
    private final float bandwidthFraction;
    private final float sigmaFractionUsedWhenPanic;

    private int selectedIndex;
    private int reason;
    private boolean initBufferEnd = false;

    /**
     * @param group The {@link TrackGroup}. Must not be null.
     * @param tracks The indices of the selected tracks within the {@link TrackGroup}. Must not be
     *     null or empty. May be in any order.
     * @param bandwidthMeter Provides an estimate of the currently available bandwidth.
     */
    public QAdaptTrackSelection(TrackGroup group, int[] tracks,
                                       BandwidthMeter bandwidthMeter) {
        this (group, tracks, bandwidthMeter, DEFAULT_MAX_INITIAL_BITRATE, DEFAULT_MIN_BUFFER_DURATION_MS,
                DEFAULT_BANDWIDTH_FRACTION, DEFAULT_SIGMA_FRACTION_USED_WHEN_PANIC);
    }

    /**
     * @param group The {@link TrackGroup}. Must not be null.
     * @param tracks The indices of the selected tracks within the {@link TrackGroup}. Must not be
     *     null or empty. May be in any order.
     * @param bandwidthMeter Provides an estimate of the currently available bandwidth.
     * @param minBufferDurationMs The minimum duration of buffered data required for avoiding underflow
     * @param bandwidthFraction The fraction of the available bandwidth that the selection should
     *     consider available for use. Setting to a value less than 1 is recommended to account
     *     for inaccuracies in the bandwidth estimator.
     * @param sigmaFractionUsedWhenPanic The fraction of the available bandwidth that the selection should
     *     consider available for use when buffer level is smaller than the duration of segment.
     *     Setting to a value less than 1 is recommended.
     */
    public QAdaptTrackSelection(TrackGroup group, int[] tracks, BandwidthMeter bandwidthMeter,int maxInitialBitrate,
                                       long minBufferDurationMs, float bandwidthFraction, float sigmaFractionUsedWhenPanic) {
        super(group, tracks);
        this.bandwidthMeter = bandwidthMeter;
        this.maxInitialBitrate = maxInitialBitrate;
        this.minBufferDurationUs = minBufferDurationMs * 1000L;
        this.bandwidthFraction = bandwidthFraction;
        this.sigmaFractionUsedWhenPanic = sigmaFractionUsedWhenPanic;
        selectedIndex = determineInitSelectedIndex(Long.MIN_VALUE);
        selectedQoE = getQoEMedia(selectedIndex);
        selectionData.put("selectedBitrate", String.valueOf(getFormat(selectedIndex).bitrate));
        selectionData.put("selectedQoE", String.valueOf(selectedQoE));
        selectionData.put("bufferedDurationUs", "0");
        reason = C.SELECTION_REASON_INITIAL;
    }

    @Override
    public void updateSelectedTrack(long bufferedDurationUs) {
        long nowMs = SystemClock.elapsedRealtime();
        // Get the current and ideal selections.
        int currentSelectedIndex = selectedIndex;
        Format currentFormat = getSelectedFormat();
        if (!initBufferEnd){
            // Still in the init buffering process
            if (bufferedDurationUs < minBufferDurationUs) {
                selectedIndex = determineInitSelectedIndex(nowMs);
                selectedQoE = getQoEMedia(selectedIndex) -
                        DEFAULT_P1 * Math.abs(getFormat(selectedIndex).bitrate - currentFormat.bitrate) / getFormat(selectedIndex).bitrate;
            } else {
                initBufferEnd = true;
            }
        }
        if (initBufferEnd) {
            if (bufferedDurationUs < getSegDutationsUs(currentSelectedIndex)) {
                // Enter the panic mode
                selectedIndex = determinePanicSelectedIndex(nowMs);
                selectedQoE = getQoEMedia(selectedIndex) -
                        DEFAULT_P1 * Math.abs(getFormat(selectedIndex).bitrate - currentFormat.bitrate) / getFormat(selectedIndex).bitrate;
            } else {
                // Switch up to higher one level bitrate
                int currentSelectedIndexMinusOne = currentSelectedIndex - 1 < 0 ? 0 : currentSelectedIndex - 1;
                // Switch down to lower one level bitrate
                int currentSelectedIndexPlusOne = currentSelectedIndex + 1 == length ? currentSelectedIndex : currentSelectedIndex + 1;
                int qualifiedSelectedIndex = determineQualifiedSelectedIndex(nowMs, bufferedDurationUs);
                if (currentSelectedIndexPlusOne < qualifiedSelectedIndex) {
                    // Even swtich down to lower one level bitrate will cause buffer underflow
                    if (!isBlacklisted(currentSelectedIndexPlusOne, nowMs)) {
                        selectedIndex = currentSelectedIndexPlusOne;
                    } else {
                        selectedIndex = qualifiedSelectedIndex;
                    }
                    selectedQoE = getQoEMedia(selectedIndex) -
                            DEFAULT_P1 * Math.abs(getFormat(selectedIndex).bitrate - currentFormat.bitrate) / getFormat(selectedIndex).bitrate;
                } else {
                    Float qoeOfSwitchUp = Float.MIN_VALUE;
                    if (currentSelectedIndexMinusOne != currentSelectedIndex) {
                        Format switchUpFormat = getFormat(currentSelectedIndexMinusOne);
                        qoeOfSwitchUp = getQoEMedia(currentSelectedIndexMinusOne) -
                                DEFAULT_P1 * (switchUpFormat.bitrate - currentFormat.bitrate) / switchUpFormat.bitrate;
                    }
                    Float qoeOfSwitchDown = Float.MIN_VALUE;
                    if (currentSelectedIndexPlusOne != currentSelectedIndex) {
                        Format switchDownFormat = getFormat(currentSelectedIndexPlusOne);
                        qoeOfSwitchDown = getQoEMedia(currentSelectedIndexPlusOne) -
                                DEFAULT_P1 * (currentFormat.bitrate - switchDownFormat.bitrate) / switchDownFormat.bitrate;
                    }
                    Float currentQoE = getQoEMedia(currentSelectedIndex);
                    selectedQoE = Math.max(qoeOfSwitchDown, Math.max(qoeOfSwitchUp, currentQoE));
                    if (selectedQoE.equals(qoeOfSwitchDown)) {
                        selectedIndex = currentSelectedIndexPlusOne;
                    } else if (selectedQoE.equals(qoeOfSwitchUp)) {
                        selectedIndex = currentSelectedIndexMinusOne;
                    } else {
                        selectedIndex = currentSelectedIndex;
                    }
                }
            }
        }
        Log.d(TAG,"Selected bitrate is " + getFormat(selectedIndex).bitrate);
        // If we adapted, update the trigger.
        if (selectedIndex != currentSelectedIndex) {
            reason = C.SELECTION_REASON_ADAPTIVE;
        }
        selectionData.put("bufferedDurationUs", String.valueOf(bufferedDurationUs));
        selectionData.put("selectedBitrate", String.valueOf(getFormat(selectedIndex).bitrate));
        selectionData.put("selectedQoE", String.valueOf(selectedQoE));
    }

    @Override
    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public int getSelectionReason() {
        return reason;
    }

    @Override
    public Object getSelectionData() {
        return selectionData;
    }


    /**
     * Computes the min qualified selected index (represents the max qualified bitrate) which won't cause buffer underflow
     *
     * @param nowMs The current time in the timebase of {@link SystemClock#elapsedRealtime()}, or
     *     {@link Long#MIN_VALUE} to ignore blacklisting.
     * @param bufferedDurationUs The current buffer size at the client
     *
     */
    private int determineQualifiedSelectedIndex(long nowMs, long bufferedDurationUs){
        int currentSelectedIndex = selectedIndex;
        Format currentFormat = getSelectedFormat();
        long bitrateEstimate = bandwidthMeter.getBitrateEstimate();
        long effectiveBitrate = bitrateEstimate == BandwidthMeter.NO_ESTIMATE
                ? maxInitialBitrate : (long) (bitrateEstimate * bandwidthFraction);
        int lowestBitrateNonBlacklistedIndex = 0;
        for(int i = 0; i < length; i++){
            if(!isBlacklisted(i,nowMs)){
                long bufferDurationAfterDownloadUs = bufferedDurationUs + getSegDutationsUs(currentSelectedIndex)
                        - getSegDutationsUs(currentSelectedIndex) * currentFormat.bitrate/effectiveBitrate;
                if(bufferDurationAfterDownloadUs >= minBufferDurationUs){
                    return i;
                }else{
                    lowestBitrateNonBlacklistedIndex = i;
                }
            }
        }
        return lowestBitrateNonBlacklistedIndex;
    }

    /**
     * Computes the initially selected index
     *
     * @param nowMs The current time in the timebase of {@link SystemClock#elapsedRealtime()}, or
     *     {@link Long#MIN_VALUE} to ignore blacklisting.
     */
    private int determineInitSelectedIndex(long nowMs) {
        long bitrateEstimate = bandwidthMeter.getBitrateEstimate();
        long effectiveBitrate = bitrateEstimate == BandwidthMeter.NO_ESTIMATE
                ? maxInitialBitrate : (long) (bitrateEstimate * bandwidthFraction);
        int lowestBitrateNonBlacklistedIndex = 0;
        for (int i = 0; i < length; i++) {
            if (nowMs == Long.MIN_VALUE || !isBlacklisted(i, nowMs)) {
                Format format = getFormat(i);
                if (format.bitrate <= effectiveBitrate) {
                    return i;
                } else {
                    lowestBitrateNonBlacklistedIndex = i;
                }
            }
        }
        return lowestBitrateNonBlacklistedIndex;
    }

    /**
     * Computes the paniclly selected index
     *
     * @param nowMs The current time in the timebase of {@link SystemClock#elapsedRealtime()}, or
     *     {@link Long#MIN_VALUE} to ignore blacklisting.
     */
    private int determinePanicSelectedIndex(long nowMs) {
        long bitrateEstimate = bandwidthMeter.getBitrateEstimate();
        long effectiveBitrate = bitrateEstimate == BandwidthMeter.NO_ESTIMATE
                ? maxInitialBitrate : (long) (bitrateEstimate * bandwidthFraction);
        int lowestBitrateNonBlacklistedIndex = 0;
        for (int i = 0; i < length; i++) {
            if (nowMs == Long.MIN_VALUE || !isBlacklisted(i, nowMs)) {
                Format format = getFormat(i);
                if (format.bitrate <= sigmaFractionUsedWhenPanic * effectiveBitrate) {
                    return i;
                } else {
                    lowestBitrateNonBlacklistedIndex = i;
                }
            }
        }
        return lowestBitrateNonBlacklistedIndex;
    }
}
