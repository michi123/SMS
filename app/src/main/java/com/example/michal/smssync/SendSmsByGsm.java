package com.example.michal.smssync;

import android.content.ContentValues;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Michal on 29. 3. 2015.
 */
public class SendSmsByGsm {

    public SendSmsByGsm(String phoneNumber, String messgeText){
        sendSMS(phoneNumber,messgeText);
    }

    public void sendSMS(String phoneNumber, String messgeText) {
        System.out.println("Metoda sendSMS");
        Log.i("Send Sms", "");
        String phoneNo = phoneNumber; // object.getPromena4();//"720391667";  //"725657989";
        String message = messgeText; //object.getPromena2();// "Zkouska aplikace do predmetu ROPR, Michal Kostelecky 29.1.";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            System.out.println("SMS odeslana");
            saveSMStoTelephone(phoneNo, message);
            Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "Sms sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.mainActivity.getApplicationContext(),
                    "SMS faild, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void saveSMStoTelephone(String cislo, String text) {
        //Ukladani sms do odeslanych
        ContentValues values = new ContentValues();
        values.put("address", cislo);
        values.put("body", text);
        MainActivity.mainActivity.getContentResolver().insert(Uri.parse("content://sms/sent"), values);

        System.out.println("sms ulozena");

    }
}
