package testk24willybrodusrangga.org.testk24willybrodusrangga.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import testk24willybrodusrangga.org.testk24willybrodusrangga.dataCLASS;

/**
 * Created by WR on 2/11/2017.
 */

public class databaseDAO {
    private final String namatable = "data";
    private SQLiteDatabase database;
    private Context context;
    private buatdatabaselocal dbHelper;

    private String[] allColumns = {
            "no",
            "ID",
            "nama",
            "asal",
            "gabung"};

    public databaseDAO(Context contex) {
        this.context =  contex;
        dbHelper = new buatdatabaselocal(contex);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public dataCLASS getdata(String xid) {
        ContentValues values = new ContentValues();
        //String xWhere =  + " = \""+ xid+"\"";
        values.put(allColumns[0], xid);
        Cursor cursor = database.query(namatable,
                allColumns, allColumns[0]+"=?" , new String[] {xid}, null, null, null);

        dataCLASS dataApiclass = null;
        if (cursor.moveToFirst()) {
            dataApiclass = getdataApi(cursor);
        }
        cursor.close();
        return dataApiclass;
    }


    public dataCLASS getdataApi(Cursor cursor) {
        dataCLASS dataApiclass = new dataCLASS();
        dataApiclass.setNo(cursor.getString(0));
        dataApiclass.setID(cursor.getString(1));
        dataApiclass.setNama(cursor.getString(2));
        dataApiclass.setAsal(cursor.getString(3));
        dataApiclass.setJoin(cursor.getString(4));


        return dataApiclass;
    }


    public void insertdata(dataCLASS dataApi) {
        ContentValues values = new ContentValues();
        values.put(allColumns[0], dataApi.getNo());
        values.put(allColumns[1], dataApi.getID());
        values.put(allColumns[2], dataApi.getNama());
        values.put(allColumns[3], dataApi.getAsal());
        values.put(allColumns[4], dataApi.getJoin());
        Log.d("Isi helper", dataApi.getAsal());


//        Toast.makeText(context, "On Insert Data Base "+dataApi.getphonenumber(), Toast.LENGTH_LONG).show();
        database.insert(namatable, null, values);
        Log.d("Menggunakan : ", "insertdata method");

    }


    public void updatedataApi(dataCLASS dataApi) {
        ContentValues values = new ContentValues();
        values.put(allColumns[0], dataApi.getNo());
        values.put(allColumns[1], dataApi.getID());
        values.put(allColumns[2], dataApi.getNama());
        values.put(allColumns[3], dataApi.getAsal());
        values.put(allColumns[4], dataApi.getJoin());

        String whereClause = allColumns[0] + "=" + dataApi.getNo();
        database.update(namatable, values, whereClause, null);
    }


    public List<dataCLASS> getListdataApi() {

        Log.d("Menggunakan : ", "getListdata method");
        List<dataCLASS> listdataApi = new ArrayList<dataCLASS>();
        Cursor cursor = database.query(namatable,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dataCLASS dataApi = getdataApi(cursor);
            listdataApi.add(dataApi);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listdataApi;
    }


    public List<dataCLASS> getListdataApi(String WhereLike) {
        List<dataCLASS> listdataApi = new ArrayList<dataCLASS>();
        Cursor cursor = database.query(namatable,
                allColumns, WhereLike, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dataCLASS dataApi = getdataApi(cursor);
            listdataApi.add(dataApi);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listdataApi;
    }


    public void setDeletedataApi() {
        database.delete(namatable, null, null);
    }

    public void setDeletedataApi(String xWhere) {
        database.delete(namatable, xWhere, null);
    }

//    public void isEksis() {
//        database.;
//    }
}
