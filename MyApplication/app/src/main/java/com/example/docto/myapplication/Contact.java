package com.example.docto.myapplication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Mukotisi on 11/2/2017.
 */

public class Contact {
    String name;
    String surname;
    String address;

    public Contact(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public static ArrayList<Contact> getContacts(String gsonString){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Contact>>(){}.getType();
        return gson.fromJson(gsonString,type);
    }
}
