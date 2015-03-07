/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dojson;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Dominik
 */
public class ContactCo {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String nick;
    @Expose
    private String email;
    @Expose
    private String phonenumberid;

    public ContactCo() {
        System.out.println("commit test 2");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /*
    public P getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(P deviceid) {
        this.deviceid = deviceid;
    }
    */
    public String getPhonenumberid() {
        return phonenumberid;
    }

    public void setPhonenumberid(String phonenumberid) {
        this.phonenumberid = phonenumberid;
    }
    
}
