package com.example.michal.smssync;




import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import Client.Client;

public class Messenger {

    private Client c;

    public Messenger(String command,boolean registrace) {
        c = Client.getInst();
        c.setActivity(this);
        send(command,registrace);
    }



    public void send(String command,boolean registrace) {
        if (true == isConnected()) {// if(true==m.isConnected()){
            if(registrace==false){
                loginToServer();
            }
            c.send(command);
            System.out.println("Odeslano na server: "+command);
            if(registrace==true){
                loginToServer();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logout();
         } else {
            System.out.println("prvek ma jit do fronty telefon je offline");
            //pokud je prikaz resync nezapisovat do fronty
            //fronta
        }
    }

    public void loginToServer() {
        c.send("loginDevice(" + MainActivity.mainActivity.testNumber + ")");
        System.out.println("loginDevice(" +  MainActivity.mainActivity.testNumber + ")");
    }

    public void logout() {
        c.send("logoutDevice()");
        System.out.println("logoutDevice()");
    }

    public boolean isConnected(){//nefunguje protoze neni v MainActivity
        ConnectivityManager cm =
                (ConnectivityManager) MainActivity.mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        else
            return false;
        }
    }

