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

public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<MedicineItem> list;
    private Context context;

    public static class ViewHolder{
        TextView tv_name;
        TextView tv_conflict;
        Button btn_edit;
        Button btn_del;
    }

    private DBOpenHelper myHelper;

    public static final String DB_NAME = "my_medicine_db";

    public MyAdapter(Context context, List<MedicineItem> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        myHelper = new DBOpenHelper(context,DB_NAME,null,1);
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
            viewHolder.tv_conflict = convertView.findViewById(R.id.tv_conflict);
            viewHolder.btn_edit = convertView.findViewById(R.id.btn_edit);
            viewHolder.btn_del = convertView.findViewById(R.id.btn_del);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(list.get(position).getName());
        viewHolder.tv_conflict.setText(list.get(position).getConflict());
        final int id = list.get(position).getID();

        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHelper.delete(id);
                Intent intent = new Intent(context, ConflictActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
