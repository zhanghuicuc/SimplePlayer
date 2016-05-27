package com.zhanghui.simpleplayer;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;

import com.zhanghui.helper.myString;
import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.BaseUrl;
import com.zhanghui.mpd.ContentComponent;
import com.zhanghui.mpd.Descriptor;
import com.zhanghui.mpd.MPD;
import com.zhanghui.mpd.Metrics;
import com.zhanghui.mpd.Period;
import com.zhanghui.mpd.ProgramInformation;
import com.zhanghui.mpd.Range;
import com.zhanghui.mpd.Representation;
import com.zhanghui.mpd.SegmentBase;
import com.zhanghui.mpd.SegmentList;
import com.zhanghui.mpd.SegmentTemplate;
import com.zhanghui.mpd.SegmentTimeline;
import com.zhanghui.mpd.SegmentURL;
import com.zhanghui.mpd.SubRepresentation;
import com.zhanghui.mpd.Subset;
import com.zhanghui.mpd.Timeline;
import com.zhanghui.mpd.URLType;
import com.zhanghui.xml.AdaptationSetNode;
import com.zhanghui.xml.BaseUrlNode;
import com.zhanghui.xml.MetricsNode;
import com.zhanghui.xml.PeriodNode;
import com.zhanghui.xml.ProgramInformationNode;

/**
 * Created by zhanghui on 2016/5/20.
 */
public class MPDParser {
    private static final String TAG = MPDParser.class.getSimpleName();
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";

    private String mpdURL;
    private SegmentManager segmentManager = new SegmentManager();
    //private RootElement root;
    private MPD mpd=new MPD();

    public MPDParser(String mpdURL) {
        this.mpdURL = mpdURL;
        BaseUrl mpdPathBaseUrl=new BaseUrl();
        mpdPathBaseUrl.SetUrl(mpdURL);
        mpd.SetMpdPathBaseUrl(mpdPathBaseUrl);
        //root = new RootElement(NAMESPACE, "MPD");
        //buildContentHandler();
    }

