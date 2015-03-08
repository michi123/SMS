package com.example.michal.smssync;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.net.NetworkInfo;
import Client.Client;
import Contact.GetContactsDemo;
import android.telephony.TelephonyManager;


public class MainActivity extends Activity {
    IntentFilter intentFilter;
    Messenger me;
    private Client c;
    private boolean isOpen = false;
    private static MainActivity mainActivity = null;

    private BroadcastReceiver intentReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView intTxt = (TextView) findViewById(R.id.textMsg);
            intTxt.setText(intent.getExtras().getString("sms"));
        }
    };

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public void setMainActivity() {
        this.mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        c = Client.getInst();
        c.setActivity(this);
        while (!isOpen) { //zkusit smazat
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        setMainActivity();
        me = new Messenger(this);
      System.out.println("Moje cislo: "+getMy10DigitPhoneNumber());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkContact(View v){// funguje (pripojeni k wifi)
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            System.out.println("Jsem připojen k internetu");
        }
        else {
            System.out.println("Nejsem připojen k internetu");
        }
    }
    public boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void loginToServer(View v) {// dodelat at si telefoni cislo bere ze simkarty!!!!!!!!!!!!!!!!!
        c.send("loginDevice(+420 739 096 145)");
        System.out.println("loginDevice(+420 739 096 145)");
    }
    public static MainActivity getMainActivity(){
        return mainActivity;
    }


    public void sendCommand(String command) {
        c.send(command);
        System.out.println("Odeslano: " + command);
    }

    public void logout(View v) {
        c.send("logoutDevice()");
        System.out.println("logoutDevice()");
    }

    public void sendSMS1(String phoneNumber, String messgeText) {
        System.out.println("Metoda sendSMS");
        Log.i("Send SMS", "");
        String phoneNo = phoneNumber; // object.getPromena4();//"720391667";  //"725657989";
        String message = messgeText; //object.getPromena2();// "Zkouska aplikace do predmetu ROPR, Michal Kostelecky 29.1.";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            System.out.println("SMS odeslana");
            saveSMStoTelephone1(phoneNo, message);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void saveSMStoTelephone1(String cislo, String text) {
        //Ukladani sms do odeslanych
        ContentValues values = new ContentValues();
        values.put("address", cislo);
        values.put("body", text);
        getContentResolver().insert(Uri.parse("content://sms/sent"), values);

        System.out.println("sms ulozena");

    }

    public void loadSMS(View v) {
        //nacteni sms od kontaktu z telefonu
        Uri uri = Uri.parse("content://sms/");
        ContentResolver contentResolver = getContentResolver();
        String phoneNumber = "0123456789";
        String sms = "address='" + phoneNumber + "'";
        Cursor cursor = contentResolver.query(uri, new String[]{"_id", "body"}, sms, null, null);
        System.out.println(cursor.getCount());
        while (cursor.moveToNext()) {
            String strbody = cursor.getString(cursor.getColumnIndex("body"));
            System.out.println(strbody);
        }cursor.close();//tady nekde dat cursor.close()
    }
    private String getMyPhoneNumber(){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getSimSerialNumber();
    }

    private String getMy10DigitPhoneNumber(){
        String s = getMyPhoneNumber();
        return s.substring(0);
    }

    public void loadContact(View v) {
        //nacteni vsech kontaktu
        GetContactsDemo contactsDemo = new GetContactsDemo();
        contactsDemo.readContacts(getContentResolver());
    }

    //receiver
    @Override
    protected void onResume() {
        registerReceiver(intentReciever, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReciever);
        super.onPause();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


}

