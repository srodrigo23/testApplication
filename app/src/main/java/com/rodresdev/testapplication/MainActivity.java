package com.rodresdev.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rodresdev.testapplication.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'testapplication' library on application startup.
    static {
        System.loadLibrary("testapplication");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button buttonAction = (Button)findViewById(R.id.buttonAction);
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Making request...");
        dialog.setCancelable(false);
        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textFromCpp = stringFromJNI();
                int duration = Toast.LENGTH_SHORT;
//                dialog.show();
                Thread gfgThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try  {
                            URL url = new URL("https://s7om3fdgbt7lcvqdnxitjmtiim0uczux.lambda-url.us-east-2.on.aws");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept","application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("address", textFromCpp);

                            Log.i("JSON", jsonParam.toString());
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());

                            os.writeBytes(jsonParam.toString());

                            os.flush();
                            os.close();

                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG" , conn.getResponseMessage());
                            conn.disconnect();
                            BufferedReader br1 = new BufferedReader(
                                    new InputStreamReader(conn.getInputStream()));
                            StringBuilder response = new StringBuilder();
                            String responseSingle = null;

                            while ((responseSingle = br1.readLine()) != null) {
                                response.append(responseSingle);
                            }
                            String xx = response.toString();
                            Log.i("code", xx);
//                            Toast.makeText(getApplicationContext(),xx,Toast.LENGTH_SHORT).show();
//                            conn.disconnect();
                        } catch (ProtocolException ex) {
                            throw new RuntimeException(ex);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        } catch (UnsupportedEncodingException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                gfgThread.start();
            }
        });
    }
    public String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
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
    private void makeRequest(){
        URL url = null;
        try {
            url = new URL("http://exampleurl.com/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("address","192.168.0.1");
            client.setDoOutput(true);
        }catch(MalformedURLException error) {
            //Handles an incorrectly entered URL
        } catch(SocketTimeoutException error) {
            // Handles URL access timeout.
        } catch (IOException error) {
            // Handles input and output errors
        }
    }

    /**
     * A native method that is implemented by the 'testapplication' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}