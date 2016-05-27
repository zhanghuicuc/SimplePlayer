package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;
import android.sax.EndElementListener;
import android.sax.StartElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.Descriptor;
import com.zhanghui.mpd.Period;
import com.zhanghui.mpd.Representation;
import com.zhanghui.mpd.SegmentBase;
import com.zhanghui.mpd.URLType;
import com.zhanghui.simpleplayer.SegmentManager;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/20.
 */
public class AdaptationSetNode implements ElementListener {
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";

    AdaptationSet adaptationSet;
    Element as_element;
    Period period;
    SegmentManager segmentManager;
    String mpdURL;
    public AdaptationSetNode(Period period,SegmentManager segmentManager,String mpdURL, Element as_element) {
        this.as_element=as_element;
        this.period=period;
        this.segmentManager=segmentManager;
        this.mpdURL=mpdURL;
    }

    public void start(Attributes attributes) {
        adaptationSet=new AdaptationSet();
        //RepresentationBase part
        if (attributes.getIndex("profiles") != -1) {
            adaptationSet.SetProfiles(attributes.getValue(attributes.getIndex("profiles")));
        }
        if (attributes.getIndex("width") != -1) {
            adaptationSet.SetWidth(Integer.parseInt(attributes.getValue(attributes.getIndex("width"))));
        }
        if (attributes.getIndex("height") != -1) {
            adaptationSet.SetHeight(Integer.parseInt(attributes.getValue(attributes.getIndex("height"))));
        }
        if (attributes.getIndex("sar") != -1) {
            adaptationSet.SetSar((attributes.getValue(attributes.getIndex("sar"))));
        }
        if (attributes.getIndex("frameRate") != -1) {
            adaptationSet.SetFrameRate(attributes.getValue(attributes.getIndex("frameRate")));
        }
        if (attributes.getIndex("audioSamplingRate") != -1) {
            adaptationSet.SetAudioSamplingRate(attributes.getValue(attributes.getIndex("audioSamplingRate")));
        }
        if (attributes.getIndex("mimeType") != -1) {
            adaptationSet.SetMimeType(attributes.getValue(attributes.getIndex("mimeType")));
        }
        if (attributes.getIndex("segmentProfiles") != -1) {
            adaptationSet.SetSegmentProfiles((attributes.getValue(attributes.getIndex("segmentProfiles"))));
        }
        if (attributes.getIndex("codecs") != -1) {
            adaptationSet.SetCodecs((attributes.getValue(attributes.getIndex("codecs"))));
        }
        if (attributes.getIndex("maximumSAPPeriod") != -1) {
            adaptationSet.SetMaximumSAPPeriod(Double.parseDouble(attributes.getValue(attributes.getIndex("maximumSAPPeriod"))));
        }
        if (attributes.getIndex("startWithSAP") != -1) {
            adaptationSet.SetStartWithSAP(Byte.parseByte(attributes.getValue(attributes.getIndex("startWithSAP"))));
        }
        if (attributes.getIndex("maxPlayoutRate") != -1) {
            adaptationSet.SetMaxPlayoutRate(Double.parseDouble(attributes.getValue(attributes.getIndex("maxPlayoutRate"))));
        }
        if (attributes.getIndex("codingDependency") != -1) {
            adaptationSet.SetCodingDependency(myString.ToBool(attributes.getValue(attributes.getIndex("codingDependency"))));
        }
        if (attributes.getIndex("scanType") != -1) {
            adaptationSet.SetScanType(attributes.getValue(attributes.getIndex("scanType")));
        }
        //Get RepresentationBase.FramePacking
        Element framePacking=as_element.getChild(NAMESPACE,"FramePacking");
        DescriptorNode toFramePacking=new DescriptorNode(adaptationSet,null,null,null,null,"FramePacking");
        framePacking.setElementListener(toFramePacking);
        //Get RepresentationBase.AudioChannelConfiguration
        Element audioChannelConfiguration=as_element.getChild(NAMESPACE,"AudioChannelConfiguration");
        DescriptorNode toAudioChannelConfiguration=new DescriptorNode(adaptationSet,null,null,null,null,"AudioChannelConfiguration");
        audioChannelConfiguration.setElementListener(toAudioChannelConfiguration);
        //Get RepresentationBase.Accessibility
        Element contentProtection=as_element.getChild(NAMESPACE,"ContentProtection");
        DescriptorNode toContentProtection=new DescriptorNode(adaptationSet,null,null,null,null,"ContentProtection");
        contentProtection.setElementListener(toContentProtection);

        //AdaptationSet part
        if (attributes.getIndex("xlink:href") != -1) {
            adaptationSet.SetXlinkHref(attributes.getValue(attributes.getIndex("xlink:href")));
        }
        if (attributes.getIndex("xlink:actuate") != -1) {
            adaptationSet.SetXlinkActuate(attributes.getValue(attributes.getIndex("xlink:actuate")));
        }
        if (attributes.getIndex("id") != -1) {
            adaptationSet.SetId(Integer.parseInt(attributes.getValue(attributes.getIndex("id"))));
        }
        if (attributes.getIndex("group") != -1) {
            adaptationSet.SetGroup(Integer.parseInt(attributes.getValue(attributes.getIndex("group"))));
        }
        if (attributes.getIndex("lang") != -1) {
            adaptationSet.SetLang(attributes.getValue(attributes.getIndex("lang")));
        }
        if (attributes.getIndex("contentType") != -1) {
            adaptationSet.SetContentType(attributes.getValue(attributes.getIndex("contentType")));
        }
        if (attributes.getIndex("par") != -1) {
            adaptationSet.SetPar(attributes.getValue(attributes.getIndex("par")));
        }
        if (attributes.getIndex("minBandwidth") != -1) {
            adaptationSet.SetMinBandwidth(Integer.parseInt(attributes.getValue(attributes.getIndex("minBandwidth"))));
        }
        if (attributes.getIndex("maxBandwidth") != -1) {
            adaptationSet.SetMaxBandwidth(Integer.parseInt(attributes.getValue(attributes.getIndex("maxBandwidth"))));
        }
        if (attributes.getIndex("minWidth") != -1) {
            adaptationSet.SetMinWidth(Integer.parseInt(attributes.getValue(attributes.getIndex("minWidth"))));
        }
        if (attributes.getIndex("maxWidth") != -1) {
            adaptationSet.SetMaxWidth(Integer.parseInt(attributes.getValue(attributes.getIndex("maxWidth"))));
        }
        if (attributes.getIndex("minHeight") != -1) {
            adaptationSet.SetMinHeight(Integer.parseInt(attributes.getValue(attributes.getIndex("minHeight"))));
        }
        if (attributes.getIndex("maxHeight") != -1) {
            adaptationSet.SetMaxHeight(Integer.parseInt(attributes.getValue(attributes.getIndex("maxHeight"))));
        }
        if (attributes.getIndex("minFrameRate") != -1) {
            adaptationSet.SetMinFramerate(attributes.getValue(attributes.getIndex("minFrameRate")));
        }
        if (attributes.getIndex("maxFrameRate") != -1) {
            adaptationSet.SetMaxFramerate(attributes.getValue(attributes.getIndex("maxFrameRate")));
        }
        if (attributes.getIndex("segmentAlignment") != -1) {
            adaptationSet.SetSegmentAlignment(Integer.parseInt(attributes.getValue(attributes.getIndex("segmentAlignment"))));
        }
        if (attributes.getIndex("subsegmentAlignment") != -1) {
            adaptationSet.SetSubsegmentAlignment( Integer.parseInt(attributes.getValue(attributes.getIndex("subsegmentAlignment"))));
        }
        if (attributes.getIndex("subsegmentStartsWithSAP") != -1) {
            adaptationSet.SetSubsegmentStartsWithSAP(Byte.parseByte(attributes.getValue(attributes.getIndex("subsegmentStartsWithSAP"))));
        }
        if (attributes.getIndex("bitstreamSwitching") != -1) {
            adaptationSet.SetIsBitstreamSwitching(myString.ToBool(attributes.getValue(attributes.getIndex("bitstreamSwitching"))));
        }
        //Get AdaptationSet.Accessibility
        Element accessibility=as_element.getChild(NAMESPACE,"Accessibility");
        DescriptorNode toAccessibility=new DescriptorNode(adaptationSet,null,null,null,null,"Accessibility");
        accessibility.setElementListener(toAccessibility);
        //Get AdaptationSet.Role
        Element role=as_element.getChild(NAMESPACE,"Role");
        DescriptorNode toRole=new DescriptorNode(adaptationSet,null,null,null,null,"Role");
        role.setElementListener(toRole);
        //Get AdaptationSet.Rating
        Element rating=as_element.getChild(NAMESPACE,"Rating");
        DescriptorNode toRating=new DescriptorNode(adaptationSet,null,null,null,null,"Rating");
        rating.setElementListener(toRating);
        //Get AdaptationSet.Viewpoint
        Element viewpoint=as_element.getChild(NAMESPACE,"Viewpoint");
        DescriptorNode toViewpoint=new DescriptorNode(adaptationSet,null,null,null,null,"Viewpoint");
        viewpoint.setElementListener(toViewpoint);
        //Get AdaptationSet.ContentComponent
        Element contentComponent=as_element.getChild(NAMESPACE,"ContentComponent");
        ContentComponentNode toContentComponent=new ContentComponentNode(adaptationSet,contentComponent);
        contentComponent.setElementListener(toContentComponent);
        //Get AdaptationSet.BaseURL
        Element baseURL=as_element.getChild(NAMESPACE,"BaseURL");
        BaseUrlNode toBaseUrl=new BaseUrlNode(null,null,adaptationSet,null,segmentManager,mpdURL);
        baseURL.setStartElementListener(toBaseUrl);
        baseURL.setEndTextElementListener(toBaseUrl);
        //Get AdaptationSet.SegmentBase
        Element segmentBase=as_element.getChild(NAMESPACE,"SegmentBase");
        SegmentBaseNode toSegmentBase=new SegmentBaseNode(null,this.adaptationSet,null,segmentBase);
        segmentBase.setElementListener(toSegmentBase);
        //Get AdaptationSet.SegmentList
        Element segmentList=as_element.getChild(NAMESPACE,"SegmentList");
        SegmentListNode toSegmentList=new SegmentListNode(null,this.adaptationSet,null,segmentList);
        segmentList.setElementListener(toSegmentList);
        //Get AdaptationSet.SegmentTemplate
        Element segmentTemplate=as_element.getChild(NAMESPACE,"SegmentTemplate");
        SegmentTemplateNode toSegmentTemplate=new SegmentTemplateNode(null,this.adaptationSet,null,segmentTemplate);
        segmentTemplate.setElementListener(toSegmentTemplate);
        //Get AdaptationSet.Representation
        final Element rep=as_element.getChild(NAMESPACE,"Representation");
        final Representation representation=new Representation();
        rep.setStartElementListener(new StartElementListener() {

            @Override
            public void start(Attributes attributes) {
                //representation=new Representation();
                //RepresentationBase part
                if (attributes.getIndex("profiles") != -1) {
                    representation.SetProfiles(attributes.getValue(attributes.getIndex("profiles")));
                }
                if (attributes.getIndex("width") != -1) {
                    representation.SetWidth(Integer.parseInt(attributes.getValue(attributes.getIndex("width"))));
                }
                if (attributes.getIndex("height") != -1) {
                    representation.SetHeight(Integer.parseInt(attributes.getValue(attributes.getIndex("height"))));
                }
                if (attributes.getIndex("sar") != -1) {
                    representation.SetSar((attributes.getValue(attributes.getIndex("sar"))));
                }
                if (attributes.getIndex("frameRate") != -1) {
                    representation.SetFrameRate(attributes.getValue(attributes.getIndex("frameRate")));
                }
                if (attributes.getIndex("audioSamplingRate") != -1) {
                    representation.SetAudioSamplingRate(attributes.getValue(attributes.getIndex("audioSamplingRate")));
                }
                if (attributes.getIndex("mimeType") != -1) {
                    representation.SetMimeType(attributes.getValue(attributes.getIndex("mimeType")));
                }
                if (attributes.getIndex("segmentProfiles") != -1) {
                    representation.SetSegmentProfiles((attributes.getValue(attributes.getIndex("segmentProfiles"))));
                }
                if (attributes.getIndex("codecs") != -1) {
                    representation.SetCodecs((attributes.getValue(attributes.getIndex("codecs"))));
                }
                if (attributes.getIndex("maximumSAPPeriod") != -1) {
                    representation.SetMaximumSAPPeriod(Double.parseDouble(attributes.getValue(attributes.getIndex("maximumSAPPeriod"))));
                }
                if (attributes.getIndex("startWithSAP") != -1) {
                    representation.SetStartWithSAP(Byte.parseByte(attributes.getValue(attributes.getIndex("startWithSAP"))));
                }
                if (attributes.getIndex("maxPlayoutRate") != -1) {
                    representation.SetMaxPlayoutRate(Double.parseDouble(attributes.getValue(attributes.getIndex("maxPlayoutRate"))));
                }
                if (attributes.getIndex("codingDependency") != -1) {
                    representation.SetCodingDependency(myString.ToBool(attributes.getValue(attributes.getIndex("codingDependency"))));
                }
                if (attributes.getIndex("scanType") != -1) {
                    representation.SetScanType(attributes.getValue(attributes.getIndex("scanType")));
                }
                //Get RepresentationBase.FramePacking
                Element framePacking = rep.getChild(NAMESPACE, "FramePacking");
                final Descriptor framePacking_descriptor=new Descriptor();
                framePacking.setStartElementListener(new StartElementListener() {
                    @Override
                    public void start(Attributes attributes) {
                        if (attributes.getIndex("schemeIdUri") != -1) {
                            framePacking_descriptor.SetSchemeIdUri(attributes.getValue(attributes.getIndex("schemeIdUri")));
                        }
                        if (attributes.getIndex("value") != -1) {
                            framePacking_descriptor.SetValue(attributes.getValue(attributes.getIndex("value")));
                        }
                        if (attributes.getIndex("id") != -1) {
                            framePacking_descriptor.SetId(attributes.getValue(attributes.getIndex("id")));
                        }
                    }
                });
                framePacking.setEndElementListener(new EndElementListener() {
                    @Override
                    public void end() {
                        representation.AddFramePacking(framePacking_descriptor);
                    }
                });
                /*DescriptorNode toFramePacking=new DescriptorNode(null,null,representation,null,null,"FramePacking");
                framePacking.setElementListener(toFramePacking);

                //Get RepresentationBase.AudioChannelConfiguration
                Element audioChannelConfiguration=rep.getChild(NAMESPACE,"AudioChannelConfiguration");
                DescriptorNode toAudioChannelConfiguration=new DescriptorNode(null,null,representation,null,null,"AudioChannelConfiguration");
                audioChannelConfiguration.setElementListener(toAudioChannelConfiguration);
                //Get RepresentationBase.Accessibility
                Element contentProtection=rep.getChild(NAMESPACE,"ContentProtection");
                DescriptorNode toContentProtection=new DescriptorNode(null,null,representation,null,null,"ContentProtection");
                contentProtection.setElementListener(toContentProtection);*/

                //Representation Part
                if (attributes.getIndex("id") != -1) {
                    representation.SetId(attributes.getValue(attributes.getIndex("id")));
                }
                if (attributes.getIndex("bandwidth") != -1) {
                    representation.SetBandwidth(Integer.parseInt(attributes.getValue(attributes.getIndex("bandwidth"))));
                }
                if (attributes.getIndex("qualityRanking") != -1) {
                    representation.SetQualityRanking(Integer.parseInt(attributes.getValue(attributes.getIndex("qualityRanking"))));
                }
                if (attributes.getIndex("dependencyId") != -1) {
                    representation.SetDependencyId((attributes.getValue(attributes.getIndex("dependencyId"))));
                }
                if (attributes.getIndex("mediaStreamStructureId") != -1) {
                    representation.SetMediaStreamStructureId(attributes.getValue(attributes.getIndex("mediaStreamStructureId")));
                }
                //Get Representation.BaseURL
                /*Element baseURL=rep.getChild(NAMESPACE,"BaseURL");
                BaseUrlNode toBaseUrl=new BaseUrlNode(null,null,null,representation,segmentManager,mpdURL);
                baseURL.setStartElementListener(toBaseUrl);
                baseURL.setEndTextElementListener(toBaseUrl);
                //Get Representation.SubRepresentation
                Element subRepresentation=rep.getChild(NAMESPACE,"SubRepresentation");
                SubRepresentationNode toSubRepresentation=new SubRepresentationNode(representation,subRepresentation);
                subRepresentation.setElementListener(toSubRepresentation);*/
                //Get Representation.SegmentBase
                final Element segmentbase=rep.getChild(NAMESPACE,"SegmentBase");
                final SegmentBase segmentBase=new SegmentBase();
                segmentbase.setStartElementListener(new StartElementListener() {
                    @Override
                    public void start(Attributes attributes) {
                        if (attributes.getIndex("timescale") != -1) {
                            segmentBase.SetTimescale(Integer.parseInt(attributes.getValue(attributes.getIndex("timescale"))));
                        }
                        if (attributes.getIndex("presentationTimeOffset") != -1) {
                            segmentBase.SetPresentationTimeOffset(Integer.parseInt(attributes.getValue(attributes.getIndex("presentationTimeOffset"))));
                        }
                        if (attributes.getIndex("timeShiftBufferDepth") != -1) {
                            segmentBase.SetTimeShiftBufferDepth(attributes.getValue(attributes.getIndex("timeShiftBufferDepth")));
                        }
                        if (attributes.getIndex("indexRange") != -1) {
                            segmentBase.SetIndexRange(attributes.getValue(attributes.getIndex("indexRange")));
                        }
                        if (attributes.getIndex("indexRangeExact") != -1) {
                            segmentBase.SetIndexRangeExact(myString.ToBool((attributes.getValue(attributes.getIndex("indexRangeExact")))));
                        }
                        if (attributes.getIndex("availabilityTimeOffset") != -1) {
                            segmentBase.SetAvailabilityTimeOffset(Double.parseDouble(attributes.getValue(attributes.getIndex("availabilityTimeOffset"))));
                        }
                        if (attributes.getIndex("availabilityTimeComplete") != -1) {
                            segmentBase.SetAvailabilityTimeComplete(myString.ToBool(attributes.getValue(attributes.getIndex("availabilityTimeComplete"))));
                        }
                        //Get SegmentBase.Initialization
                        final Element initialization=segmentbase.getChild(NAMESPACE,"Initialization");
                        final URLType urlType=new URLType();
                        initialization.setStartElementListener(new StartElementListener() {
                            @Override
                            public void start(Attributes attributes) {

                                urlType.SetType(HTTPTransactionType.InitializationSegment);
                                if (attributes.getIndex("sourceURL") != -1) {
                                    urlType.SetSourceURL(attributes.getValue(attributes.getIndex("sourceURL")));
                                }
                                if (attributes.getIndex("range") != -1) {
                                    urlType.SetRange(attributes.getValue(attributes.getIndex("range")));
                                }
                            }
                        });
                        initialization.setEndElementListener(new EndElementListener() {
                            @Override
                            public void end() {
                                segmentBase.SetInitialization(urlType);
                            }
                        });
                        /*URLTypeNode toInitialization=new URLTypeNode(this.segmentBase,null, null, HTTPTransactionType.InitializationSegment);
                        initialization.setElementListener(toInitialization);*/
                        //Get SegmentBase.RepresentationIndex
                        Element representationIndex=segmentbase.getChild(NAMESPACE,"RepresentationIndex");
                        final URLType urlType1=new URLType();
                        representationIndex.setStartElementListener(new StartElementListener() {
                            @Override
                            public void start(Attributes attributes) {

                                urlType1.SetType(HTTPTransactionType.IndexSegment);
                                if (attributes.getIndex("sourceURL") != -1) {
                                    urlType1.SetSourceURL(attributes.getValue(attributes.getIndex("sourceURL")));
                                }
                                if (attributes.getIndex("range") != -1) {
                                    urlType1.SetRange(attributes.getValue(attributes.getIndex("range")));
                                }
                            }
                        });
                        representationIndex.setEndElementListener(new EndElementListener() {
                            @Override
                            public void end() {
                                segmentBase.SetRepresentationIndex(urlType1);
                            }
                        });
                        //URLTypeNode toRepresentationIndex=new URLTypeNode(this.segmentBase,null,null,HTTPTransactionType.IndexSegment);
                        //representationIndex.setElementListener(toRepresentationIndex);
                    }
                });
                segmentbase.setEndElementListener(new EndElementListener() {
                    @Override
                    public void end() {
                        representation.SetSegmentBase(segmentBase);
                    }
                });
                //SegmentBaseNode toSegmentBase=new SegmentBaseNode(null,null,representation,segmentBase);
                //segmentBase.setElementListener(toSegmentBase);
                //Get Representation.SegmentList
                /*Element segmentList=rep.getChild(NAMESPACE,"SegmentList");
                SegmentListNode toSegmentList=new SegmentListNode(null,null,representation,segmentList);
                segmentList.setElementListener(toSegmentList);
                //Get Representation.SegmentTemplate
                /*Element segmentTemplate=rep.getChild(NAMESPACE,"SegmentTemplate");
                SegmentTemplateNode toSegmentTemplate=new SegmentTemplateNode(null,null,representation,segmentTemplate);
                segmentTemplate.setElementListener(toSegmentTemplate);*/
            }
        });
        rep.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                adaptationSet.AddRepresentation(representation);
            }
        });

        //RepresentationNode toRepresentation=new RepresentationNode(this.adaptationSet,segmentManager,mpdURL,representation,as_element);
        //representation.setElementListener(toRepresentation);
    }

    public void end() {
        period.AddAdaptationSets(this.adaptationSet);

    }
}
