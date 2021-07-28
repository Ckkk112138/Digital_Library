package com.example.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Digital_Lib.db";
    public static final String TABLE1_NAME = "User_table";
    public static final String TABLE2_NAME = "Bookshelf_table";
    public static final String TABLE3_NAME = "Collection_table";
    public static final String TABLE4_NAME = "Physical_library_book_table";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1_NAME + "(ID integer primary key autoincrement,username text,password text,cardnumber text,avatar blob )");
        db.execSQL("create table " + TABLE2_NAME + "(ID integer primary key autoincrement,username text,book_name text,type text,cover_page blob )");
        db.execSQL("create table " + TABLE3_NAME + "(ID integer primary key autoincrement,username text,book_name text )");
        db.execSQL("create table " + TABLE4_NAME + "(ID integer primary key autoincrement,cardnumber text,book_name text,expire_date text )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void setBookFromLibrary(String cardNumber, String bookName,String expireDate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("cardnumber",cardNumber);
        contentValues.put("book_name",bookName);
        contentValues.put("expire_date",expireDate);
        db.insert(TABLE4_NAME,null,contentValues);

    }

    public void setAvatar(String userName, Bitmap bitmap)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);

        contentValues.put("avatar",os.toByteArray());
        db.update(TABLE1_NAME,contentValues,"username=?",new String[]{userName});

    }

    public void setCardNumber(String userName,String cardNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put("cardnumber",cardNumber);
        db.update(TABLE1_NAME,contentValues,"username=?",new String[]{userName});

    }

    public void setBookCover(String userName, String bookName,String type,Bitmap bitmap)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
        contentValues.put("username",userName);
        contentValues.put("book_name",bookName);
        contentValues.put("type",type);

        contentValues.put("cover_page",os.toByteArray());
        db.insert(TABLE2_NAME,null,contentValues);

    }

    public void setCollection(String userName, String bookName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username",userName);
        contentValues.put("book_name",bookName);

        db.insert(TABLE3_NAME,null,contentValues);

    }

    public void setUserAccount(String userName, String password,String cardnumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",userName);
        contentValues.put("password",password);
        contentValues.put("cardnumber",cardnumber);

        db.insert(TABLE1_NAME,null,contentValues);


    }

    public String getUserName(String userName)
    {
        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select username from " +TABLE1_NAME + " where username = ?",new String[]{userName});
        while (res.moveToNext())
        {
            result = res.getString(0);
        }
        return result;
    }

    public String[] getLibraryBookName(String cardNumber)
    {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        String[] result = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select book_name from " +TABLE4_NAME + " where cardnumber = ?",new String[]{cardNumber});
        while (res.moveToNext())
        {
            stringArrayList.add(res.getString(0));
        }
        if(stringArrayList.size() != 0)
        {
           result = stringArrayList.toArray(new String[stringArrayList.size()]);
        }
        return result;
    }

    public String getLibraryBookExpireDate(String cardNumber,String bookName)
    {

        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select expire_date from " +TABLE4_NAME + " where cardnumber = ? and book_name = ?",new String[]{cardNumber,bookName});
        while (res.moveToNext())
        {
          result = res.getString(0);
        }

        return result;
    }

    public String getPassword(String userName)
    {
        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select password from " +TABLE1_NAME + " where username = ?",new String[]{userName});
        while (res.moveToNext())
        {
            result = res.getString(0);
        }
        return result;
    }

    public String getCardNumber(String userName)
    {
        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select cardnumber from " +TABLE1_NAME + " where username = ?",new String[]{userName});
        while (res.moveToNext())
        {
            result = res.getString(0);
        }
        return result;
    }

    public Bitmap getAvatar(String username)
    {
        Bitmap bitmap = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE1_NAME,new String[]{"avatar"},"username=?",new String[]{username},null,null,null);
        while (cursor.moveToNext())
        {
            byte[] in = cursor.getBlob(cursor.getColumnIndex("avatar"));
            if(in != null)
            {
                bitmap = BitmapFactory.decodeByteArray(in,0,in.length);

            }

        }
        return bitmap;
    }

    public Bitmap getBookCover(String username,String type)
    {
        Bitmap bitmap = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE2_NAME,new String[]{"cover_page","type"},"username=? and type=?",new String[]{username,type},null,null,null);
        while (cursor.moveToNext())
        {
            byte[] in = cursor.getBlob(cursor.getColumnIndex("cover_page"));
            if(in != null)
            {
                bitmap = BitmapFactory.decodeByteArray(in,0,in.length);

            }

        }
        return bitmap;
    }

    public String[] getBookCoverName(String userName,String type)
    {
        int i = 0;
        String[] result = {"-1","-1","-1","-1","-1","-1"};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select book_name from " +TABLE2_NAME + " where username = ? and type=?",new String[]{userName,type});
        while (res.moveToNext())
        {
            result[i] = res.getString(0);
            i++;
        }
        return result;
    }

    public String getBookType(String userName)
    {

        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select type from " +TABLE2_NAME + " where username = ?",new String[]{userName});
        while (res.moveToNext())
        {
            result = res.getString(0);

        }
        return result;
    }

    public void deleteBookInBookshelf(String userName, String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE2_NAME,"username=? and book_name=?",new String[]{
                userName,title
        });
    }
}
