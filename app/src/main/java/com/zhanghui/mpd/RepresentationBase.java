package com.zhanghui.mpd;

import com.zhanghui.helper.myString;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class RepresentationBase extends AbstractMPDElement implements IRepresentationBase {
    public RepresentationBase() {
        width=0;height=0;
        sar="";
        frameRate="";
        audioSamplingRate="";
        mimeType="";
        maximumSAPPeriod=0.0;
        startWithSAP=0;
        maxPlayoutRate=0.0;
        codingDependency=false;
        scanType="";
        framePacking= new Vector<Descriptor>();
        audioChannelConfiguration= new Vector<Descriptor>();
        contentProtection= new Vector<Descriptor>();
        profiles= new Vector<String>();
        segmentProfiles=new Vector<String>();
        codecs=new Vector<String>();
    }

    @Override
    public Vector<Descriptor> GetFramePacking() {
        return framePacking;
    }

    public void AddFramePacking(Descriptor framePacking) {
        this.framePacking.add(framePacking);
    }

    @Override
    public Vector<Descriptor> GetAudioChannelConfiguration() {
        return audioChannelConfiguration;
    }

    public void AddAudioChannelConfiguration(Descriptor audioChannelConfiguration) {
        this.audioChannelConfiguration.add(audioChannelConfiguration);
    }

    @Override
    public Vector<Descriptor> GetContentProtection() {
        return contentProtection;
    }

    public void AddContentProtection(Descriptor contentProtection) {
        this.contentProtection.add(contentProtection);
    }

    @Override
    public Vector<String> GetProfiles() {
        return profiles;
    }

    public void SetProfiles(String profiles) {
        myString.Split(profiles,",",this.profiles);
    }

    @Override
    public int GetWidth() {
        return width;
    }

    public void SetWidth(int width) {
        this.width = width;
    }

    @Override
    public int GetHeight() {
        return height;
    }

    public void SetHeight(int height) {
        this.height = height;
    }

    @Override
    public String GetSar() {
        return sar;
    }

    public void SetSar(String sar) {
        this.sar = sar;
    }

    @Override
    public String GetFrameRate() {
        return frameRate;
    }

    public void SetFrameRate(String frameRate) {
        this.frameRate = frameRate;
    }

    @Override
    public String GetAudioSamplingRate() {
        return audioSamplingRate;
    }

    public void SetAudioSamplingRate(String audioSamplingRate) {
        this.audioSamplingRate = audioSamplingRate;
    }

    @Override
    public String GetMimeType() {
        return mimeType;
    }

    public void SetMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public Vector<String> GetSegmentProfiles() {
        return segmentProfiles;
    }

    public void SetSegmentProfiles(String segmentProfiles) {
        myString.Split(segmentProfiles,",",this.segmentProfiles);
    }

    @Override
    public Vector<String> GetCodecs() {
        return codecs;
    }

    public void SetCodecs(String codecs) {
        myString.Split(codecs,",",this.codecs);
    }

    @Override
    public double GetMaximumSAPPeriod() {
        return maximumSAPPeriod;
    }

    public void SetMaximumSAPPeriod(double maximumSAPPeriod) {
        this.maximumSAPPeriod = maximumSAPPeriod;
    }

    @Override
    public byte GetStartWithSAP() {
        return startWithSAP;
    }

    public void SetStartWithSAP(byte startWithSAP) {
        this.startWithSAP = startWithSAP;
    }

    @Override
    public double GetMaxPlayoutRate() {
        return maxPlayoutRate;
    }

    public void SetMaxPlayoutRate(double maxPlayoutRate) {
        this.maxPlayoutRate = maxPlayoutRate;
    }

    public boolean HasCodingDependency() {
        return codingDependency;
    }

    public void SetCodingDependency(boolean codingDependency) {
        this.codingDependency = codingDependency;
    }

    @Override
    public String GetScanType() {
        return scanType;
    }

    public void SetScanType(String scanType) {
        this.scanType = scanType;
    }

    protected Vector<Descriptor> framePacking;
    protected Vector<Descriptor>   audioChannelConfiguration;
    protected Vector<Descriptor>   contentProtection;
    protected Vector<String>    profiles;
    protected int                    width;
    protected int                    height;
    protected String                 sar;
    protected String                 frameRate;
    protected String                 audioSamplingRate;
    protected String                 mimeType;
    protected Vector<String>    segmentProfiles;
    protected Vector<String>    codecs;
    protected double                      maximumSAPPeriod;
    protected byte                     startWithSAP;
    protected double                      maxPlayoutRate;
    protected boolean                        codingDependency;
    protected String                 scanType;
}
