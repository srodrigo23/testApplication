package com.rodresdev.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rodresdev.testapplication.databinding.ActivityMainBinding;

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
        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textFromCpp = stringFromJNI();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), textFromCpp, duration);
                toast.show();
            }
        });
    }

    /**
     * A native method that is implemented by the 'testapplication' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}