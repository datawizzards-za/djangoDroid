package com.example.docto.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AddUser extends AppCompatActivity {

    private EditText etName, etSurname, etAddress;
    private String name, lastname, address;
    private JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);
        initialize();

        Button btnSave = (Button) findViewById(R.id.btnSaveContact);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdateContact);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Check if requirements are meant*/
                if (validateNullPointer()) {

                    name = etName.getText().toString();
                    lastname = etSurname.getText().toString();
                    address = etAddress.getText().toString();
                    //TODO i must call a post method and send the required parameters
                    try {
                        new JSONTask().execute("http://192.168.43.243:8000/app/postdata/" + name + "/" + lastname + "/" + address + "/");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        /**The method is reserved for future use...*/
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void initialize() {
        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etAddress = (EditText) findViewById(R.id.etAddress);
    }

    boolean validateNullPointer() {
        if (TextUtils.isEmpty(etName.getText())) {
            etName.setError("Required.");
            return false;
        } else {
            etName.setError(null);
        }
        if (TextUtils.isEmpty(etSurname.getText())) {
            etSurname.setError("Required.");
            return false;
        } else {
            etSurname.setError(null);
        }
        if (TextUtils.isEmpty(etAddress.getText())) {
            etAddress.setError("Required.");
            return false;
        } else {
            etAddress.setError(null);
        }
        return true;
    }

    private class JSONTask extends AsyncTask<String, String, String> {

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
                String finalObject = "{" + '"' + "message" + '"' + ": " + jsonObject + "}";

                JSONObject jsonParent = new JSONObject(finalObject);
                jsonArray = jsonParent.getJSONArray("message");
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
            TextView textView = (TextView) findViewById(R.id.display);
            textView.setText(jsonArray.toString());
            //ListView list = (ListView) findViewById(R.id.listView);
            //list.setOnItemClickListener(this);
            //UserAdapter adapter = new UserAdapter(getBaseContext(), stringObjects);
            //list.setAdapter(adapter);
//            stringObjects.clear();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }

//    // Create GetText Method
//    public void GetText() throws UnsupportedEncodingException {
//        // Create data variable for sent values to server
//
//        //        String data = URLEncoder.encode("name", "UTF-8")
//        //                + "=" + URLEncoder.encode(name, "UTF-8");
//        //
//        //        data += "&" + URLEncoder.encode("lastname", "UTF-8") + "="
//        //                + URLEncoder.encode(surname, "UTF-8");
//        //
//        //        data += "&" + URLEncoder.encode("address", "UTF-8")
//        //                + "=" + URLEncoder.encode(address, "UTF-8");
//        //
//        //        String text = "";
//        BufferedReader reader = null;
//
//        // Send data
//        try {
//            // Defined URL  where to send data
//            URL url = new URL("http://192.168.43.243:8000/app/postdata/" + name + "/" + surname + "/" + address + "/");
//
//            // Send POST data request
//            URLConnection conn = url.openConnection();
//            conn.setDoOutput(true);
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//            System.out.println("//////////////////////////////////////////");
////            wr.write(data);
////            wr.flush();
//
//            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::");
//            // Get the server response
//            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuilder sb = new StringBuilder();
//            System.out.println("==========================================");
//            System.out.println(reader.toString());
//
//            //text = reader.toString();
////            String line = null;
////
////            // Read Server Response
////            while ((line = reader.readLine()) != null) {
////                // Append server response in string
////                sb.append(line + "\n");
////            }
////            text = sb.toString();
//        } catch (Exception ex) {
//
//        } finally {
//            try {
//                reader.close();
//            } catch (Exception ex) {
//            }
//        }
//        // Show response on activity
//        //Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
//
//    }
}
