package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ISegmentTemplate extends IMultipleSegmentBase {
    /**
     *  Returns a reference to a string that specifies the template to create the Media Segment List.\n
     *  For more details on template-based segment URL construction refer to section 5.3.9.4.4 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a string
     */
    public String  Getmedia                    ();

    /**
     *  Returns a reference to a string that specifies the template to create the Index Segment List. If neither the \em \$Number\$ nor the \em \$Time\$ identifier
     *  is included, this provides the URL to a Representation Index. \n
     *  For more details on template-based segment URL construction refer to section 5.3.9.4.4 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a string
     */
    public String  Getindex                    ();

    /**
     *  Returns a reference to a string that specifies the template to create the Initialization Segment. Neither \em \$Number\$ nor the \em \$Time\$ identifier
     *  shall be included. \n
     *  For more details on template-based segment URL construction refer to section 5.3.9.4.4 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a string
     */
    public String  Getinitialization           ();

    /**
     *  Returns a reference to a string that specifies the template to create the Bitstream Switching Segment. Neither \em \$Number\$ nor the \em \$Time\$ identifier shall be included.\n
     *  For more details on template-based segment URL construction refer to section 5.3.9.4.4 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a string
     */
    public String  GetbitstreamSwitching       ();

    /**
     *  Returns a pointer to a dash::mpd::ISegment object that represents an initialization segment and can be downloaded.
     *  @param      baseurls            a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the Initialization Segment (template).
     *  @param      representationID    a string containing the representation ID that will replace the identifier \em \$RepresentationID\$ in the Initialization template. \n
     *                                  \b NOTE:    If there is no identifier \em \$RepresentationID\$ in the template then this parameter will not be used and can be set to \"\".
     *  @param      bandwidth           an integer specifying the bandwidth that will replace the identifier \em \$Bandwidth\$ in the initialization template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Bandwidth\$ identifier. \n
     *                                  \b NOTE:    If there is no identifier \em \$bandwidth\$ in the template then this parameter will not be used and can be set to 0.
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           ToInitializationSegment     (Vector<IBaseUrl> baseurls, String representationID, int bandwidth);

    /**
     *  Returns a pointer to a dash::mpd::ISegment object that represents a Bitstream Switching Segment and can be downloaded.
     *  @param      baseurls            a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the Bitstream Switching Segment (template).
     *  @param      representationID    a string containing the representation ID that will replace the identifier \em \$RepresentationID\$ in the Bitstream Switching template. \n
     *                                  \b NOTE:    If there is no identifier \em \$RepresentationID\$ in the template then this parameter will not be used and can be set to \"\".
     *  @param      bandwidth           an integer specifying the bandwidth that will replace the identifier \em \$Bandwidth\$ in the Bitstream Switching template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Bandwidth\$ identifier.\n
     *                                  \b NOTE:    If there is no identifier \em \$bandwidth\$ in the template then this parameter will not be used and can be set to 0.
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           ToBitstreamSwitchingSegment (Vector<IBaseUrl> baseurls, String representationID, int bandwidth);

    /**
     *  Returns a pointer to a dash::mpd::ISegment object that represents a Media Segment and can be downloaded.
     *  @param      baseurls            a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the Media Segment (template).
     *  @param      representationID    a string containing the representation ID that will replace the identifier \em \$RepresentationID\$ in the Media template.\n
     *                                  \b NOTE:    If there is no identifier \em \$RepresentationID\$ in the template then this parameter will not be used and can be set to \"\".
     *  @param      bandwidth           an integer specifying the bandwidth that will replace the identifier \em \$Bandwidth\$ in the Media template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Bandwidth\$ identifier. \n
     *                                  \b NOTE:    If there is no identifier \em \$bandwidth\$ in the template then this parameter will not be used and can be set to 0.
     *  @param      number              an integer specifying the desired Segment number that will replace the identifier \em \$Number\$ in the Media template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Number\$ identifier.
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           GetMediaSegmentFromNumber   (Vector<IBaseUrl> baseurls, String representationID, int bandwidth, int number);

    /**
     *  Returns a pointer to a dash::mpd::ISegment object that represents a Index Segment and can be downloaded.
     *  @param      baseurls            a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the Index Segment (template).
     *  @param      representationID    a string containing the representation ID that will replace the identifier \em \$RepresentationID\$ in the Index template.\n
     *                                  \b NOTE:    If there is no identifier \em \$RepresentationID\$ in the template then this parameter will not be used and can be set to \"\".
     *  @param      bandwidth           an integer specifying the bandwidth that will replace the identifier \em \$Bandwidth\$ in the Index template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Bandwidth\$ identifier.\n
     *                                  \b NOTE:    If there is no identifier \em \$bandwidth\$ in the template then this parameter will not be used and can be set to 0.
     *  @param      number              an integer specifying the desired Segment number that will replace the identifier \em \$Number\$ in the Index template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Number\$ identifier.
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           GetIndexSegmentFromNumber   (Vector<IBaseUrl> baseurls, String representationID, int bandwidth, int number);

    /**
     *  Returns a pointer to a dash::mpd::ISegment object that represents a Media Segment and can be downloaded.
     *  @param      baseurls            a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the Media Segment (template).
     *  @param      representationID    a string containing the representation ID that will replace the identifier \em \$RepresentationID\$ in the Media template.
     *                                  \b NOTE:    If there is no identifier \em \$RepresentationID\$ in the template then this parameter will not be used and can be set to \"\".
     *  @param      bandwidth           an integer specifying the bandwidth that will replace the identifier \em \$Bandwidth\$ in the Media template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Bandwidth\$ identifier.\n
     *                                  \b NOTE:    If there is no identifier \em \$bandwidth\$ in the template then this parameter will not be used and can be set to 0.
     *  @param      time                an integer corresponding to the <tt><b>SegmentTimeline</b>\@t</tt> attribute that will replace the identifier \em \$Time\$ in the Media template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Time\$ identifier.
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           GetMediaSegmentFromTime     (Vector<IBaseUrl> baseurls, String representationID, int bandwidth, int time);

    /**
     *  Returns a pointer to a dash::mpd::ISegment object that represents a Index Segment and can be downloaded.
     *  @param      baseurls            a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the Index Segment (template).
     *  @param      representationID    a string containing the representation ID that will replace the identifier \em \$RepresentationID\$ in the Index template.\n
     *                                  \b NOTE:    If there is no identifier \em \$RepresentationID\$ in the template then this parameter will not be used and can be set to \"\".
     *  @param      bandwidth           an integer specifying the bandwidth that will replace the identifier \em \$Bandwidth\$ in the Index template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Bandwidth\$ identifier.\n
     *                                  \b NOTE:    If there is no identifier \em \$bandwidth\$ in the template then this parameter will not be used and can be set to 0.
     *  @param      time                an integer corresponding to the <tt><b>SegmentTimeline</b>\@t</tt> attribute that will replace the identifier \em \$Time\$ in the Index template.
     *                                  This integer will be formated according to a possibly contained format tag in the \em \$Time\$ identifier.
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           GetIndexSegmentFromTime     (Vector<IBaseUrl> baseurls, String representationID, int bandwidth, int time);
}
