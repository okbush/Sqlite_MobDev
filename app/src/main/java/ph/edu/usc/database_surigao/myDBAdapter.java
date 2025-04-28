package ph.edu.usc.database_surigao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.SpringAnimation;

public class myDBAdapter {

    myDBAHelper dbaHelper;

    public myDBAdapter(Context context){
        dbaHelper = new myDBAHelper(context);
    }

    public long insertData(String name, String pass){
        SQLiteDatabase sqLiteDatabase = dbaHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbaHelper.NAME, name);
        contentValues.put(dbaHelper.MyPASSWORD, pass);
        long id = sqLiteDatabase.insert(dbaHelper.TABLE_NAME, null, contentValues);

        return id;
    }

    public String getData() {
        SQLiteDatabase db = dbaHelper.getWritableDatabase();
        String[] columns = {myDBAHelper.UID,
                            myDBAHelper.NAME,
                            myDBAHelper.MyPASSWORD};
        Cursor cursor = db.query(myDBAHelper.TABLE_NAME,columns,
                null,null,null,null,null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()){
            @SuppressLint("Range")
            int cid = cursor.getInt(cursor.getColumnIndex(myDBAHelper.UID));

            @SuppressLint("Range")
            String name = String.valueOf(cursor.getString(cursor.getColumnIndex(myDBAHelper.NAME)));

            @SuppressLint("Range")
            String pass = String.valueOf(cursor.getString(cursor.getColumnIndex(myDBAHelper.MyPASSWORD)));
            stringBuffer.append(cid + "  " + name + "  " + pass + "  \n");
        }
        return stringBuffer.toString();
    }

    public int deleteData(String uname) {
        SQLiteDatabase db = dbaHelper.getWritableDatabase();
        String[] whereArgs = {uname};
        int count = db.delete(
                myDBAHelper.TABLE_NAME,
                myDBAHelper.NAME+" =?", whereArgs);
        return count;
    }

    public int updateData(String cname, String nname) {
        SQLiteDatabase db = dbaHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDBAHelper.NAME, nname);
        String[] whereArgs = {cname};
        int count = db.update(myDBAHelper.TABLE_NAME,
                contentValues,
                myDBAHelper.NAME+" =?", whereArgs);
        return count;
    }

    static class myDBAHelper extends SQLiteOpenHelper {

        private Context context;


        private static final String DATABASE_NAME ="myDB";
        private static final String TABLE_NAME ="myTABLE";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String MyPASSWORD = "Password";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " VARCHAR(255), "
                + MyPASSWORD + " VARCHAR(255));";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;


        public myDBAHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context =context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(CREATE_TABLE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
