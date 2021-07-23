package in.sundram.hyperzodassignment.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="MERCHANT.DB";
    public static final int DATABASE_VERSION=1;
    public static String CREATE_QUERY="CREATE TABLE "+DataProvider.TABLE_NAME+"("+DataProvider.MERCHANT_ID+" TEXT,"+DataProvider.COUNT+" TEXT"+");";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Toast.makeText(context,"Database Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addCountOfMerchant(String id,String count,SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(DataProvider.MERCHANT_ID,id);
        values.put(DataProvider.COUNT,count);
        db.insert(DataProvider.TABLE_NAME,null,values);
        Log.e("DB","One Row Inserted");
    }

//    public void updateTableData(String id,SQLiteDatabase db){
//
//        String []selection = {DataProvider.MERCHANT_ID,DataProvider.COUNT};
//        ContentValues newValues = new ContentValues();
//        newValues.put(DataProvider.COUNT, count);
//
//        String[] args = new String[]{"user1", "user2"};
//        db.update("YOUR_TABLE", newValues, "name=? OR name=?", args);
//    }

    public Cursor getCountOfMerchantsById(String id,SQLiteDatabase db){
        String []selection = {DataProvider.MERCHANT_ID,DataProvider.COUNT};
        String where= DataProvider.MERCHANT_ID+" Like ?";
        String []selectionArgs = {id};
        Cursor cursor=db.query(DataProvider.TABLE_NAME,selection,where,selectionArgs,null,null,null);
        return cursor;
    }
}
