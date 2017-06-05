package com.example.sanzharaubakir.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        check_permissions();
        initialize_buttons();

    }

    private void check_permissions() {
        int MY_PERMISSIONS_REQUEST_CAMERA = 1;
        int MY_PERMISSIONS_REQUEST_INTERNET = 2;
        /*if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MenuActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MenuActivity.this,
                    new String[]{Manifest.permission.INTERNET},
                    MY_PERMISSIONS_REQUEST_INTERNET);*/
    }

    private void initialize_buttons() {
        Button registerGood = (Button) findViewById(R.id.register_good_activity);
        Button purchase = (Button) findViewById(R.id.purchase_activity);

        registerGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ScanGoodActivity.class);
                i.putExtra("task", "register");
                startActivity(i);
            }
        });
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ScanGoodActivity.class);
                i.putExtra("task", "purchase");
                startActivity(i);
            }
        });
    }
}
