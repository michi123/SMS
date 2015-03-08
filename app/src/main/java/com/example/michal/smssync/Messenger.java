package com.example.michal.smssync;


public class Messenger {

    private MainActivity mainActivity;

    public Messenger() {

    }

    public Messenger(MainActivity ma) {
        this.mainActivity = ma;
    }

    public void send(String command) {
        MainActivity m=MainActivity.getMainActivity();
        if(true==m.isConnected()){
            m.sendCommand(command);
        }
        else {
            System.out.println("prvek ma jit do fronty telefon je offline");
            //fronta
        }

    }
}
