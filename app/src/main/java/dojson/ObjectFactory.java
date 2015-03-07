/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dojson;

import dojson.MsgPack.ActionType;
import dojson.MsgPack.ObjectType;
import java.util.Date;

/**
 *
 * @author Dominik
 */
public class ObjectFactory<T> {

    public ContactCo createContact(String email, String firstName, String lastName, String nick, String phone) {
        ContactCo mc = new ContactCo();
        mc.setEmail(email);
        mc.setFirstName(firstName);
        mc.setLastName(lastName);
        mc.setNick(nick);
        mc.setPhonenumberid(phone);
        return mc;
    }

    public DeviceCo createDevice(String phone) {
        DeviceCo md = new DeviceCo();
        md.setPhonenumberId(phone);
        return md;
    }

    public MessageCo createMessage(String reciever, String sender, String text, Date sendTime) {
        MessageCo ms = new MessageCo();
        ms.setReciever(reciever);
        ms.setSender(sender);
        ms.setText(text);
        ms.setSendTime(sendTime);
        return ms;
    }
    
    public MsgPack createMsgPack(T object, int hash, ActionType type, ObjectType type2){
        MsgPack<T> pack = new MsgPack();
        pack.setObject(object);
        pack.setHash(hash);
        pack.setAction(type);
        pack.setObjectType(type2);
        return pack;
    }

}
