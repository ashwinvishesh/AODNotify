package com.cynix.ashwin.aodnotify;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashwi on 31-10-2017.
 */

public class DBHelper extends SQLiteAssetHelper
{

    private static int DB_VERSION=1;
    private static String DB_NAME = "aoddb.db";

    public DBHelper(Context context)
    {
        super(context, DB_NAME , null, DB_VERSION);
    }

    public SQLiteDatabase getDb ()
    {
        SQLiteDatabase db = getWritableDatabase();
        return  db;
    }

    public void InsertNewNotification(int id , int iconid , String packagename)
    {
        ContentValues cv = new ContentValues();
        cv.put("ID", id);
        cv.put("ICONID",iconid);
        cv.put("PACKAGENAME", packagename);
        SQLiteDatabase db = getDb();
        db.insert("NOTILIST", null, cv);
    }

    public void RemoveNotification(String packageName)
    {
        SQLiteDatabase db = getDb();
        db.execSQL("DELETE FROM NOTILIST WHERE PACKAGENAME ='"+packageName+"'");
    }

    public List<NotificationItemInformation> ReadNotilist (Context context)
    {
        String packageName;
        int notiId,iconId;
        Drawable notiIcon = null;
        Log.e("ReadNotilist ", " Called ");
        List<NotificationItemInformation>  data = new ArrayList<>();
        SQLiteDatabase db = getDb();
        Cursor cursor = db.rawQuery("SELECT ID,MAX(ICONID),PACKAGENAME FROM NOTILIST GROUP BY PACKAGENAME", null);
        cursor.moveToFirst();

        Context remotePackageContext = null;

        for (int i=0 ; i < cursor.getCount() ; i++ )
        {
            notiId = cursor.getInt(0);
            iconId = cursor.getInt(1);
            packageName = cursor.getString(2);

            try
            {
                remotePackageContext = context.createPackageContext(packageName, 0);
                notiIcon = remotePackageContext.getResources().getDrawable(iconId);
            } catch (PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
            }

            NotificationItemInformation current =new NotificationItemInformation();
            current.noitId = notiId;
            current.iconId = iconId;
            current.packageName = packageName;
            current.notiIcon = notiIcon;
            data.add(current);
            cursor.moveToNext();
        }
        return data;
    }

}
