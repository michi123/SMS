package com.example.michal.smssync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Date;

import dojson.JSONBroker;
import dojson.MessageCo;
import dojson.MsgPack;
import dojson.ObjectFactory;


/**
 * Created by Michal on 10. 2. 2015.
 */
public class SMSReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;
        String str = "";
        String sender = null;
        String text = null;
        if (bundle != null) {
            java.lang.Object[] pdus = (java.lang.Object[]) bundle.get(("pdus"));
            messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += "Message from " + messages[i].getOriginatingAddress();
                sender = messages[i].getOriginatingAddress();  //tadyto preposlat do objektu
                str += " :";
                str += messages[i].getMessageBody().toString();
                text = messages[i].getMessageBody().toString();   // tadyto preposlat do objektu
                str += "\n";
            }//display the message
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            System.out.println("Zprava zachycena: " + sender + " text: " + text);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);
            toObject(sender, text);

        }
    }

    private void toObject(String sender, String text) {
        ObjectFactory factory = new ObjectFactory();
        JSONBroker broker = new JSONBroker();
        MessageCo mesCo = factory.createMessage(
                MainActivity.mainActivity.testNumber,//prijemce
                sender,//odesilatel
                text,//text
                new Date());//datum
        MsgPack packedMessage = factory.createMsgPack(mesCo, mesCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.MES);
        new Messenger(broker.messageToJson(packedMessage),false);

    }

}
