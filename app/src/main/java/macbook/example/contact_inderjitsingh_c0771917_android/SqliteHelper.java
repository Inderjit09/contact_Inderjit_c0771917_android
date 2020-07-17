package macbook.example.contact_inderjitsingh_c0771917_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper
{
    private static final String databaseName = "myDatabase.db";
    private static final int version = 1;
    public static final String TABLE_NAME = "CONTACT";

    public SqliteHelper(Context context)
    {
        super(context,databaseName,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createContactDataTable = "CREATE TABLE CONTACT(_id INTEGER PRIMARY KEY AUTOINCREMENT ,FIRSTNAME TEXT,LASTNAME TEXT,CONTACT TEXT,EMAIL TEXT,ADDRESS TEXT)";
        db.execSQL(createContactDataTable);

        // insert Contact directly
        insertContact("Police","Helpline","911","emergency911@gmail.com","L1R 6TC",db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public void insertContact(String firstName,String lastName,String contact,String email,String address,SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME",firstName);
        contentValues.put("LASTNAME",lastName);
        contentValues.put("CONTACT",contact);
        contentValues.put("EMAIL",email);
        contentValues.put("ADDRESS",address);
        database.insert("CONTACT",null,contentValues);
    }

    public void updateContact(String toMatch,String firstName,String lastName,String contact,String email,String address,SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME",firstName);
        contentValues.put("LASTNAME",lastName);
        contentValues.put("CONTACT",contact);
        contentValues.put("EMAIL",email);
        contentValues.put("ADDRESS",address);
        database.update("CONTACT",contentValues,"FIRSTNAME = ?",new  String[]{toMatch});

    }


    public Cursor getContactData(String text)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursorObject = database.rawQuery("SELECT * FROM CONTACT WHERE FIRSTNAME = ?",new String[]{text});
        return cursorObject;
    }

}



