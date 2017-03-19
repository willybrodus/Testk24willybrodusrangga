package testk24willybrodusrangga.org.testk24willybrodusrangga;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import testk24willybrodusrangga.org.testk24willybrodusrangga.localdatabase.databaseDAO;

public class MainActivity extends AppCompatActivity {

    boolean connected = false;
    private static final int REQUEST_CODE = 0x11;
    private dataCLASS dataApi;
    private ArrayList<dataCLASS> xLsdata = null;
    private Adapterdata adapterdata;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    public int posisiOnLOngClick=-1;
    public ProgressDialog progress;
    private LinearLayout gakkonek;
    private LinearLayout konek;
    private Button klikkonek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connected = cekinternet();
        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_INTERNAL_STORAGE",
                "android.permission.READ_INTERNAL_STORAGE"};


        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        gakkonek = (LinearLayout) findViewById(R.id.gakkonek);
        konek = (LinearLayout) findViewById(R.id.konek);
        klikkonek = (Button) findViewById(R.id.klikinternet);

        if (connected == true) {
            //we are connected to a network
            Toast.makeText(getApplicationContext(), "Koneksi aktif " + connected, Toast.LENGTH_SHORT).show();
            konek.setVisibility(View.VISIBLE);
            gakkonek.setVisibility(View.GONE);

            jikaconek();
        } else {
            konek.setVisibility(View.GONE);
            gakkonek.setVisibility(View.VISIBLE);


            klikkonek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connected = cekinternet();
                    if (connected == true) {
                        jikaconek();
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("No Internet Acess, Turn On WIFI?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            });

            Toast.makeText(getApplicationContext(), "Koneksi Tidak aktif " + connected, Toast.LENGTH_SHORT).show();

        }
    }

    public void jikaconek(){
        konek.setVisibility(View.VISIBLE);
        gakkonek.setVisibility(View.GONE);

        xLsdata = new ArrayList<dataCLASS>();
        rv = (RecyclerView) findViewById(R.id.rv_recycler);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setHasFixedSize(true);


        databaseDAO xSetPhnDAO = new databaseDAO(getBaseContext());
        xSetPhnDAO.open();
        xLsdata.addAll(xSetPhnDAO.getListdataApi());
        xSetPhnDAO.close();


        if (xLsdata.size() == 0 ) {
            //secara asinkronus
            new setdata().execute();

            //secara sinkronus
    /*        URL url;
            HttpURLConnection connection = null;
            try {
                //Create connection
                url = new URL(" http://test.k24.co.id/api.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("id", "rc2017")
                        .appendQueryParameter("name", "willybrodus");

                String query = builder.build().getEncodedQuery();

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                //Get Response
                @SuppressWarnings("serial")
                InputStream xInputStream = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(xInputStream));
                String line;
                String Sresponse = "";
                while ((line = rd.readLine()) != null) {
                    Sresponse += line;
                }

                Log.d("Response Hasil konekurl", "" + Sresponse + "\n");


            // ini nanti untuk melihat jika ketemu ERROR
            //StringBuffer response = new StringBuffer();
            //while((line = rd.readLine()) != null) {
            //response.append(line);
            //response.append('\r');
            //}
            rd.close();

                Gson gson = new Gson();
                ArrayList<dataCLASS> listdata = gson.fromJson(Sresponse, new TypeToken<List<dataCLASS>>() {
                }.getType());

                if (listdata != null) {
                    if(listdata.size() > 0){
                        databaseDAO xSetdataDAO = new databaseDAO(getBaseContext());
                        xSetdataDAO.open();

                        for (int i = 0; i < listdata.size(); i++) {
                            dataApi = new dataCLASS();
                            Log.d("Isi listdata", listdata.get(i).getNama());
                            dataApi.setNo(String.valueOf(i));
                            dataApi.setID(listdata.get(i).getID());
                            dataApi.setNama(listdata.get(i).getNama());
                            dataApi.setAsal(listdata.get(i).getAsal());
                            dataApi.setJoin(listdata.get(i).getJoin());
                            Log.d("Isi dataApi", dataApi.getAsal());
                            xSetdataDAO.insertdata(dataApi);

                        }
                        xSetdataDAO.close();
                        xLsdata.addAll(listdata);

                        setdataK24();

                    }else{
                        Toast.makeText(getApplication(), "Data Tidak Ada", Toast.LENGTH_LONG).show();
                    }

                }

            } catch (Exception e) {

                e.printStackTrace();

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            } */

        } else {
            setdataK24();
        }
//

    }


    public boolean cekinternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }else {
            return false;
        }
    }

    public class setdata extends AsyncTask<Void, Void, ArrayList<dataCLASS>> {
        private ArrayList<dataCLASS> Listdata;
        private String iSearch;

        setdata() {

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected ArrayList<dataCLASS> doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            URL url = null;


            try {
                url = new URL(config.SERVER_PHP + "data/lihatandroid");
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setReadTimeout(30000);
//                    urlConnection.setConnectTimeout(35000);
//                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);


                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("search", "");
//                            .appendQueryParameter("name", "willybrodus");

                    String query = builder.build().getEncodedQuery();

                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();

                    //urlConnection.connect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    BufferedReader bufreader = null;
                    //List<ItemHasilSearch> buflistitemSearch= new ArrayList<>();
                    bufreader = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));

                    @SuppressWarnings("serial")
                    Gson gson = new Gson();


//                    String  line;
//                    String Sresponse="";
//
//
//                    while ((line=bufreader.readLine()) != null) {
//                        Sresponse+=line;
//                    }
//                    Log.d("Data hasil Search ", "> " + Sresponse+"\n");
//


                    Listdata = gson.fromJson(bufreader, new TypeToken<List<dataCLASS>>() {
                    }.getType());


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return Listdata;
        }

        @Override
        protected void onPostExecute(final ArrayList<dataCLASS> listdata) {

            posisiOnLOngClick=-1;
            //iStart += iLimit;
//            Toast.makeText(getApplication(), "Pencarian Di GAGALKAN"+listproduk.size(), Toast.LENGTH_LONG).show();

            if (listdata != null) {
                    if(listdata.size() > 0){
                databaseDAO xSetdataDAO = new databaseDAO(getBaseContext());
                xSetdataDAO.open();

                
                for (int i = 0; i < listdata.size(); i++) {
                    dataApi = new dataCLASS();
                    Log.d("Isi listdata", listdata.get(i).getNama());
                    dataApi.setNo(String.valueOf(i));
                    dataApi.setID(listdata.get(i).getID());
                    dataApi.setNama(listdata.get(i).getNama());
                    dataApi.setAsal(listdata.get(i).getAsal());
                    dataApi.setJoin(listdata.get(i).getJoin());
                    Log.d("Isi dataApi", dataApi.getAsal());
                    xSetdataDAO.insertdata(dataApi);

                }

                xSetdataDAO.close();
                xLsdata.addAll(listdata);

                setdataK24();
                
               }else{
                        Toast.makeText(getApplication(), "Data Tidak Ada", Toast.LENGTH_LONG).show();
                    }

            }
            else {
                Toast.makeText(getApplication(), "Data Tidak Ada", Toast.LENGTH_LONG).show();

            }


        }


        @Override
        protected void onCancelled() {

            Toast.makeText(getApplication(), "Pencarian Di GAGALKAN", Toast.LENGTH_LONG).show();
        }
    }

    public void setdataK24(){
        adapterdata = new Adapterdata(xLsdata);
        rv.setAdapter(adapterdata);
        rv.setLayoutManager(llm);
    }
}
