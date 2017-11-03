package com.example.docto.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class AddUser extends AppCompatActivity {

    private EditText etName, etSurname, etAddress;
    private String name, lastname, address;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);
        initialize();

        Button btnSave = findViewById(R.id.btnSaveContact);
        Button btnUpdate = findViewById(R.id.btnUpdateContact);

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
                        new PostData().execute("http://192.168.43.243:8000/app/postdata/");
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
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etAddress = findViewById(R.id.etAddress);
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

    public class PostData extends AsyncTask<String, String, String> {
        // This is the JSON body of the post
        JSONObject postData;

        // This is a constructor that allows you to pass in the JSON body
        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection con = null;
            try {
                url = new URL(strings[0]);
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                System.out.println("______________________________________________");
//                System.out.println(con.getHeaderField("Cookie"));
//                System.out.println(con.getHeaderFieldDate("Date", 1));
//                System.out.println(con.getResponseMessage());
                System.out.println("_______________________________________________");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String urlParameters = "name=" + etName.getText().toString() + "&lastname=" + etSurname.getText().toString()
                        + "&address=" + etAddress.getText().toString();//+"&csrfmiddlewaretoken={{csrf_token}}";
                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //print result
                System.out.println(response.toString());
                //
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
//    public static Cookie getCookie(String name) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(name)) {
//                    return cookie;
//                }
//            }
//        }
//        return null;
//    }
}
