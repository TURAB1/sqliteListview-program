package com.example.sqlitelistview;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SqlitelistActivity extends AppCompatActivity {

    private EditText edtId, edtName;
    private Button btnSave, btnShow, btnUpdate, btnDelete;

    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlitelist);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);

        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = edtId.getText().toString();
                String userName = edtName.getText().toString();

                if (v.getId() == R.id.btnSave) {
                    if (userId.equals("") && userName.equals("")) {
                        Toast.makeText(SqlitelistActivity.this, "Please fill up the all information.", Toast.LENGTH_SHORT).show();
                    } else {
                        long rowNumber = myDatabaseHelper.saveData(userId, userName);
                        if (rowNumber > -1) {
                            Toast.makeText(SqlitelistActivity.this, "Data is successfully inserted.", Toast.LENGTH_SHORT).show();
                            edtId.setText("");
                            edtName.setText("");
                        } else {
                            Toast.makeText(SqlitelistActivity.this, "Data isn't successfully inserted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnShow) {
                    startActivity(new Intent(SqlitelistActivity.this, ListDataActivity.class));
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = edtId.getText().toString();
                String userName = edtName.getText().toString();

                if (v.getId() == R.id.btnUpdate) {
                    Boolean isUpdate = myDatabaseHelper.updateData(userId, userName);

                    if (isUpdate == true) {
                        edtId.setText("");
                        edtName.setText("");
                        Toast.makeText(SqlitelistActivity.this, "Data is successfully Updated.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SqlitelistActivity.this, "Data is not updated.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = edtId.getText().toString();
                if (v.getId() == R.id.btnDelete) {
                    int value = myDatabaseHelper.deleteData(userId);
                    if (value < 0) {
                        Toast.makeText(SqlitelistActivity.this, "Data is not deleted.", Toast.LENGTH_SHORT).show();
                    } else {
                        edtId.setText("");
                        Toast.makeText(SqlitelistActivity.this, "Data is successfully deleted.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
