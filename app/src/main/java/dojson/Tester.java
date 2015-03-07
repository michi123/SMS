/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dojson;

import com.example.michal.smssync.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 *
 * @author Dominik
 */
public class Tester {

    protected  MainActivity mainActivity;

    public Tester(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /*
    public static void main(String[] args) {
       /* JSONBroker broker = new JSONBroker();
        ObjectFactory factory = new ObjectFactory();

      /*  MessageCo mesCo
                = factory.createMessage(
                        "+420 775 414 547",
                        "+420 112 445 778",
                        "nooord meeeaaaad",
                        new Date());

        DeviceCo devCo = factory.createDevice(
                "+420 775 414 547");

        ContactCo conCo = factory.createContact(
                "alfa@gmail.com",
                "Martin",
                "Luther",
                "Cartn",
                "+478 542 112");

     /* MsgPack packedContact = factory.createMsgPack(conCo, conCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.MES);
        MsgPack packedDevice = factory.createMsgPack(devCo, devCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.DEV);
        MsgPack packedMessage = factory.createMsgPack(mesCo, mesCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.CON);

        String encodedMessage = broker.messageToJson(packedMessage);
        String encodedContact = broker.contactToJson(packedContact);
        String encodedDevice = broker.deviceToJson(packedDevice);

        System.out.println(encodedMessage);
        System.out.println(encodedContact);
        System.out.println(encodedDevice);

        /* pro rozpoznani zpravy se vyuzije funkce recognize, ktera zpracuje
        * a vyhodnoti prijatou zpravu.*/
       /* recognize(encodedMessage, broker);
        recognize(encodedContact, broker);
        recognize(encodedDevice, broker);*/
   // }

    public void recognize(String encodedMessage, JSONBroker broker) {
        System.out.println("vchazim do metody recognize");
        //MainActivity mainActivity= new MainActivity().getMainActivity();
        MsgPack extractedPack = broker.extractObject(encodedMessage);
        System.out.println("encodedmessage " +encodedMessage);
        Type collectionType;
        Gson gson = new Gson();
        System.out.println("jdu do switche");
        System.out.println("get type: "+extractedPack.getObjectType());
        switch (extractedPack.getObjectType()) {
            case DEV:
                System.out.println("Device found");
                collectionType = new TypeToken<MsgPack<DeviceCo>>() {}.getType();
                System.out.println(collectionType.toString());
                MsgPack<DeviceCo> extractedPackD = gson.fromJson(encodedMessage, collectionType);
                System.out.println(extractedPackD.toString());
                // Zde se vyuzije vlastni kod pro zpracovani zpravy
                // napr extractedPackD.getObject() vrati objekt typu DeviceCo a ten lze
                // vyuzit k dalsimu zpracovani.

                //ZTM NIC NEMAM NWM JESTE..
                break;
            case MES:
                System.out.println("Message found");
                collectionType = new TypeToken<MsgPack<MessageCo>>() {
                }.getType();
                MsgPack<MessageCo> extractedPackM = gson.fromJson(encodedMessage, collectionType);
                mainActivity.sendSMS1(extractedPackM.getObject().getReciever(),extractedPackM.getObject().getText());
                System.out.println("Odesila se zprava: "+extractedPackM.getObject().getReciever()+"  "+extractedPackM.getObject().getText());

                break;
            case CON:
                System.out.println("Contact found");
                collectionType = new TypeToken<MsgPack<ContactCo>>() {
                }.getType();
                System.out.println(collectionType.toString());
                MsgPack<ContactCo> extractedPackC = gson.fromJson(encodedMessage, collectionType);
                System.out.println(extractedPackC.getObject());
                System.out.println(extractedPackC.getAction());
                // Prostor pro vlasni kod tykajici se zpracovani MessageCo
                break;
            default:
                break;
        };
    }

}
