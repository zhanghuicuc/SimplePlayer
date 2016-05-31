package com.zhanghui.metric;

import java.io.Serializable;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class TCPConnection implements ITCPConnection ,Serializable {
    //Identifier of the TCP connection on which the HTTP request was sent.
    private int        tcpId;
    //IP Address of the interface over which the client is receiving the TCP data.
    private String     dest;
    //The time at which the connection was opened (sending time of the initial SYN or connect socket operation).
    private String     tOpen;
    //The time at which the connection was closed (sending or reception time of FIN or RST or close socket operation).
    private String    tClose;
    //Connect time in ms (time from sending the initial SYN to receiving the ACK or completion of the connect socket operation).
    //连接用时
    private long        tConnect;

    void    SetTCPId                (int tcpId){
        this.tcpId=tcpId;
    }
    void    SetDestinationAddress   (String destAddress){
        this.dest=destAddress;
    }
    void    SetConnectionOpenedTime (String tOpen){
        this.tOpen=tOpen;
    }
    void    SetConnectionClosedTime (String tClose){
        this.tClose=tClose;
    }
    void    SetConnectionTime       (long tConnect){
        this.tConnect=tConnect;
    }

    public int TCPId(){
        return this.tcpId;
    }
    public String      DestinationAddress      (){
        return this.dest;
    }
    public String      ConnectionOpenedTime    (){
        return this.tOpen;
    }
    public String      ConnectionClosedTime    (){
        return this.tClose;
    }
    public long                ConnectionTime(){
        return this.tConnect;
    }

}
