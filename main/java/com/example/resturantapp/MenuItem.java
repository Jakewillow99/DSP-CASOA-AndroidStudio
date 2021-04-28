package com.example.resturantapp;

public class MenuItem {
    //class created will getter methods and constructor
    int itemID;
    String itemName, itemSection, itemDesc, itemPrice, itemAdditions;

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemSection() {
        return itemSection;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemAdditions() {
        return itemAdditions;
    }

    public MenuItem(int itemID, String itemName, String itemSection, String itemDesc, String itemPrice, String itemAdditions) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemSection = itemSection;
        this.itemDesc = itemDesc;
        this.itemPrice = itemPrice;
        this.itemAdditions = itemAdditions;



    }
}
