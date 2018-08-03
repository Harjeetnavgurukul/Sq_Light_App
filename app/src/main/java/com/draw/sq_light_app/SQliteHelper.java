package com.draw.sq_light_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;



public class SQliteHelper extends SQLiteOpenHelper {

    public SQliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void quaryData (String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public void insertData(String name, String age, String phone, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO RECORD VALUES(NULL,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindString(2, age);
        statement.bindString(3, phone);
        statement.bindBlob(4, image);
        statement.execute();

    }

    public void updateData (String name, String age, String phone, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE RECORD SET name = ?, age = ?, phone = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, name);
        statement.bindString(2, age);
        statement.bindString(3, phone);
        statement.bindBlob(4, image);
        statement.bindDouble(5, (double)id);
        statement.execute();
        database.close();
    }

    public void deleteData (int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM RECORD WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);
        statement.execute();
        database.close();
        }
        public Cursor getData (String sql){
            SQLiteDatabase database = getWritableDatabase();
            return database.rawQuery(sql, null);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
