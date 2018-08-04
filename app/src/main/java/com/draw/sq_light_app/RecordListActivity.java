package com.draw.sq_light_app;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordListActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<modal> mList;
    RecordListAdepter mAdepter = null;

    ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        mListView = findViewById(R.id.List);
        mList = new ArrayList<>();
        mAdepter = new RecordListAdepter(this, R.layout.cardview, mList);
        mListView.setAdapter(mAdepter);

        Cursor cursor = SqLiteApp.mySQliteHelper.getData("SELECT * RECORD.SQlite");
        mList.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String age = cursor.getString(2);
            String phone = cursor.getString(3);

            byte[] image = cursor.getBlob(4);
            mList.add(new modal(id, name, age, phone, image));
            }
            mAdepter.notifyDataSetChanged();
        if (mList.size() == 0){
            Toast.makeText(this, "No record found", Toast.LENGTH_SHORT).show();

            }
            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    return false;
                }
            });
    }
}
