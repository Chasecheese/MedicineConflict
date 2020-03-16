package com.example.medicineconflict;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class ItemActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_name;

    public static final String DB_NAME = "new_medicine_db";
    public static final String TABLE_NAME = "newMedicineItem";
    public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String LEVEL4 = "level_4";
    public static final String LEVEL3 = "level_3";
    public static final String LEVEL2 = "level_2";
    public static final String LEVEL1 = "level_1";

    private DBOpenHelper myHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initView();
        int id = getIntent().getIntExtra("id", 0);
        MedicineItem temp = setMedicineInfo(id);
        tv_name.setText(temp.getName());
        setTitle(temp.getName());

        String level4 = temp.getLevel4();
        String level3 = temp.getLevel3();
        String level2 = temp.getLevel2();
        String level1 = temp.getLevel1();


        String[] coListLevel4 = level4.split(",");
        String[] coListLevel3 = level3.split(",");
        String[] coListLevel2 = level2.split(",");
        String[] coListLevel1 = level1.split(",");

        ListView list = findViewById(R.id.ListView);

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        if(!coListLevel4[0].equals("#")){
            for (int i = 0; i < coListLevel4.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", coListLevel4[i]);
                map.put("ItemText", "禁忌");
                if(!coListLevel4[i].equals("#"))
                    listItem.add(map);
            }
        }
        if(!coListLevel3[0].equals("#")){
            for (int i = 0; i < coListLevel3.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", coListLevel3[i]);
                map.put("ItemText", "严重");
                listItem.add(map);
            }
        }
        if(!coListLevel2[0].equals("#")){
            for (int i = 0; i < coListLevel2.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", coListLevel2[i]);
                map.put("ItemText", "中度");
                listItem.add(map);
            }
        }
        if(!coListLevel1[0].equals("#")){
            for (int i = 0; i < coListLevel1.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", coListLevel1[i]);
                map.put("ItemText", "轻度");
                listItem.add(map);
            }
        }


        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,// 数据源
                R.layout.list_items,// ListItem的XML实现
                // 动态数组与ImageItem对应的子项
                new String[]{"ItemTitle", "ItemText"},
                // ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.ItemTitle, R.id.ItemText});

        // 添加并且显示
        list.setAdapter(listItemAdapter);

        // 添加点击
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //setTitle("点击第" + arg2 + "项");
            }
        });
        /*
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
        */

    }

    private void initView() {
        myHelper = new DBOpenHelper(this, DB_NAME, null, 1);
        db = myHelper.getWritableDatabase();
        tv_name = findViewById(R.id.tv_itemname);
    }

    private MedicineItem setMedicineInfo(int id) {
        Cursor c = db.query(TABLE_NAME, new String[]{}, "ID=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(NAME));

            String level4 = c.getString(c.getColumnIndex(LEVEL4));
            String level3 = c.getString(c.getColumnIndex(LEVEL3));
            String level2 = c.getString(c.getColumnIndex(LEVEL2));
            String level1 = c.getString(c.getColumnIndex(LEVEL1));
            MedicineItem d = new MedicineItem(name, level4, level3, level2, level1);
            d.setID(c.getInt(c.getColumnIndex("ID")));
            c.close();
            return d;
        } else {
            c.close();
            return null;
        }
    }

    /*
        @Override
        public boolean onContextItemSelected(MenuItem item) {
            setTitle("点击了长按菜单的第" + item.getItemId() + "项");
            return super.onContextItemSelected(item);
        }
    */
    @Override
    public void onClick(View view) {

    }
}
