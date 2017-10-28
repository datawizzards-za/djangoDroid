package com.example.docto.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> stringObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHit = (Button) findViewById(R.id.btnViewContacts);
        Button btnAddContact = (Button) findViewById(R.id.btnAddContact);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringObjects.clear();
                new JSONTask().execute("http://192.168.43.243:8000/app/details/");
            }
        });
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),AddUser.class));
                finish();
            }
        });
    }

    private class JSONTask extends AsyncTask<String, String, String> implements AdapterView.OnItemClickListener {

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String jsonObject = buffer.toString();
                String finalObject = "{" + '"' + "users" + '"' + ": " + jsonObject + "}";

                JSONObject jsonParent = new JSONObject(finalObject);
                JSONArray jsonArray = jsonParent.getJSONArray("users");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    String surname = object.getString("lastname");
                    String address = object.getString("address");

                    //creating a string
                    String _data = name+"#"+surname+"#"+address;

                    //console debugging
                    System.out.println("================================");
                    System.out.println(_data);//ends here..

                    //populating a string array
                    stringObjects.add(_data);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ListView list = (ListView) findViewById(R.id.listView);
            list.setOnItemClickListener(this);
            UserAdapter adapter = new UserAdapter(getBaseContext(), stringObjects);
            list.setAdapter(adapter);
//            stringObjects.clear();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO when the item on the list is clicked
        }
    }
}
