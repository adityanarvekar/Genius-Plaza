package com.geniusplaza;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button adduser;
    ProgressBar progressBar;
    LinearLayout header;
    ArrayList<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
    CustomAdapterForUser ADA = new CustomAdapterForUser(MainActivity.this, prolist);
    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        adduser = (Button) findViewById(R.id.adduser);
        header = (LinearLayout) findViewById(R.id.header);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        FillList fillList = new FillList();
        fillList.execute();
    }

    public void onUserAdded(String jsonResponse) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Info of New User")
                .setMessage(jsonResponse)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }


    public class FillList extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            header.setVisibility(View.GONE);
            adduser.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String r) {

            progressBar.setVisibility(View.GONE);
            header.setVisibility(View.VISIBLE);
            adduser.setVisibility(View.VISIBLE);
            listView.setAdapter(ADA);
            adduser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                    CreateNewUserFragment addN = new CreateNewUserFragment();
                    addN.show(fm, "tag");
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall("https://reqres.in/api/users");
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        HashMap<String, String> datanum = new HashMap<>();
                        datanum.put("A", c.getString("email"));
                        datanum.put("B", c.getString("first_name"));
                        datanum.put("C", c.getString("last_name"));
                        datanum.put("D", c.getString("avatar"));
                        prolist.add(datanum);
                    }
                } catch (final JSONException e) {
                    e.printStackTrace();

                }
            }
            return "";
        }
    }


}
