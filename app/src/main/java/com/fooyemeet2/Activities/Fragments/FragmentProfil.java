package com.fooyemeet2.Activities.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fooyemeet2.Activities.Adapter.PhotosAdapter;
import com.fooyemeet2.Activities.LoginActivity;
import com.fooyemeet2.Activities.Models.Photos;
import com.fooyemeet2.Activities.parserJson.ParserJson;
import com.fooyemeet2.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mehdi on 07/03/2016.
 */

public class FragmentProfil extends Fragment {

    private TextView username;
    private TextView age;
    private CircleImageView profile;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;

    private List<Photos> mPhotosList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private PhotosAdapter mPhotosAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile2, container, false);

        nestedScrollView = (NestedScrollView) rootView.findViewById(R.id.nestedscrollview);
        username = (TextView) rootView.findViewById(R.id.nameProfile);
        age = (TextView) rootView.findViewById(R.id.ageProfile);
        profile =(CircleImageView) rootView.findViewById(R.id.profile);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((LoginActivity)getActivity()).setSupportActionBar(toolbar);
        ((LoginActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((LoginActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((LoginActivity)getActivity()).getSupportActionBar().setTitle("Profil");

        Picasso.with(getContext()).load("http://resize3-parismatch.ladmedia.fr/img/var/news/storage/images/paris-match/people/sport/ludivine-sagna-je-suis-toujours-la-ludivine-de-la-campagne-981562/13852804-1-fre-FR/Ludivine-Sagna-Je-suis-toujours-la-Ludivine-de-la-campagne.jpg").into
                (profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPhoto();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        Photos p = new Photos();
        p.setId("3");
        p.setPhoto("http://www.ufunk.net/wp-content/uploads/2016/01/ValentinValkov-2.jpg");
        mPhotosList.add(0, p);
        p = new Photos();
        p.setPhoto("http://www.snut.fr/wp-content/uploads/2015/08/image-de-paysage-4.jpg");
        p.setId("3");
        mPhotosList.add(0, p);
        p = new Photos();
        p.setPhoto("http://img1.mxstatic.com/wallpapers/85fe057b269394c7dfa0d536b123fba3_large.jpeg");
        p.setId("3");
        mPhotosList.add(0, p);
        p = new Photos();
        p.setPhoto("http://media.meltyxtrem.fr/article-1985478-ajust_930-f1388769984/media.jpg");
        p.setId("3");
        mPhotosList.add(0, p);
        p = new Photos();
        p.setId("3");
        p.setPhoto("http://www.polynesie-francaise.eu.com/images/rochers_1024x768.jpg");
        mPhotosList.add(0, p);
        mPhotosAdapter = new PhotosAdapter(getContext(), mPhotosList);
        recyclerView.setAdapter(mPhotosAdapter);



        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Change Username")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        AsynctaskProfil asynctaskProfil = new AsynctaskProfil();
        asynctaskProfil.execute();
        //nestedScrollView.scrollTo(0, 0);
        return rootView;
    }


    private void loadPhoto() {
        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getContext());;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.zoom, null);
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        Picasso.with(getContext()).load("http://resize3-parismatch.ladmedia.fr/img/var/news/storage/images/paris-match/people/sport/ludivine-sagna-je-suis-toujours-la-ludivine-de-la-campagne-981562/13852804-1-fre-FR/Ludivine-Sagna-Je-suis-toujours-la-Ludivine-de-la-campagne.jpg").fit().centerCrop().noFade().into(image);
        imageDialog.setView(layout);
        imageDialog.create();
        imageDialog.show();
    }
    
    class AsynctaskProfil extends AsyncTask<String , Void, String> {

        private String url = null;
        private int code2;
        private ProgressDialog dialo;
        
        private String convertStreamToString(InputStream is) {
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

        private String request(String url) {

            String result = "false";
            InputStream is;

            URL uri;
            HttpURLConnection conn = null;

            try {
                uri = new URL(url);

                conn = (HttpURLConnection) uri.openConnection();
                String token;
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("clerep", Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", null);
                System.out.println("token ===> " + token);

                conn.setRequestMethod("GET");
                conn.setRequestProperty("x-access-token", token);
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(15000);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();

                System.out.println("Code ====> " + conn.getResponseCode());

                code2 = conn.getResponseCode(); //valeur de retour de lapi
                if (code2 == HttpURLConnection.HTTP_CREATED) {
                    is = conn.getInputStream();
                    result = convertStreamToString(is);
                }
                else {
                    is = conn.getInputStream();
                    result = convertStreamToString(is);                }
                }
                catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                assert conn != null;
                conn.disconnect();
            }
            System.out.println("Result ====> " + result);
            return result;
        }

        private String getUrl() {
            url = "http://ec2-52-59-251-0.eu-central-1.compute.amazonaws.com:8080/api/users/me";
            return (url);
        }

        @Override
        protected String doInBackground(String... params) {
            String resultat;
            resultat = request(getUrl());

            return resultat;
        }

        private boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isOnline()) {
                cancel(true);
            } else {
                this.dialo = new ProgressDialog(getActivity());
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
            System.out.println("Object s ====> " + s);
            JSONObject new_js = new JSONObject();
            try {
                JSONObject js = new JSONObject(s);
                new_js = js.getJSONObject("fooyemeet");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Profil profilslist = ParserJson.parserGetProfil(new_js.toString());
            username.setText(profilslist.getUsername());
            age.setText(profilslist.getAge());
        }
    }
}
