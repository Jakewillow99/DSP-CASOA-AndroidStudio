package com.example.resturantapp;

import android.app.UiModeManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.BoringLayout;
import android.text.format.Time;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Set Variables Used.
    public static final String DATABASE_NAME = "RESTURANTAPP.db";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "UNAME";
    public static final String COL_3 = "PWORD";
    public static final String COL_4 = "ROLE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //onCreate will create tables in the database in case the database can't be found
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USERS (STAFFID INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PWORD TEXT, NAME TEXT, ROLE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE CUSTOMUSER (CUSTOMID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PWORD TEXT, PHONENO INTEGER, EMAIL TEXT, ROLE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE MENU (ITEMID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT, PRICE FLOAT, ADDITIONS BOOLEAN)");
        sqLiteDatabase.execSQL("CREATE TABLE TABLES (TABLENO TEXT PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE CURRENTORDERS (ORDERID INTEGER PRIMARY KEY AUTOINCREMENT, TABLENO TEXT, ORDERCON TEXT, TIMEPLACED TEXT, TAKENBY TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE COMPLETEORDERS (ORDERID INTEGER PRIMARY KEY AUTOINCREMENT, TABLENO TEXT, ORDERCON TEXT, TIMEPLACED TEXT, TAKENBY TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + "USERS");
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + "CUSTOMUSER");
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + "MENU");
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + "TABLES");
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + "CURRENTORDERS");
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + "COMPLETEORDERS");
        onCreate(sqLiteDatabase);
    }

    //addCustomer will add the contents provided by the user and add the details to the database
    //if all the details are correct.
    public long addCustomer(String user, String passw, String phoneno, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", user);
        contentValues.put("PWORD", passw);
        contentValues.put("PHONENO", phoneno);
        contentValues.put("EMAIL", email);
        contentValues.put("ROLE", "CUSTOMER");
        long res = db.insert("CUSTOMUSER", null, contentValues);
        db.close();
        return res;
    }

    //Similar to add customer if all the details are correct this method will add a new
    //staff member to the database.
    public long addStaff(String user, String passw, String name, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UNAME", user);
        contentValues.put("PWORD", passw);
        contentValues.put("ROLE", role);
        contentValues.put("NAME", name);
        long res = db.insert("USERS", null, contentValues);
        db.close();
        return res;
    }

    //when a customer or staff member logs in the method checks if the username and password
    //match an existing record on the database
    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("USERS", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;

    }

    //when a customer or staff member makes an account this methid checks if a username already exists
    public boolean checkUsername(String username) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        onCreate(db);
        String selection = COL_2 + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("USERS", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
            return false;
        else
            return true;
    }

    //This method when provided with a name will remove that record from the database
    public boolean deleteAccount(String name){
        SQLiteDatabase db = getWritableDatabase();
        String Where = "NAME=?";
        String whereArgs[] = {name};
        db.delete("USERS",Where, whereArgs);
        db.close();
        String[] columns = {"ID"};
        SQLiteDatabase db2 = getReadableDatabase();
        String selection = "NAME" + "=?";
        String[] selectionArgs = {name};
        Cursor cursor = db2.query("USERS", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return false;
        else
            return true;
    }

    //Using a staff members name this method will return their StaffID
    public int getIDFromName(String name){
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;
        String query = "Select * From USERS Where NAME = '"+name+"'";
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()) {
            do {
                id = c.getInt(0);
            }  while(c.moveToNext());
        }
        c.close();
        db.close();
        return id;
    }

    //the reverce of the method before, using a staffs ID this method obtains their name
    public String getUNAMEFromID(int id){
        SQLiteDatabase db = getReadableDatabase();
        String uname = null;
        String query = "Select * From USERS Where ID = '"+id+"'";
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()) {
            do {
                uname = c.getString(1);
            }  while(c.moveToNext());
        }
        c.close();
        db.close();
        return uname;
    }

    //This method obtains a staff members password from their ID
    public String getPWORDFromID(int id){
        SQLiteDatabase db = getReadableDatabase();
        String pword = null;
        String query = "Select * From USERS Where ID = '"+id+"'";
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()) {
            do {
                pword = c.getString(2);
            }  while(c.moveToNext());
        }
        c.close();
        db.close();
        return pword;
    }

    //this method when provided with the correct information will update an exising record based on the ID
    public boolean updateAccount(int id, String user, String pass, String name, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Update USERS " +
                "Set UNAME = '" +user + "' , PWORD = '" +pass+ "' , NAME = '" +name+ "' , ROLE = '" +role+
                "' Where ID = " +id;
        Cursor c = db.rawQuery(query,null);
        int count = c.getCount();
        c.close();
        db.close();
        if (count > 0)
            return false;
        else
            return true;
    }

    //this method returns all users from the staff table
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * From USERS";
        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //this method returns all orders from the current orders table
    public Cursor viewOrder(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * From CURRENTORDER";
        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //this method returns all orders from the complete orders table
    public Cursor viewOrderComp(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * From COMPLETEORDERS";
        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //this method returns all tables from the tables table
    public Cursor viewTables(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * From TABLES";
        Cursor c = db.rawQuery(query, null);

        return c;

    }

    //This method returns the number of items in the menu table
    public int getMenuCount(){
        SQLiteDatabase db = getReadableDatabase();
        int count;
        String query = "Select Distinct ITEMID From MENU";
        Cursor c = db.rawQuery(query,null);
        count = c.getCount();
        c.close();
        db.close();
        return count;
    }

    //this method will return the full menu item details by ID
    public MenuItem getItemsByID(int id){
        SQLiteDatabase db = getReadableDatabase();
        String name;
        String section;
        String desc;
        String price;
        String additions;
        MenuItem item = null;
        String query = "Select * From MENU Where ITEMID = '" +id+ "'";
        Cursor c = db.rawQuery(query,null);
        while(c.moveToNext()){
             name = c.getString(1);
             section = c.getString(2);
             desc = c.getString(3);
             price = c.getString(4);
             additions = c.getString(5);
             item = new MenuItem(id, name, section, desc, price, additions);
        }
        c.close();
        db.close();
        return item;
    }

    //this method will return the menu item name by ID
    public String getMenuNameByID(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * From MENU Where ITEMID = '"+id+"'";
        Cursor c = db.rawQuery(query,null);
        String res = "";
        while(c.moveToNext()){
            res = c.getString(1);

        }
        c.close();
        db.close();
        return res;
    }

    //this method will return the full menu item section by ID
    public Boolean getSectionbyID(int id, String selection){
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * From MENU Where ITEMID = '"+id+"'";
        Cursor c = db.rawQuery(query,null);
        boolean count = false;
        while(c.moveToNext()){
            if(selection.equals(c.getString(2))){
                count = true;
            }
        }
        c.close();
        db.close();
        return count;
    }

    //This method uploads the order in the users basket to the current orders table.
    public long addOrderToCurrent(String user, String table, ArrayList<Integer> contents){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String menuItems = "";
        for (int i = 0; i < contents.size(); i++){
            if (i>0){
                menuItems = menuItems+"-";
            }
            menuItems = menuItems+contents.get(i);
        }
        Time now = new Time();
        now.setToNow();
        int hour = now.hour;
        int min = now.minute;
        String time = hour+":"+min;

        contentValues.put("TABLENO", table);
        contentValues.put("ORDERCON", menuItems);
        contentValues.put("TIMEPLACED", time);
        contentValues.put("TAKENBY", user);

        long res = db.insert("CURRENTORDER", null, contentValues);
        db.close();
        return res;

    }

    public ArrayList<String> getOrderContents(String selectedID, String sTable){
        ArrayList<String> orderCon = new ArrayList<>();
        String wholeOrder = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * From "+sTable+" Where ORDERID = '"+selectedID+"'";
        Cursor c = db.rawQuery(query,null);
        while(c.moveToNext()){
            wholeOrder = c.getString(2);
        }
        c.close();

        String[] parts = wholeOrder.split("-");
        for (int i = 0; i < parts.length; i++){
            String query2 = "Select * From MENU Where ITEMID = '"+parts[i]+"'";
            Cursor c2 = db.rawQuery(query2,null);
            while(c2.moveToNext()){
                orderCon.add(c2.getString(1));
            }
            c.close();
        }

        db.close();
        return orderCon;
    }

    public int getMenuID(String itemName, String sTable) {
        int ID = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * From "+sTable+" Where ITEMNAME = '" + itemName + "'";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            ID = c.getInt(0);
        }
        c.close();
        db.close();
        return ID;
    }

    public String getOrderTime(String selectedID, String sTable) {
        String time = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * From "+sTable+" Where ORDERID = '" + selectedID + "'";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            time = c.getString(3);
        }
        c.close();
        db.close();
        return time;
    }

    public String getOrderTimeComp(String selectedID, String sTable) {
        String time = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * From "+sTable+" Where ORDERID = '" + selectedID + "'";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            time = c.getString(5);
        }
        c.close();
        db.close();
        return time;
    }

    public String getOrderPlacedBy(String selectedID, String sTable) {
        String placedBy = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * From "+sTable+" Where ORDERID = '" + selectedID + "'";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            placedBy = c.getString(4);
        }
        c.close();
        db.close();
        return placedBy;
    }

    public String getOrderTableNo(String selectedID, String sTable) {
        String table = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * From "+sTable+" Where ORDERID = '" + selectedID + "'";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            table = c.getString(1);
        }
        c.close();
        db.close();
        return table;
    }

    public long addOrderToComplete(String selectedID, String table, String time, String user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String wholeOrder = "";
        String query = "Select * From CURRENTORDER Where ORDERID = '"+selectedID+"'";
        Cursor c = db.rawQuery(query,null);
        while(c.moveToNext()){
            wholeOrder = c.getString(2);
        }
        c.close();
        Time now = new Time();
        now.setToNow();
        int hour = now.hour;
        int min = now.minute;
        String timecomp = hour+":"+min;
        contentValues.put("ORDERID", selectedID);
        contentValues.put("TABLENO", table);
        contentValues.put("ORDERCON", wholeOrder);
        contentValues.put("TIMEPLACED", time);
        contentValues.put("TIMECOMPLETED", timecomp);
        contentValues.put("TAKENBY", user);

        long res = db.insert("COMPLETEORDERS", null, contentValues);
        db.close();
        return res;
    }

    public void deleteFromCurrent(String selectedID, String sTable){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereArg = "ORDERID =?";

        db.delete(sTable, whereArg, new String[]{selectedID});
        db.close();
    }

    public long deleteFromMenu(int menuID, String sTable){
        SQLiteDatabase db = this.getWritableDatabase();
        String ID = String.valueOf(menuID);
        String whereArg = "ITEMID =?";

        long res = db.delete(sTable, whereArg, new String[]{ID});
        db.close();
        return res;
    }

    public long updateMenu(int menuID, String itemName, String itemDesc, String itemPrice, String section){
        SQLiteDatabase db = this.getWritableDatabase();
        long res = 0;
        String ID = String.valueOf(menuID);
        String where = "ITEMID = ?";
        ContentValues update = new ContentValues();
        update.put("ITEMNAME", itemName);
        update.put("ITEMCAT", section);
        update.put("ITEMDESC", itemDesc);
        update.put("ITEMPRICE", itemPrice);
        update.put("ADDITIONS", "-");

        res = db.update("MENU", update, where, new String[]{ID});
        return res;
    }

    public long addMenuItem(String itemName, String itemDesc, String itemPrice, String addSelect) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ITEMNAME", itemName);
        contentValues.put("ITEMCAT", addSelect);
        contentValues.put("ITEMDESC", itemDesc);
        contentValues.put("ITEMPRICE", itemPrice);
        contentValues.put("ADDITIONS", "-");
        long res = db.insert("MENU", null, contentValues);
        db.close();
        return res;
    }

}
