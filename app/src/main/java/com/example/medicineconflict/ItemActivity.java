package com.example.medicineconflict;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class ItemActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_conflict;
//    private EditText et_conflict2;
//    private Button btn_ok;
//    private Button btn_back;

    public static final String DB_NAME = "my_medicine_db";
    public static final String TABLE_NAME="medicineItem";
    public static final String ID = "ID";
    public static final String NAME="Name";
    public static final String CONFLICT="Conflict_Name";

    private DBOpenHelper myHelper;

    private SQLiteDatabase db;

//    private int flag = 0;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initView();
        if(getIntent().getIntExtra("id",0)>0)//判断是新增还是修改
        {
            id=getIntent().getIntExtra("id",0);
            setMedicineInfo(id);
//            flag=1;
        }


    }

    private void initView(){
        myHelper = new DBOpenHelper(this,DB_NAME,null,1);
        db = myHelper.getWritableDatabase();

        et_name = findViewById(R.id.et_itemname);
        et_conflict = findViewById(R.id.et_itemconflict);
//        et_conflict2 = findViewById(R.id.et_itemconflict2);
//        btn_ok=findViewById(R.id.button1);
//        btn_back=findViewById(R.id.button2);
//        btn_ok.setOnClickListener(this);
//        btn_back.setOnClickListener(this);
    }

    private void setMedicineInfo(int id) {
        Cursor c=db.query(TABLE_NAME,new String[]{},"ID="+id,null,null,null,null);
        if(c.moveToFirst())
        {
            String name=c.getString(c.getColumnIndex(NAME));
            String conflict=c.getString(c.getColumnIndex(CONFLICT));

            MedicineItem d=new MedicineItem(name,conflict);
            d.setID(c.getInt(c.getColumnIndex("ID")));
            et_name.setText(d.getName());
            et_conflict.setText(d.getConflict());
        }
        c.close();
    }

    @Override
    public void onClick(View view) {

    }
    /*
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button1){
            ContentValues value=new ContentValues();
            value.put(NAME, et_name.getText().toString());

            ArrayList<String> Input = new ArrayList<String>();
            Input.add(et_conflict2.getText().toString());
            Input.add(et_conflict.getText().toString());
            String joined = TextUtils.join(",",Input);

            //value.put(CONFLICT, et_conflict.getText().toString());
            value.put(CONFLICT, joined);

            long result;
            if(flag==0)
                result=db.insert(TABLE_NAME, null, value);
            else
                result=db.update(TABLE_NAME, value, "ID="+id, null);
            db.close();
            if(result>0)
            {
                Toast.makeText(ItemActivity.this, "操作成功！", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(ItemActivity.this,ConflictActivity.class);
                startActivity(intent);
                finish();
            }
        }else if (view.getId() == R.id.button2){
            finish();
            startActivity(new Intent(ItemActivity.this,ConflictActivity.class));
        }
    }

     */
}
