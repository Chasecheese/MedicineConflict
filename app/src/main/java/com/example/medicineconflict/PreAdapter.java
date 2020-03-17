package com.example.medicineconflict;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PreAdapter extends BaseAdapter {

    public static final String DB_NAME = "new_medicine_db";
    public static final String TABLE_NAME="newMedicineItem";
    public static final String ID = "ID";
    public static final String NAME="Name";
    public static final String LEVEL4 = "level_4";
    public static final String LEVEL3 = "level_3";
    public static final String LEVEL2 = "level_2";
    public static final String LEVEL1 = "level_1";

    private LayoutInflater inflater;
    private List<MedicineItem> list;
    private Context context;

    public static class ViewHolder{
        TextView tv_name;
        //Button btn_check;
    }



    public PreAdapter(Context context, List<MedicineItem> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.medicine_item, null);
            viewHolder = new ViewHolder();

            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            //viewHolder.btn_check = convertView.findViewById(R.id.btn_check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(list.get(position).getName());
        final int id = list.get(position).getID();
        /*
        viewHolder.btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowItemActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        */
        return convertView;
    }
}
