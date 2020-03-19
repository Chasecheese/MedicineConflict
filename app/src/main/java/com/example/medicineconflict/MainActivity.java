package com.example.medicineconflict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button btn_conflict;
    private Button btn_others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        SQLiteStudioService.instance().start(this);

    }

    public void initView(){
        btn_conflict = findViewById(R.id.btn_mainactivity_conflict);
        btn_others = findViewById(R.id.button_mainactivity_others);
        btn_conflict.setOnClickListener(this);
        btn_others.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mainactivity_conflict:
                startActivity(new Intent(this, ListAllActivity.class));
                break;
            case R.id.button_mainactivity_others:
                Toast.makeText(this, "Not Ready!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(this, X.class));
                break;
            default:
                break;
        }
    }
}
