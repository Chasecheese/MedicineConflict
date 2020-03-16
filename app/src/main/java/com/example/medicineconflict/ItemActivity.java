package com.example.medicineconflict;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class ItemActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_name;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initView();
        int id = getIntent().getIntExtra("id",0);
        MedicineItem temp = setMedicineInfo(id);
        tv_name.setText(temp.getName());
        setTitle(temp.getName());

        String conflict = temp.getConflict();
        String[] coList = conflict.split(",");

        ListView list = findViewById(R.id.ListView);

        // 生成动态数组，加入数据
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < coList.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            String couple[];
            couple = coList[i].split("-");
            map.put("ItemTitle", couple[0]+"");

            String level = new String();

            switch (Integer.parseInt(couple[1])){
                case 1:
                    level = "轻度";
                    break;
                case 2:
                    level = "中度";
                    break;
                case 3:
                    level = "严重";
                    break;
                default:
                    break;
            }
            map.put("ItemText", level+" ");
            listItem.add(map);
        }

        // 生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,// 数据源
                R.layout.list_items,// ListItem的XML实现
                // 动态数组与ImageItem对应的子项
                new String[] { "ItemTitle", "ItemText" },
                // ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] { R.id.ItemTitle, R.id.ItemText });

        // 添加并且显示
        list.setAdapter(listItemAdapter);

        // 添加点击
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                setTitle("点击第" + arg2 + "项");
            }
        });

        // 添加长按点击
        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("长按菜单-ContextMenu");
                menu.add(0, 0, 0, "弹出长按菜单0");
                menu.add(0, 1, 0, "弹出长按菜单1");
            }
        });

    }

    private void initView(){
        myHelper = new DBOpenHelper(this,DB_NAME,null,1);
        db = myHelper.getWritableDatabase();

        tv_name = findViewById(R.id.tv_itemname);
//        et_conflict2 = findViewById(R.id.et_itemconflict2);
//        btn_ok=findViewById(R.id.button1);
//        btn_back=findViewById(R.id.button2);
//        btn_ok.setOnClickListener(this);
//        btn_back.setOnClickListener(this);
    }

    private MedicineItem setMedicineInfo(int id) {
        Cursor c=db.query(TABLE_NAME,new String[]{},"ID="+id,null,null,null,null);
        if(c.moveToFirst())
        {
            String name=c.getString(c.getColumnIndex(NAME));
            String conflict=c.getString(c.getColumnIndex(CONFLICT));
            MedicineItem d=new MedicineItem(name,conflict);
            d.setID(c.getInt(c.getColumnIndex("ID")));
            c.close();
            return d;
        }
        else {
            c.close();
            return null;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        setTitle("点击了长按菜单的第" + item.getItemId() + "项");
        return super.onContextItemSelected(item);
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
