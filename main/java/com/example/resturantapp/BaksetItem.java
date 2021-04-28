package com.example.resturantapp;

public class BaksetItem {
    //class created will getter methods and constructor

    int baskID, menuID;
    String baskUser, baskName, baskPrice;

    public int getBaskID() {
        return baskID;
    }

    public int getMenuID() {
        return menuID;
    }

    public String getBaskUser() {
        return baskUser;
    }

    public String getBaskName() {
        return baskName;
    }

    public String getBaskPrice() {
        return baskPrice;
    }

    public BaksetItem(int baskID, int menuID, String baskUser, String baskName, String baskPrice) {
        this.baskID = baskID;
        this.menuID = menuID;
        this.baskUser = baskUser;
        this.baskName = baskName;
        this.baskPrice = baskPrice;
    }
}
