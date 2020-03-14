package com.example.medicineconflict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConflictActivity extends AppCompatActivity implements OnClickListener{


    public static final String DB_NAME = "my_medicine_db";
    private DBOpenHelper myHelper;
    private ListView lv;
    private MyAdapter adapter;

    private Button btn_add;
    private Button btn_clear;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conflict);
        initView();
    }

    private void initView(){
        lv = findViewById(R.id.listView_medicine);
        myHelper = new DBOpenHelper(this,DB_NAME,null,1);
        adapter = new MyAdapter(this,myHelper.getBasicInfo());
        lv.setAdapter(adapter);
        btn_add = findViewById(R.id.btn_add);
        btn_clear = findViewById(R.id.btn_clear);
        btn_add.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    public int deleteAll()
    {
        return myHelper.deleteAll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myHelper.close();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add){
            Intent intent = new Intent(ConflictActivity.this, ItemActivity.class);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.btn_clear){
            if(deleteAll()>0) {
                Toast.makeText(ConflictActivity.this,
                        "清空成功！", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(ConflictActivity.this, ConflictActivity.class));
            }
            else{
                Toast.makeText(ConflictActivity.this,
                        "列表为空！", Toast.LENGTH_LONG).show();
            }
        }
    }
}