    public void parse(InputStream inputStream){
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, null);
            int eventType = xpp.next();
            if (eventType != XmlPullParser.START_TAG || !"MPD".equals(xpp.getName())) {
                throw new Exception(
                        "inputStream does not contain a valid media presentation description");
            }
            parseMpdWithPull(xpp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseMpdWithPull(XmlPullParser mpdPullParser){
        try{
            String id=mpdPullParser.getAttributeValue(null,"id");
            if(id!=null)
                mpd.SetId(id);
            String profiles=mpdPullParser.getAttributeValue(null,"profiles");
            if (profiles!=null) {
                mpd.SetProfiles(profiles);
            }
            String type=mpdPullParser.getAttributeValue(null,"type");
            if (type!=null) {
                mpd.SetType(type);
            }
            String availabilityStartTime=mpdPullParser.getAttributeValue(null,"availabilityStartTime");
            if (availabilityStartTime!=null) {
                mpd.SetAvailabilityStarttime(availabilityStartTime);
            }
            String availabilityEndTime=mpdPullParser.getAttributeValue(null,"availabilityEndTime");
            if (availabilityEndTime!=null) {
                mpd.SetAvailabilityEndtime(availabilityEndTime);
            }
            String mediaPresentationDuration=mpdPullParser.getAttributeValue(null,"mediaPresentationDuration");
            if (mediaPresentationDuration!=null) {
                mpd.SetMediaPresentationDuration(mediaPresentationDuration);
            }
            String minimumUpdatePeriod=mpdPullParser.getAttributeValue(null,"minimumUpdatePeriod");
            if (minimumUpdatePeriod!=null) {
                mpd.SetMinimumUpdatePeriod(minimumUpdatePeriod);
            }
            String minBufferTime=mpdPullParser.getAttributeValue(null,"minBufferTime");
            if (minBufferTime!=null) {
                mpd.SetMinBufferTime(minBufferTime);
            }
            String timeShiftBufferDepth=mpdPullParser.getAttributeValue(NAMESPACE,"timeShiftBufferDepth");
            if (timeShiftBufferDepth!=null) {
                mpd.SetTimeShiftBufferDepth(timeShiftBufferDepth);
            }
            String suggestedPresentationDelay=mpdPullParser.getAttributeValue(NAMESPACE,"suggestedPresentationDelay");
            if (suggestedPresentationDelay!=null) {
                mpd.SetSuggestedPresentationDelay(suggestedPresentationDelay);
            }
            String maxSegmentDuration=mpdPullParser.getAttributeValue(NAMESPACE,"maxSegmentDuration");
            if (maxSegmentDuration!=null) {
                mpd.SetMaxSegmentDuration(maxSegmentDuration);
            }
            String maxSubsegmentDuration=mpdPullParser.getAttributeValue(NAMESPACE,"maxSubsegmentDuration");
            if (maxSubsegmentDuration!=null) {
                mpd.SetMaxSubsegmentDuration(maxSubsegmentDuration);
            }
            do{
                mpdPullParser.next();
                if (mpdPullParser.getEventType() == XmlPullParser.START_TAG && mpdPullParser.getName().equals("BaseURL")) {
                    BaseUrl baseUrl=new BaseUrl();
                    String serviceLocation=mpdPullParser.getAttributeValue(null,"serviceLocation");
                    if(serviceLocation!=null)
                        baseUrl.SetServiceLocation(serviceLocation);
                    String byteRange=mpdPullParser.getAttributeValue(null,"byteRange");
                    if (byteRange!=null) {
                        baseUrl.SetByteRange(byteRange);
                    }
                    String availabilityTimeOffset=mpdPullParser.getAttributeValue(null,"availabilityTimeOffset");
                    if (availabilityTimeOffset!=null) {
                        baseUrl.SetAvailabilityTimeOffset(Double.parseDouble(availabilityTimeOffset));
                    }
                    String availabilityTimeComplete=mpdPullParser.getAttributeValue(null,"availabilityTimeComplete");
                    if (availabilityTimeComplete!=null) {
                        baseUrl.SetAvailabilityTimeComplete(myString.ToBool(availabilityTimeComplete));
                    }
                    mpdPullParser.next();
                    baseUrl.SetUrl(mpdPullParser.getText());
                    mpd.AddBaseUrls(baseUrl);

                } else if (mpdPullParser.getEventType() == XmlPullParser.START_TAG && mpdPullParser.getName().equals("Period")) {
                    Period period = parsePeriod(mpdPullParser);
                    mpd.AddPeriods(period);
                }
                else if (mpdPullParser.getEventType() == XmlPullParser.START_TAG && mpdPullParser.getName().equals("Location")) {
                    mpdPullParser.next();
                    mpd.AddLocations(mpdPullParser.getText());
                }
                else if (mpdPullParser.getEventType() == XmlPullParser.START_TAG && mpdPullParser.getName().equals("ProgramInformation")) {
                    ProgramInformation programInformation = new ProgramInformation();
                    programInformation=parseProgramInformation(mpdPullParser);
                    mpd.AddProgramInformations(programInformation);
                }
                else if (mpdPullParser.getEventType() == XmlPullParser.START_TAG && mpdPullParser.getName().equals("Metrics")) {
                    Metrics metrics = new Metrics();
                    metrics=parseMetrics(mpdPullParser);
                    mpd.AddMetrics(metrics);
                }
            }
            while(!(mpdPullParser.getEventType() == XmlPullParser.END_TAG && mpdPullParser.getName().equals("MPD")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected ProgramInformation parseProgramInformation(XmlPullParser pgmInfoPullParser) {
        ProgramInformation programInformation=new ProgramInformation();
        try{
            String lang=pgmInfoPullParser.getAttributeValue(null,"lang");
            if(lang!=null)
                programInformation.SetLang(lang);
            String moreInformationURL=pgmInfoPullParser.getAttributeValue(null, "moreInformationURL");
            if (moreInformationURL!=null) {
                programInformation.SetMoreInformationURL(moreInformationURL);
            }
            do {
                pgmInfoPullParser.next();
                if (pgmInfoPullParser.getEventType() == XmlPullParser.START_TAG && pgmInfoPullParser.getName().equals("Title")) {
                    pgmInfoPullParser.next();
                    programInformation.SetTitle(pgmInfoPullParser.getText());
                } else if (pgmInfoPullParser.getEventType() == XmlPullParser.START_TAG && pgmInfoPullParser.getName().equals("Source")) {
                    pgmInfoPullParser.next();
                    programInformation.SetSource(pgmInfoPullParser.getText());
                }else if(pgmInfoPullParser.getEventType() == XmlPullParser.START_TAG && pgmInfoPullParser.getName().equals("Copyright")){
                    pgmInfoPullParser.next();
                    programInformation.SetCopyright(pgmInfoPullParser.getText());
                }
            } while (!(pgmInfoPullParser.getEventType() == XmlPullParser.END_TAG && pgmInfoPullParser.getName().equals("ProgramInformation")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return programInformation;
    }

    protected Metrics parseMetrics(XmlPullParser metricsPullParser) {
        Metrics metrics=new Metrics();
        try{
            String value=metricsPullParser.getAttributeValue(null,"metrics");
            if(value!=null)
                metrics.SetMetrics(value);
            do {
                metricsPullParser.next();
                if (metricsPullParser.getEventType() == XmlPullParser.START_TAG && metricsPullParser.getName().equals("Reporting")) {
                    Descriptor descriptor=parseDescriptor(metricsPullParser);
                    metrics.AddReportings(descriptor);
                } else if (metricsPullParser.getEventType() == XmlPullParser.START_TAG && metricsPullParser.getName().equals("Range")) {
                    Range range=parseRange(metricsPullParser);
                    metrics.AddRanges(range);
                }
            } while (!(metricsPullParser.getEventType() == XmlPullParser.END_TAG && metricsPullParser.getName().equals("Metrics")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return metrics;
    }

    protected Descriptor parseDescriptor(XmlPullParser descriptorPullParser) {
        Descriptor descriptor=new Descriptor();
        try{
            String schemeIdUri=descriptorPullParser.getAttributeValue(null,"schemeIdUri");
            if(schemeIdUri!=null)
                descriptor.SetSchemeIdUri(schemeIdUri);
            String value=descriptorPullParser.getAttributeValue(null,"value");
            if(value!=null)
                descriptor.SetValue(value);
            String id=descriptorPullParser.getAttributeValue(null,"id");
            if(id!=null)
                descriptor.SetId(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return descriptor;
    }

    protected Range parseRange(XmlPullParser rangePullParser) {
        Range range=new Range();
        try{
            String starttime=rangePullParser.getAttributeValue(null,"starttime");
            if(starttime!=null)
                range.SetStarttime(starttime);
            String duration=rangePullParser.getAttributeValue(null,"duration");
            if(duration!=null)
                range.SetDuration(duration);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return range;
    }

    protected Period parsePeriod(XmlPullParser periodPullParser) {
        Period period=new Period();
        try{
            String href=periodPullParser.getAttributeValue(null,"xlink:href");
            if(href!=null)
                period.SetXlinkHref(href);
            String actuate=periodPullParser.getAttributeValue(null,"xlink:actuate");
            if(actuate!=null)
                period.SetXlinkActuate(actuate);
            String id=periodPullParser.getAttributeValue(null,"id");
            if(id!=null)
                period.SetId(id);
            String start=periodPullParser.getAttributeValue(null, "start");
            if (start!=null) {
                period.SetStart(start);
            }
            String duration=periodPullParser.getAttributeValue(null, "duration");
            if (duration!=null) {
                period.SetDuration((duration));
            }
            String bitstreamSwitching=periodPullParser.getAttributeValue(null,"bitstreamSwitching");
            if(bitstreamSwitching!=null)
                period.SetBitstreamSwitching(myString.ToBool(bitstreamSwitching));
            do {
                periodPullParser.next();
                if (periodPullParser.getEventType() == XmlPullParser.START_TAG && periodPullParser.getName().equals("BaseURL")) {
                    BaseUrl baseUrl=new BaseUrl();
                    String serviceLocation=periodPullParser.getAttributeValue(null,"serviceLocation");
                    if(serviceLocation!=null)
                        baseUrl.SetServiceLocation(serviceLocation);
                    String byteRange=periodPullParser.getAttributeValue(null,"byteRange");
                    if (byteRange!=null) {
                        baseUrl.SetByteRange(byteRange);
                    }
                    String availabilityTimeOffset=periodPullParser.getAttributeValue(null,"availabilityTimeOffset");
                    if (availabilityTimeOffset!=null) {
                        baseUrl.SetAvailabilityTimeOffset(Double.parseDouble(availabilityTimeOffset));
                    }
                    String availabilityTimeComplete=periodPullParser.getAttributeValue(null,"availabilityTimeComplete");
                    if (availabilityTimeComplete!=null) {
                        baseUrl.SetAvailabilityTimeComplete(myString.ToBool(availabilityTimeComplete));
                    }
                    periodPullParser.next();
                    baseUrl.SetUrl(periodPullParser.getText());
                    period.AddBaseURLs(baseUrl);
                } else if (periodPullParser.getEventType() == XmlPullParser.START_TAG && periodPullParser.getName().equals("AdaptationSet")) {
                    period.AddAdaptationSets(parseAdaptationSet(periodPullParser));
                }else if (periodPullParser.getEventType() == XmlPullParser.START_TAG && periodPullParser.getName().equals("Subset")) {
                    period.AddSubsets(parseSubset(periodPullParser));
                }else if (periodPullParser.getEventType() == XmlPullParser.START_TAG && periodPullParser.getName().equals("SegmentBase")) {
                    period.SetSegmentBase(parseSegmentBase(periodPullParser));
                }else if (periodPullParser.getEventType() == XmlPullParser.START_TAG && periodPullParser.getName().equals("SegmentList")) {
                    period.SetSegmentList(parseSegmentList(periodPullParser));
                }else if (periodPullParser.getEventType() == XmlPullParser.START_TAG && periodPullParser.getName().equals("SegmentTemplate")) {
                    period.SetSegmentTemplate(parseSegmentTemplate(periodPullParser));
                }
            } while (!(periodPullParser.getEventType() == XmlPullParser.END_TAG && periodPullParser.getName().equals("Period")));
        }
        catch (Exception e){
            e.printStackTrace();
        }return period;
    }

    protected Subset parseSubset(XmlPullParser subsetPullParser) {
        Subset subset=new Subset();
        try{
            String contains=subsetPullParser.getAttributeValue(null,"contains");
            if(contains!=null)
                subset.SetContains(contains);
            String id=subsetPullParser.getAttributeValue(null,"id");
            if(id!=null)
                subset.SetId(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return subset;
    }


    protected AdaptationSet parseAdaptationSet(XmlPullParser asPullParser) {
        AdaptationSet adaptationSet=new AdaptationSet();
        try{
            //RepresentationBase part
            String profiles=asPullParser.getAttributeValue(null, "profiles");
            if (profiles!=null) {
                adaptationSet.SetProfiles((profiles));
            }
            String width=asPullParser.getAttributeValue(null, "width");
            if (width!=null) {
                adaptationSet.SetWidth(Integer.parseInt(width));
            }
            String height=asPullParser.getAttributeValue(null, "height");
            if (height!=null) {
                adaptationSet.SetHeight(Integer.parseInt(height));
            }
            String sar=asPullParser.getAttributeValue(null, "sar");
            if (sar!=null) {
                adaptationSet.SetSar((sar));
            }
            String frameRate=asPullParser.getAttributeValue(null, "frameRate");
            if (frameRate!=null) {
                adaptationSet.SetFrameRate((frameRate));
            }
            String audioSamplingRate=asPullParser.getAttributeValue(null, "audioSamplingRate");
            if (audioSamplingRate!=null) {
                adaptationSet.SetAudioSamplingRate((audioSamplingRate));
            }
            String mimeType=asPullParser.getAttributeValue(null, "mimeType");
            if (mimeType!=null) {
                adaptationSet.SetMimeType((mimeType));
            }
            String segmentProfiles=asPullParser.getAttributeValue(null, "segmentProfiles");
            if (segmentProfiles!=null) {
                adaptationSet.SetSegmentProfiles((segmentProfiles));
            }
            String codecs=asPullParser.getAttributeValue(null, "codecs");
            if (codecs!=null) {
                adaptationSet.SetCodecs((codecs));
            }
            String maximumSAPPeriod=asPullParser.getAttributeValue(null, "maximumSAPPeriod");
            if (maximumSAPPeriod!=null) {
                adaptationSet.SetMaximumSAPPeriod(Double.parseDouble(maximumSAPPeriod));
            }
            String startWithSAP=asPullParser.getAttributeValue(null, "startWithSAP");
            if (startWithSAP!=null) {
                adaptationSet.SetStartWithSAP(Byte.parseByte(startWithSAP));
            }
            String maxPlayoutRate=asPullParser.getAttributeValue(null, "maxPlayoutRate");
            if (maxPlayoutRate!=null) {
                adaptationSet.SetMaxPlayoutRate(Double.parseDouble(maxPlayoutRate));
            }
            String codingDependency=asPullParser.getAttributeValue(null, "codingDependency");
            if (codingDependency!=null) {
                adaptationSet.SetCodingDependency(myString.ToBool(codingDependency));
            }
            String scanType=asPullParser.getAttributeValue(null, "scanType");
            if (scanType!=null) {
                adaptationSet.SetScanType((scanType));
            }
            //AdaptationSet part
            String href=asPullParser.getAttributeValue(null, "xlink:href");
            if (href!=null) {
                adaptationSet.SetXlinkHref((href));
            }
            String actuate=asPullParser.getAttributeValue(null, "xlink:actuate");
            if (actuate!=null) {
                adaptationSet.SetXlinkActuate((actuate));
            }
            String id=asPullParser.getAttributeValue(null, "id");
            if (id!=null) {
                adaptationSet.SetId(Integer.parseInt(id));
            }
            String group=asPullParser.getAttributeValue(null, "group");
            if (group!=null) {
                adaptationSet.SetGroup(Integer.parseInt(group));
            }
            String lang=asPullParser.getAttributeValue(null, "lang");
            if (lang!=null) {
                adaptationSet.SetLang((lang));
            }
            String contentType=asPullParser.getAttributeValue(null, "contentType");
            if (contentType!=null) {
                adaptationSet.SetContentType((contentType));
            }
            String par=asPullParser.getAttributeValue(null, "par");
            if (par!=null) {
                adaptationSet.SetPar((par));
            }
            String minBandwidth=asPullParser.getAttributeValue(null, "minBandwidth");
            if (minBandwidth!=null) {
                adaptationSet.SetMinBandwidth(Integer.parseInt(minBandwidth));
            }
            String maxBandwidth=asPullParser.getAttributeValue(null, "maxBandwidth");
            if (maxBandwidth!=null) {
                adaptationSet.SetMaxBandwidth(Integer.parseInt(maxBandwidth));
            }
            String minWidth=asPullParser.getAttributeValue(null, "minWidth");
            if (minWidth!=null) {
                adaptationSet.SetMinWidth(Integer.parseInt(minWidth));
            }
            String maxWidth=asPullParser.getAttributeValue(null, "maxWidth");
            if (maxWidth!=null) {
                adaptationSet.SetMaxWidth(Integer.parseInt(maxWidth));
            }
            String minHeight=asPullParser.getAttributeValue(null, "minHeight");
            if (minHeight!=null) {
                adaptationSet.SetMinHeight(Integer.parseInt(minHeight));
            }
            String maxHeight=asPullParser.getAttributeValue(null, "maxHeight");
            if (maxHeight!=null) {
                adaptationSet.SetMaxHeight(Integer.parseInt(maxHeight));
            }
            String minFrameRate=asPullParser.getAttributeValue(null, "minFrameRate");
            if (minFrameRate!=null) {
                adaptationSet.SetMinFramerate((minFrameRate));
            }
            String maxFrameRate=asPullParser.getAttributeValue(null, "maxFrameRate");
            if (maxFrameRate!=null) {
                adaptationSet.SetMaxFramerate((maxFrameRate));
            }
            String segmentAlignment=asPullParser.getAttributeValue(null, "segmentAlignment");
            if (segmentAlignment!=null) {
                adaptationSet.SetSegmentAlignment(Integer.parseInt(segmentAlignment));
            }
            String subsegmentAlignment=asPullParser.getAttributeValue(null, "subsegmentAlignment");
            if (subsegmentAlignment!=null) {
                adaptationSet.SetSubsegmentAlignment(Integer.parseInt(subsegmentAlignment));
            }
            String subsegmentStartsWithSAP=asPullParser.getAttributeValue(null, "subsegmentStartsWithSAP");
            if (subsegmentStartsWithSAP!=null) {
                adaptationSet.SetSubsegmentStartsWithSAP(Byte.parseByte(subsegmentStartsWithSAP));
            }
            String bitstreamSwitching=asPullParser.getAttributeValue(null, "bitstreamSwitching");
            if (bitstreamSwitching!=null) {
                adaptationSet.SetIsBitstreamSwitching(myString.ToBool(bitstreamSwitching));
            }

            do {
                asPullParser.next();
                if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("BaseURL")) {
                    BaseUrl baseUrl=new BaseUrl();
                    String serviceLocation=asPullParser.getAttributeValue(null,"serviceLocation");
                    if(serviceLocation!=null)
                        baseUrl.SetServiceLocation(serviceLocation);
                    String byteRange=asPullParser.getAttributeValue(null,"byteRange");
                    if (byteRange!=null) {
                        baseUrl.SetByteRange(byteRange);
                    }
                    String availabilityTimeOffset=asPullParser.getAttributeValue(null,"availabilityTimeOffset");
                    if (availabilityTimeOffset!=null) {
                        baseUrl.SetAvailabilityTimeOffset(Double.parseDouble(availabilityTimeOffset));
                    }
                    String availabilityTimeComplete=asPullParser.getAttributeValue(null,"availabilityTimeComplete");
                    if (availabilityTimeComplete!=null) {
                        baseUrl.SetAvailabilityTimeComplete(myString.ToBool(availabilityTimeComplete));
                    }
                    asPullParser.next();
                    baseUrl.SetUrl(asPullParser.getText());
                    adaptationSet.AddBaseURLs(baseUrl);
                } else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("Representation")) {
                    Representation representation = parseRepresentation(asPullParser);
                    adaptationSet.AddRepresentation(representation);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("Accessibility")) {
                    Descriptor accessibility = parseDescriptor(asPullParser);
                    adaptationSet.AddAccessibility(accessibility);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("Role")) {
                    Descriptor role = parseDescriptor(asPullParser);
                    adaptationSet.AddRole(role);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("Rating")) {
                    Descriptor rating = parseDescriptor(asPullParser);
                    adaptationSet.AddRating(rating);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("Viewpoint")) {
                    Descriptor viewpoint = parseDescriptor(asPullParser);
                    adaptationSet.AddViewpoint(viewpoint);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("ContentComponent")) {
                    ContentComponent contentComponent = parseContentComponent(asPullParser);
                    adaptationSet.AddContentComponent(contentComponent);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("SegmentBase")) {
                    SegmentBase segmentBase = parseSegmentBase(asPullParser);
                    adaptationSet.SetSegmentBase(segmentBase);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("SegmentList")) {
                    SegmentList segmentList = parseSegmentList(asPullParser);
                    adaptationSet.SetSegmentList(segmentList);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("SegmentTemplate")) {
                    SegmentTemplate segmentTemplate = parseSegmentTemplate(asPullParser);
                    adaptationSet.SetSegmentTemplate(segmentTemplate);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("FramePacking")) {
                    Descriptor framepacking = parseDescriptor(asPullParser);
                    adaptationSet.AddFramePacking(framepacking);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("AudioChannelConfiguration")) {
                    Descriptor acc = parseDescriptor(asPullParser);
                    adaptationSet.AddAudioChannelConfiguration(acc);
                }
                else if (asPullParser.getEventType() == XmlPullParser.START_TAG && asPullParser.getName().equals("ContentProtection")) {
                    Descriptor cp = parseDescriptor(asPullParser);
                    adaptationSet.AddContentProtection(cp);
                }
            } while (!(asPullParser.getEventType() == XmlPullParser.END_TAG && asPullParser.getName().equals("AdaptationSet")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return adaptationSet;
    }

    protected ContentComponent parseContentComponent(XmlPullParser ccPullParser) {
        ContentComponent contentComponent=new ContentComponent();
        try{
            String id=ccPullParser.getAttributeValue(null, "id");
            if (id!=null) {
                contentComponent.SetId(Integer.parseInt(id));
            }
            String lang=ccPullParser.getAttributeValue(null, "lang");
            if (lang!=null) {
                contentComponent.SetLang((lang));
            }
            String contentType=ccPullParser.getAttributeValue(null, "contentType");
            if (contentType!=null) {
                contentComponent.SetContentType((contentType));
            }
            String par=ccPullParser.getAttributeValue(null, "par");
            if (par!=null) {
                contentComponent.SetPar((par));
            }
            do {
                ccPullParser.next();
                if (ccPullParser.getEventType() == XmlPullParser.START_TAG && ccPullParser.getName().equals("Accessibility")) {
                    Descriptor accessibility = parseDescriptor(ccPullParser);
                    contentComponent.AddAccessibility(accessibility);
                }
                else if (ccPullParser.getEventType() == XmlPullParser.START_TAG && ccPullParser.getName().equals("Role")) {
                    Descriptor role = parseDescriptor(ccPullParser);
                    contentComponent.AddRole(role);
                }
                else if (ccPullParser.getEventType() == XmlPullParser.START_TAG && ccPullParser.getName().equals("Rating")) {
                    Descriptor rating = parseDescriptor(ccPullParser);
                    contentComponent.AddRating(rating);
                }
                else if (ccPullParser.getEventType() == XmlPullParser.START_TAG && ccPullParser.getName().equals("Viewpoint")) {
                    Descriptor viewpoint = parseDescriptor(ccPullParser);
                    contentComponent.AddViewpoint(viewpoint);
                }
            } while (!(ccPullParser.getEventType() == XmlPullParser.END_TAG && ccPullParser.getName().equals("ContentComponent")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return contentComponent;
    }

    protected Representation parseRepresentation(XmlPullParser repPullParser) {
        Representation representation=new Representation();
        try{
            //RepresentationBase part
            String profiles=repPullParser.getAttributeValue(null, "profiles");
            if (profiles!=null) {
                representation.SetProfiles((profiles));
            }
            String width=repPullParser.getAttributeValue(null, "width");
            if (width!=null) {
                representation.SetWidth(Integer.parseInt(width));
            }
            String height=repPullParser.getAttributeValue(null, "height");
            if (height!=null) {
                representation.SetHeight(Integer.parseInt(height));
            }
            String sar=repPullParser.getAttributeValue(null, "sar");
            if (sar!=null) {
                representation.SetSar((sar));
            }
            String frameRate=repPullParser.getAttributeValue(null, "frameRate");
            if (frameRate!=null) {
                representation.SetFrameRate((frameRate));
            }
            String audioSamplingRate=repPullParser.getAttributeValue(null, "audioSamplingRate");
            if (audioSamplingRate!=null) {
                representation.SetAudioSamplingRate((audioSamplingRate));
            }
            String mimeType=repPullParser.getAttributeValue(null, "mimeType");
            if (mimeType!=null) {
                representation.SetMimeType((mimeType));
            }
            String segmentProfiles=repPullParser.getAttributeValue(null, "segmentProfiles");
            if (segmentProfiles!=null) {
                representation.SetSegmentProfiles((segmentProfiles));
            }
            String codecs=repPullParser.getAttributeValue(null, "codecs");
            if (codecs!=null) {
                representation.SetCodecs((codecs));
            }
            String maximumSAPPeriod=repPullParser.getAttributeValue(null, "maximumSAPPeriod");
            if (maximumSAPPeriod!=null) {
                representation.SetMaximumSAPPeriod(Double.parseDouble(maximumSAPPeriod));
            }
            String startWithSAP=repPullParser.getAttributeValue(null, "startWithSAP");
            if (startWithSAP!=null) {
                representation.SetStartWithSAP(Byte.parseByte(startWithSAP));
            }
            String maxPlayoutRate=repPullParser.getAttributeValue(null, "maxPlayoutRate");
            if (maxPlayoutRate!=null) {
                representation.SetMaxPlayoutRate(Double.parseDouble(maxPlayoutRate));
            }
            String codingDependency=repPullParser.getAttributeValue(null, "codingDependency");
            if (codingDependency!=null) {
                representation.SetCodingDependency(myString.ToBool(codingDependency));
            }
            String scanType=repPullParser.getAttributeValue(null, "scanType");
            if (scanType!=null) {
                representation.SetScanType((scanType));
            }
            //Representation Part
            String id=repPullParser.getAttributeValue(null, "id");
            if (id!=null) {
                representation.SetId((id));
            }
            String dependencyId=repPullParser.getAttributeValue(null, "dependencyId");
            if (dependencyId!=null) {
                representation.SetDependencyId((dependencyId));
            }
            String qualityRanking=repPullParser.getAttributeValue(null, "qualityRanking");
            if (qualityRanking!=null) {
                representation.SetQualityRanking(Integer.parseInt(qualityRanking));
            }
            String mediaStreamStructureId=repPullParser.getAttributeValue(null, "mediaStreamStructureId");
            if (mediaStreamStructureId!=null) {
                representation.SetMediaStreamStructureId((mediaStreamStructureId));
            }
            String bandwidth=repPullParser.getAttributeValue(null, "bandwidth");
            if (bandwidth!=null) {
                representation.SetBandwidth(Integer.parseInt(bandwidth));
            }
            do {
                repPullParser.next();
                if (repPullParser.getEventType() == XmlPullParser.START_TAG && repPullParser.getName().equals("BaseURL")) {
                    BaseUrl baseUrl=new BaseUrl();
                    String serviceLocation=repPullParser.getAttributeValue(null,"serviceLocation");
                    if(serviceLocation!=null)
                        baseUrl.SetServiceLocation(serviceLocation);
                    String byteRange=repPullParser.getAttributeValue(null,"byteRange");
                    if (byteRange!=null) {
                        baseUrl.SetByteRange(byteRange);
                    }
                    String availabilityTimeOffset=repPullParser.getAttributeValue(null,"availabilityTimeOffset");
                    if (availabilityTimeOffset!=null) {
                        baseUrl.SetAvailabilityTimeOffset(Double.parseDouble(availabilityTimeOffset));
                    }
                    String availabilityTimeComplete=repPullParser.getAttributeValue(null,"availabilityTimeComplete");
                    if (availabilityTimeComplete!=null) {
                        baseUrl.SetAvailabilityTimeComplete(myString.ToBool(availabilityTimeComplete));
                    }
                    repPullParser.next();
                    baseUrl.SetUrl(repPullParser.getText());
                    representation.AddBaseURLs(baseUrl);
                }
                else if (repPullParser.getEventType() == XmlPullParser.START_TAG && repPullParser.getName().equals("SegmentBase")) {
                    SegmentBase segmentBase = parseSegmentBase(repPullParser);
                    representation.SetSegmentBase(segmentBase);
                }
                else if (repPullParser.getEventType() == XmlPullParser.START_TAG && repPullParser.getName().equals("SegmentList")) {
                    SegmentList segmentList = parseSegmentList(repPullParser);
                    representation.SetSegmentList(segmentList);
                }
                else if (repPullParser.getEventType() == XmlPullParser.START_TAG && repPullParser.getName().equals("SegmentTemplate")) {
                    SegmentTemplate segmentTemplate = parseSegmentTemplate(repPullParser);
                    representation.SetSegmentTemplate(segmentTemplate);
                }
                else if (repPullParser.getEventType() == XmlPullParser.START_TAG && repPullParser.getName().equals("FramePacking")) {
                    Descriptor framepacking = parseDescriptor(repPullParser);
                    representation.AddFramePacking(framepacking);
                }
                else if (repPullParser.getEventType() == XmlPullParser.START_TAG && repPullParser.getName().equals("AudioChannelConfiguration")) {
                    Descriptor acc = parseDescriptor(repPullParser);
                    representation.AddAudioChannelConfiguration(acc);
                }
                else if (repPullParser.getEventType() == XmlPullParser.START_TAG && repPullParser.getName().equals("ContentProtection")) {
                    Descriptor cp = parseDescriptor(repPullParser);
                    representation.AddContentProtection(cp);
                }
                else if (repPullParser.getEventType() == XmlPullParser.START_TAG && repPullParser.getName().equals("SubRepresentation")) {
                    SubRepresentation subRepresentation = parseSubRepresentation(repPullParser);
                    representation.AddSubRepresentations(subRepresentation);
                }
            } while (!(repPullParser.getEventType() == XmlPullParser.END_TAG && repPullParser.getName().equals("Representation")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return representation;
    }

    protected SubRepresentation parseSubRepresentation(XmlPullParser srPullParser) {
        SubRepresentation subRepresentation=new SubRepresentation();
        try{
            //RepresentationBase part
            String profiles=srPullParser.getAttributeValue(null, "profiles");
            if (profiles!=null) {
                subRepresentation.SetProfiles((profiles));
            }
            String width=srPullParser.getAttributeValue(null, "width");
            if (width!=null) {
                subRepresentation.SetWidth(Integer.parseInt(width));
            }
            String height=srPullParser.getAttributeValue(null, "height");
            if (height!=null) {
                subRepresentation.SetHeight(Integer.parseInt(height));
            }
            String sar=srPullParser.getAttributeValue(null, "sar");
            if (sar!=null) {
                subRepresentation.SetSar((sar));
            }
            String frameRate=srPullParser.getAttributeValue(null, "frameRate");
            if (frameRate!=null) {
                subRepresentation.SetFrameRate((frameRate));
            }
            String audioSamplingRate=srPullParser.getAttributeValue(null, "audioSamplingRate");
            if (audioSamplingRate!=null) {
                subRepresentation.SetAudioSamplingRate((audioSamplingRate));
            }
            String mimeType=srPullParser.getAttributeValue(null, "mimeType");
            if (mimeType!=null) {
                subRepresentation.SetMimeType((mimeType));
            }
            String segmentProfiles=srPullParser.getAttributeValue(null, "segmentProfiles");
            if (segmentProfiles!=null) {
                subRepresentation.SetSegmentProfiles((segmentProfiles));
            }
            String codecs=srPullParser.getAttributeValue(null, "codecs");
            if (codecs!=null) {
                subRepresentation.SetCodecs((codecs));
            }
            String maximumSAPPeriod=srPullParser.getAttributeValue(null, "maximumSAPPeriod");
            if (maximumSAPPeriod!=null) {
                subRepresentation.SetMaximumSAPPeriod(Double.parseDouble(maximumSAPPeriod));
            }
            String startWithSAP=srPullParser.getAttributeValue(null, "startWithSAP");
            if (startWithSAP!=null) {
                subRepresentation.SetStartWithSAP(Byte.parseByte(startWithSAP));
            }
            String maxPlayoutRate=srPullParser.getAttributeValue(null, "maxPlayoutRate");
            if (maxPlayoutRate!=null) {
                subRepresentation.SetMaxPlayoutRate(Double.parseDouble(maxPlayoutRate));
            }
            String codingDependency=srPullParser.getAttributeValue(null, "codingDependency");
            if (codingDependency!=null) {
                subRepresentation.SetCodingDependency(myString.ToBool(codingDependency));
            }
            String scanType=srPullParser.getAttributeValue(null, "scanType");
            if (scanType!=null) {
                subRepresentation.SetScanType((scanType));
            }
            //Representation Part
            String level=srPullParser.getAttributeValue(null, "level");
            if (level!=null) {
                subRepresentation.SetLevel(Integer.parseInt(level));
            }
            String dependencyLevel=srPullParser.getAttributeValue(null, "dependencyLevel");
            if (dependencyLevel!=null) {
                subRepresentation.SetDependencyLevel((dependencyLevel));
            }
            String contentComponent=srPullParser.getAttributeValue(null, "contentComponent");
            if (contentComponent!=null) {
                subRepresentation.SetContentComponent((contentComponent));
            }
            String bandwidth=srPullParser.getAttributeValue(null, "bandwidth");
            if (bandwidth!=null) {
                subRepresentation.SetBandWidth(Integer.parseInt(bandwidth));
            }
            do {
                srPullParser.next();
                if (srPullParser.getEventType() == XmlPullParser.START_TAG && srPullParser.getName().equals("FramePacking")) {
                    Descriptor framepacking = parseDescriptor(srPullParser);
                    subRepresentation.AddFramePacking(framepacking);
                }
                else if (srPullParser.getEventType() == XmlPullParser.START_TAG && srPullParser.getName().equals("AudioChannelConfiguration")) {
                    Descriptor acc = parseDescriptor(srPullParser);
                    subRepresentation.AddAudioChannelConfiguration(acc);
                }
                else if (srPullParser.getEventType() == XmlPullParser.START_TAG && srPullParser.getName().equals("ContentProtection")) {
                    Descriptor cp = parseDescriptor(srPullParser);
                    subRepresentation.AddContentProtection(cp);
                }
            } while (!(srPullParser.getEventType() == XmlPullParser.END_TAG && srPullParser.getName().equals("SubRepresentation")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return subRepresentation;
    }


    protected SegmentBase parseSegmentBase(XmlPullParser sbPullParser) {
        SegmentBase segmentBase=new SegmentBase();
        try{
            String timescale=sbPullParser.getAttributeValue(null, "timescale");
            if (timescale!=null) {
                segmentBase.SetTimescale(Integer.parseInt(timescale));
            }
            String presentationTimeOffset=sbPullParser.getAttributeValue(null, "presentationTimeOffset");
            if (presentationTimeOffset!=null) {
                segmentBase.SetPresentationTimeOffset(Integer.parseInt(presentationTimeOffset));
            }
            String timeShiftBufferDepth=sbPullParser.getAttributeValue(null, "timeShiftBufferDepth");
            if (timeShiftBufferDepth!=null) {
                segmentBase.SetTimeShiftBufferDepth((timeShiftBufferDepth));
            }
            String indexRange=sbPullParser.getAttributeValue(null, "indexRange");
            if (indexRange!=null) {
                segmentBase.SetIndexRange((indexRange));
            }
            String indexRangeExact=sbPullParser.getAttributeValue(null, "indexRangeExact");
            if (indexRangeExact!=null) {
                segmentBase.SetIndexRangeExact(myString.ToBool(indexRangeExact));
            }
            String availabilityTimeOffset=sbPullParser.getAttributeValue(null, "availabilityTimeOffset");
            if (availabilityTimeOffset!=null) {
                segmentBase.SetAvailabilityTimeOffset(Double.parseDouble(availabilityTimeOffset));
            }
            String availabilityTimeComplete=sbPullParser.getAttributeValue(null, "availabilityTimeComplete");
            if (availabilityTimeComplete!=null) {
                segmentBase.SetAvailabilityTimeComplete(myString.ToBool(availabilityTimeComplete));
            }
            do {
                sbPullParser.next();
                if (sbPullParser.getEventType() == XmlPullParser.START_TAG && sbPullParser.getName().equals("Initialization")) {
                    URLType init=parseURLType(sbPullParser);
                    init.SetType(HTTPTransactionType.InitializationSegment);
                    segmentBase.SetInitialization(init);
                }else if(sbPullParser.getEventType() == XmlPullParser.START_TAG && sbPullParser.getName().equals("RepresentationIndex")) {
                    URLType repidx=parseURLType(sbPullParser);
                    repidx.SetType(HTTPTransactionType.IndexSegment);
                    segmentBase.SetRepresentationIndex(repidx);
                }
            } while (!(sbPullParser.getEventType() == XmlPullParser.END_TAG && sbPullParser.getName().equals("SegmentBase")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return segmentBase;
    }

    protected SegmentList parseSegmentList(XmlPullParser slPullParser) {
        SegmentList segmentList=new SegmentList();
        try{
            //SegmentBase part
            String timescale=slPullParser.getAttributeValue(null, "timescale");
            if (timescale!=null) {
                segmentList.SetTimescale(Integer.parseInt(timescale));
            }
            String presentationTimeOffset=slPullParser.getAttributeValue(null, "presentationTimeOffset");
            if (presentationTimeOffset!=null) {
                segmentList.SetPresentationTimeOffset(Integer.parseInt(presentationTimeOffset));
            }
            String timeShiftBufferDepth=slPullParser.getAttributeValue(null, "timeShiftBufferDepth");
            if (timeShiftBufferDepth!=null) {
                segmentList.SetTimeShiftBufferDepth((timeShiftBufferDepth));
            }
            String indexRange=slPullParser.getAttributeValue(null, "indexRange");
            if (indexRange!=null) {
                segmentList.SetIndexRange((indexRange));
            }
            String indexRangeExact=slPullParser.getAttributeValue(null, "indexRangeExact");
            if (indexRangeExact!=null) {
                segmentList.SetIndexRangeExact(myString.ToBool(indexRangeExact));
            }
            String availabilityTimeOffset=slPullParser.getAttributeValue(null, "availabilityTimeOffset");
            if (availabilityTimeOffset!=null) {
                segmentList.SetAvailabilityTimeOffset(Double.parseDouble(availabilityTimeOffset));
            }
            String availabilityTimeComplete=slPullParser.getAttributeValue(null, "availabilityTimeComplete");
            if (availabilityTimeComplete!=null) {
                segmentList.SetAvailabilityTimeComplete(myString.ToBool(availabilityTimeComplete));
            }
            //ＭultiSegmentBase Part
            String duration=slPullParser.getAttributeValue(null, "duration");
            if (duration!=null) {
                segmentList.SetDuration(Integer.parseInt(duration));
            }
            String startNumber=slPullParser.getAttributeValue(null, "startNumber");
            if (startNumber!=null) {
                segmentList.SetStartNumber(Integer.parseInt(startNumber));
            }
            //SegmentList part
            String href=slPullParser.getAttributeValue(null,"xlink:href");
            if(href!=null)
                segmentList.SetXlinkHref(href);
            String actuate=slPullParser.getAttributeValue(null,"xlink:actuate");
            if(actuate!=null)
                segmentList.SetXlinkActuate(actuate);
            do {
                slPullParser.next();
                //Get SegmentBase.Initialization
                if (slPullParser.getEventType() == XmlPullParser.START_TAG && slPullParser.getName().equals("Initialization")) {
                    URLType init=parseURLType(slPullParser);
                    init.SetType(HTTPTransactionType.InitializationSegment);
                    segmentList.SetInitialization(init);
                }
                //Get SegmentBase.RepresentationIndex
                else if(slPullParser.getEventType() == XmlPullParser.START_TAG && slPullParser.getName().equals("RepresentationIndex")) {
                    URLType repidx=parseURLType(slPullParser);
                    repidx.SetType(HTTPTransactionType.IndexSegment);
                    segmentList.SetRepresentationIndex(repidx);
                }
                //Get NultiSegmentBase.SegmentTimeline
                else if(slPullParser.getEventType() == XmlPullParser.START_TAG && slPullParser.getName().equals("SegmentTimeline")) {
                    SegmentTimeline st=parseSegmentTimeline(slPullParser);
                    segmentList.SetSegmentTimeline(st);
                }
                //Get NultiSegmentBase.BitstreamSwitching
                else if(slPullParser.getEventType() == XmlPullParser.START_TAG && slPullParser.getName().equals("BitstreamSwitching")) {
                    URLType bss=parseURLType(slPullParser);
                    bss.SetType(HTTPTransactionType.BitstreamSwitchingSegment);
                    segmentList.SetBitstreamSwitching(bss);
                }
                //Get SegmentList.SegmentURL
                else if(slPullParser.getEventType() == XmlPullParser.START_TAG && slPullParser.getName().equals("SegmentURL")) {
                    SegmentURL segmentURL=parseSegmentURL(slPullParser);
                    segmentList.AddSegmentURLs(segmentURL);
                }
            } while (!(slPullParser.getEventType() == XmlPullParser.END_TAG && slPullParser.getName().equals("SegmentList")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return segmentList;
    }

    protected SegmentTemplate parseSegmentTemplate(XmlPullParser stPullParser) {
        SegmentTemplate segmentTemplate=new SegmentTemplate();
        try{
            //SegmentBase part
            String timescale=stPullParser.getAttributeValue(null, "timescale");
            if (timescale!=null) {
                segmentTemplate.SetTimescale(Integer.parseInt(timescale));
            }
            String presentationTimeOffset=stPullParser.getAttributeValue(null, "presentationTimeOffset");
            if (presentationTimeOffset!=null) {
                segmentTemplate.SetPresentationTimeOffset(Integer.parseInt(presentationTimeOffset));
            }
            String timeShiftBufferDepth=stPullParser.getAttributeValue(null, "timeShiftBufferDepth");
            if (timeShiftBufferDepth!=null) {
                segmentTemplate.SetTimeShiftBufferDepth((timeShiftBufferDepth));
            }
            String indexRange=stPullParser.getAttributeValue(null, "indexRange");
            if (indexRange!=null) {
                segmentTemplate.SetIndexRange((indexRange));
            }
            String indexRangeExact=stPullParser.getAttributeValue(null, "indexRangeExact");
            if (indexRangeExact!=null) {
                segmentTemplate.SetIndexRangeExact(myString.ToBool(indexRangeExact));
            }
            String availabilityTimeOffset=stPullParser.getAttributeValue(null, "availabilityTimeOffset");
            if (availabilityTimeOffset!=null) {
                segmentTemplate.SetAvailabilityTimeOffset(Double.parseDouble(availabilityTimeOffset));
            }
            String availabilityTimeComplete=stPullParser.getAttributeValue(null, "availabilityTimeComplete");
            if (availabilityTimeComplete!=null) {
                segmentTemplate.SetAvailabilityTimeComplete(myString.ToBool(availabilityTimeComplete));
            }
            //ＭultiSegmentBase Part
            String duration=stPullParser.getAttributeValue(null, "duration");
            if (duration!=null) {
                segmentTemplate.SetDuration(Integer.parseInt(duration));
            }
            String startNumber=stPullParser.getAttributeValue(null, "startNumber");
            if (startNumber!=null) {
                segmentTemplate.SetStartNumber(Integer.parseInt(startNumber));
            }
            //SegmentTemplate part
            String media=stPullParser.getAttributeValue(null,"media");
            if(media!=null)
                segmentTemplate.SetMedia(media);
            String initialization=stPullParser.getAttributeValue(null,"initialization");
            if(initialization!=null)
                segmentTemplate.SetInitialization(initialization);
            String index=stPullParser.getAttributeValue(null,"index");
            if(index!=null)
                segmentTemplate.SetIndex(index);
            String bitstreamSwitching=stPullParser.getAttributeValue(null,"bitstreamSwitching");
            if(bitstreamSwitching!=null)
                segmentTemplate.SetBitstreamSwitching(bitstreamSwitching);
            do {
                stPullParser.next();
                //Get SegmentBase.Initialization
                if (stPullParser.getEventType() == XmlPullParser.START_TAG && stPullParser.getName().equals("Initialization")) {
                    URLType init=parseURLType(stPullParser);
                    init.SetType(HTTPTransactionType.InitializationSegment);
                    segmentTemplate.SetInitialization(init);
                }
                //Get SegmentBase.RepresentationIndex
                else if(stPullParser.getEventType() == XmlPullParser.START_TAG && stPullParser.getName().equals("RepresentationIndex")) {
                    URLType repidx=parseURLType(stPullParser);
                    repidx.SetType(HTTPTransactionType.IndexSegment);
                    segmentTemplate.SetRepresentationIndex(repidx);
                }
                //Get NultiSegmentBase.SegmentTimeline
                else if(stPullParser.getEventType() == XmlPullParser.START_TAG && stPullParser.getName().equals("SegmentTimeline")) {
                    SegmentTimeline st=parseSegmentTimeline(stPullParser);
                    segmentTemplate.SetSegmentTimeline(st);
                }
                //Get NultiSegmentBase.BitstreamSwitching
                else if(stPullParser.getEventType() == XmlPullParser.START_TAG && stPullParser.getName().equals("BitstreamSwitching")) {
                    URLType bss=parseURLType(stPullParser);
                    bss.SetType(HTTPTransactionType.BitstreamSwitchingSegment);
                    segmentTemplate.SetBitstreamSwitching(bss);
                }
            } while (!(stPullParser.getEventType() == XmlPullParser.END_TAG && stPullParser.getName().equals("SegmentTemplate")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return segmentTemplate;
    }

    protected SegmentTimeline parseSegmentTimeline(XmlPullParser stPullParser) {
        SegmentTimeline segmentTimeline=new SegmentTimeline();
        try{
            do {
                stPullParser.next();
                if (stPullParser.getEventType() == XmlPullParser.START_TAG && stPullParser.getName().equals("S")) {
                    Timeline timeline=parseTimeline(stPullParser);
                    segmentTimeline.AddTimelines(timeline);
                }
            } while (!(stPullParser.getEventType() == XmlPullParser.END_TAG && stPullParser.getName().equals("SegmentTimeline")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return segmentTimeline;
    }

    protected Timeline parseTimeline(XmlPullParser tlPullParser) {
        Timeline timeline=new Timeline();
        try{
            String t=tlPullParser.getAttributeValue(null,"t");
            if(t!=null)
                timeline.SetStartTime(Integer.parseInt(t));
            String d=tlPullParser.getAttributeValue(null,"d");
            if(d!=null)
                timeline.SetDuration(Integer.parseInt(d));
            String r=tlPullParser.getAttributeValue(null,"r");
            if(r!=null)
                timeline.SetRepeatCount(Integer.parseInt(r));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return timeline;
    }

    protected SegmentURL parseSegmentURL(XmlPullParser suPullParser) {
        SegmentURL segmentURL=new SegmentURL();
        try{
            String media=suPullParser.getAttributeValue(null,"media");
            if(media!=null)
                segmentURL.SetMediaURI(media);
            String mediaRange=suPullParser.getAttributeValue(null,"mediaRange");
            if(mediaRange!=null)
                segmentURL.SetMediaRange(mediaRange);
            String index=suPullParser.getAttributeValue(null,"index");
            if(index!=null)
                segmentURL.SetIndexURI(index);
            String indexRange=suPullParser.getAttributeValue(null,"indexRange");
            if(indexRange!=null)
                segmentURL.SetIndexRange(indexRange);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return segmentURL;
    }

    protected URLType parseURLType(XmlPullParser urltypePullParser) {
        URLType urlType=new URLType();
        try{
            String sourceURL=urltypePullParser.getAttributeValue(null,"sourceURL");
            if(sourceURL!=null)
                urlType.SetSourceURL(sourceURL);
            String range=urltypePullParser.getAttributeValue(null,"range");
            if(range!=null)
                urlType.SetRange(range);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return urlType;
    }

    /*private void buildContentHandler() {
        root.setStartElementListener(new StartElementListener() {
        final Element representation = root.getChild(NAMESPACE, "Period").
                getChild(NAMESPACE, "AdaptationSet").
                getChild(NAMESPACE, "Representation");
        representation.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                Log.w(TAG, "Start representation!");
                // Process only the first representation at this time.in our test, it's the 5th rep
                if ("5".equals(attributes.getValue("id"))) {
                    Log.w(TAG, "Representation 5");
                    // Get the first segment
                    Element initialization = representation.getChild(NAMESPACE, "SegmentBase").
                            getChild(NAMESPACE, "Initialization");
                    initialization.setStartElementListener(new StartElementListener() {
                        @Override
                        public void start(Attributes attributes) {
                            segmentManager.setInitURL(attributes.getValue("sourceURL"));
                            segmentManager.setInitRange(attributes.getValue("range"));
                        }
                    });
                    // Get the others
                    Element segmentURL = representation.getChild(NAMESPACE, "SegmentList").
                            getChild(NAMESPACE, "SegmentURL");
                    segmentURL.setStartElementListener(new StartElementListener() {
                        @Override
                        public void start(Attributes attributes) {
                            segmentManager.addSegment(attributes.getValue("media"));
                            segmentManager.addRanges(attributes.getValue("mediaRange"));
                        }
                    });
                }
            }
        });
    }*/

    public void parse() {
        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream stream = new URL(mpdURL).openConnection().getInputStream();
                    parse(stream);
                    //Xml.parse(stream, Xml.Encoding.UTF_8, root.getContentHandler());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } //catch (SAXException e) {
                   // e.printStackTrace();
                //}
                Log.d(TAG, "Base URL: " + segmentManager.getBaseURL());
                int count = 0;
                while (!segmentManager.isEmpty()) {
                    count ++;
                    Log.v(TAG, "Segment URL: " + segmentManager.getSegment());
                }
                Log.d(TAG, "Segment number: " + count);
            }
        }.start();
    }
}
