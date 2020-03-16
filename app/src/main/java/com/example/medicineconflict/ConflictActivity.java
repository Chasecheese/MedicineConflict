package com.example.medicineconflict;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ConflictActivity extends AppCompatActivity implements OnClickListener{


    public static final String DB_NAME = "new_medicine_db";
    public static final String TABLE_NAME="newMedicineItem";
    private DBOpenHelper myHelper;
    private MyAdapter adapter;
    private ListView lv;
    private ListView lv2;
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        myHelper.close();
    }

    @Override
    public void onClick(View v) {
    }
}
