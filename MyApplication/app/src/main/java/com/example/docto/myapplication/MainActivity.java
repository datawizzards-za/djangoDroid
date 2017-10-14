package com.example.docto.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHit = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://192.168.43.13:8000/app/all/");
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
                    System.out.println("===========================");
                    System.out.println("Am inside while loop");
                    System.out.println("===========================");
                    buffer.append(line);
                }
                System.out.println("===========================");
                System.out.println("Your Buffer is :"+buffer.toString());
                return buffer.toString();
            } catch (MalformedURLException e) {
                System.out.println("===========================");
                System.out.println("MalformedURLException has occurred");
                System.out.println("===========================");
            } catch (IOException e) {
                System.out.println("===========================");
                System.out.println("IOException has occurred");
                System.out.println("===========================");
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    System.out.println("===========================");
                    System.out.println("Could not close the buffer");
                    System.out.println("===========================");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("===========================");
            System.out.println(result);
            textView.setText(result);
        }
    }
}
