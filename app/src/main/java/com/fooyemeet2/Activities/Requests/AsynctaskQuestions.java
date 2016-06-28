package com.fooyemeet2.Activities.Requests;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fooyemeet2.Activities.Fragments.FragmentProfileTab;
import com.fooyemeet2.Activities.LoginActivity;
import com.fooyemeet2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * Created by m.faid on 15/06/2016.
 */

public class AsynctaskQuestions extends AsyncTask<String, Void, String> {

    private Activity act;
    private ProgressDialog dialo;

    public AsynctaskQuestions(Activity activity) {
        this.act = activity;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private String request(String url, JSONObject raw) {

        String result = "false";
        OutputStream os;
        InputStream is;

        String logs = raw.toString();

        URL uri;
        HttpURLConnection conn = null;

        try {
            uri = new URL(url);

            conn = (HttpURLConnection) uri.openConnection();

            conn.setRequestMethod("POST");

            conn.setReadTimeout(5000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoInput(true); //autoriser lecriture
            conn.setDoOutput(true); // lecture
            conn.setUseCaches(false);
            conn.connect();

            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(logs.getBytes());
            os.flush(); //ecrire

            int code2 = conn.getResponseCode();
            if (code2 == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                result = convertStreamToString(is);
            }
            else
                result = "false";
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            assert conn != null;
            conn.disconnect();
        }

        return result;
    }

    private String getUrl() {
        return ("http://ec2-52-59-251-0.eu-central-1.compute.amazonaws.com:8080/api/users/login");
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private String getConnexion(String url, String... params) { //creat json param a l api avec le chemin
        JSONObject json = new JSONObject();
        String resultat = null;

        try {
            json.put("reponse", params[0]);   // charu api
            resultat = request(url, json);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        dialo.cancel();
        return (resultat);
    }

    @Override
    protected String doInBackground(String... strings) {
        String resultat = null;

        resultat = getConnexion(getUrl(), strings);
        return resultat;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!isOnline()) {
            cancel(true);
        } else {
            this.dialo = new ProgressDialog(act);
            dialo.setMessage("Login... Wait");
            dialo.show();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (dialo.isShowing() || dialo != null) {
            dialo.dismiss();
            dialo = null;
        }
        ((LoginActivity) act).getSupportFragmentManager().beginTransaction().replace(R.id.container_login, new FragmentProfileTab()).commit();
    }
}
