/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dojson;

import com.google.gson.annotations.Expose;
import java.util.Date;

/**
 *
 * @author Dominik
 */
public class MessageCo {
    @Expose
    private String text;
    @Expose
    private String reciever;
    @Expose
    private Date sendTime;
    @Expose
    private String sender;
    
    public MessageCo(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
