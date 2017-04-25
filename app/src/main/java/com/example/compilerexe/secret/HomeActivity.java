package com.example.compilerexe.secret;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.compilerexe.secret.model.DBHelper;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HomeActivity";

    Bundle bundle;
    DBHelper myDbHelper;
    Intent intent_main;
    EditText txt_remember1, txt_remember2, txt_remember3;
    Button btn_submit, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        intent_main = new Intent(this, MainActivity.class);

        txt_remember1 = (EditText) findViewById(R.id.txt_remember1);
        txt_remember2 = (EditText) findViewById(R.id.txt_remember2);
        txt_remember3 = (EditText) findViewById(R.id.txt_remember3);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_submit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        bundle = getIntent().getExtras();

        Log.d(TAG, "" + bundle.getString("ID") + bundle.getString("Email") + bundle.getString("Password"));

        // Read Secret
        myDbHelper = new DBHelper(this, null, null, 0);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String sql = "SELECT * FROM Secrets WHERE ID = ?";
        String[] whereArgs = {bundle.getString("ID")};
        Cursor cur = db.rawQuery(sql, whereArgs);

        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    Log.d(TAG, "" + cur.getString(0) + cur.getString(1) + cur.getString(2));
                    txt_remember1.setText(cur.getString(1));
                    txt_remember2.setText(cur.getString(2));
                    txt_remember3.setText(cur.getString(3));
                } while (cur.moveToNext());
            }
        }

        cur.close();
        // End Read Secret

    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit) {

            SQLiteDatabase db = myDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("Secret1", txt_remember1.getText().toString());
            values.put("Secret2", txt_remember2.getText().toString());
            values.put("Secret3", txt_remember3.getText().toString());

            String[] whereArgs = {bundle.getString("ID")};

            long updateSecret = db.update("Secrets", values, "ID = ?", whereArgs);

            if (updateSecret != -1) {
                Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Something wrong please again.", Toast.LENGTH_LONG);
                toast.show();
            }

        } else if (v == btn_cancel) {
            startActivity(intent_main);
        }
    }
}
