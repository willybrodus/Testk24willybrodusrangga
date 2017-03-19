package testk24willybrodusrangga.org.testk24willybrodusrangga.localdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by WR on 2/11/2017.
 */

public class buatdatabaselocal extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    //deklarasi nama sqlite
    private static final String DATABASE_NAME = "kapada.db";
    //membuar querry pembuatan table
    private static final String tablesetting = "CREATE TABLE  " +
            "data(" +
            "no text DEFAULT null," +
            "ID text DEFAULT null," +
            "nama text DEFAULT null," +
            "asal text DEFAULT null," +
            "gabung text DEFAULT null)";

    private Context contex;

    //membuat database konstruktor
    public buatdatabaselocal(Context contex) {
        super(contex, DATABASE_NAME, null, DATABASE_VERSION);
        this.contex = contex;
        //Toast.makeText(this.contex, "buatdatabase!", Toast.LENGTH_LONG).show();

    }


    //terun otomatis karena turunan dari kelas sqlite
    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("DROP TABLE IF EXISTS settingphone" );
        //Toast.makeText(this.contex, "On Create!", Toast.LENGTH_LONG).show();
        db.execSQL(tablesetting);

        //Toast.makeText(this.contex, "On Create!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS settingphone" );
  /*      onCreate(db);*/
        // Toast.makeText(this.contex, "On Updgare!", Toast.LENGTH_LONG).show();
    }

}
