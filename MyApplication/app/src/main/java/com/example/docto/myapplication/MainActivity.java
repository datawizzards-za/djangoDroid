package com.example.docto.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {
private TextView textView;
    private ArrayList<String> stringObjects =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHit = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://192.168.43.13:8000/app/details/");
            }});
    }
    public class JSONTask extends AsyncTask<String,String,String> {

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
                String finalObject = "{"+'"' + "users"+ '"' + ": " +jsonObject+"}";
                System.out.println("=============================");
                System.out.println(finalObject);

                JSONObject jsonParent = new JSONObject(finalObject);
                JSONArray jsonArray = jsonParent.getJSONArray("users");

                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    String surname = object.getString("lastname");
                    String address = object.getString("address");

                    line = "name: "+name+" surname :"+surname +" address: "+address;
//                    String lineAppend = line;
                    System.out.println("=============================");
                    System.out.println(line);
                    System.out.println("=============================");
                    stringObjects.add(line);
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
//            for(String s:stringObjects){
//                System.out.println("=============================");
//                System.out.println(s);
//                System.out.println("=============================");
//            }
//            textView.setText(result);
        }
    }
}
