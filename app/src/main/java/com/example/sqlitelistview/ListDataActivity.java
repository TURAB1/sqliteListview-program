package com.example.sqlitelistview;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private ListView listItem;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        myDatabaseHelper = new MyDatabaseHelper(this);
        listItem = findViewById(R.id.listItem);

        loadAllData();
    }

    private void loadAllData() {
        ArrayList<String> listData = new ArrayList<>();
        Cursor cursor = myDatabaseHelper.showAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data is found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listData.add(cursor.getString(0) + "\t" + cursor.getString(1));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtItem, listData);
        listItem.setAdapter(adapter);

        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                Toast.makeText(ListDataActivity.this, "Selected value: " + selectedValue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
