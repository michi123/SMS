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
        m.sendCommand(command);
    }
}
