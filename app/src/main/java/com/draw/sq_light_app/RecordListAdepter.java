package com.draw.sq_light_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordListAdepter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<modal> recordList;

    public RecordListAdepter(Context context, int layout, ArrayList<modal> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }


    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtName, txtAge, txtPhone;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtName = row.findViewById(R.id.name);
            holder.txtAge = row.findViewById(R.id.age);
            holder.txtPhone = row.findViewById(R.id.phone);
            holder.imageView = row.findViewById(R.id.image_view);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder)row.getTag();
        }

        modal modal = recordList.get(i);

        holder.txtName.setText(modal.getName());
        holder.txtAge.setText(modal.getAge());
        holder.txtPhone.setText(modal.getPhone());

        byte[] recordImage = modal.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
        holder.imageView.setImageBitmap(bitmap);
        return row;
    }
}
