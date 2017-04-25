package com.example.compilerexe.agoda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.compilerexe.agoda.model.DBHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent_main;
    Button btn_submit, btn_cancel;
    EditText field_email, field_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        intent_main = new Intent(this, MainActivity.class);
        field_email = (EditText)findViewById(R.id.field_email);
        field_password = (EditText)findViewById(R.id.field_password);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_submit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_cancel) {
            startActivity(intent_main);
        } else if (v == btn_submit) {
            String getInputEmail = field_email.getText().toString();
            String getInputPassword = field_password.getText().toString();

            if (!TextUtils.isEmpty(getInputEmail) && !TextUtils.isEmpty(getInputPassword)) {
                DBHelper myDbHelper = new DBHelper(this, null, null, 0);
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("Email", getInputEmail);
                values.put("Password", getInputPassword);
                long newCustomers = db.insert("Customers", null, values);

                if (newCustomers != -1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG);
                    toast.show();
                    field_email.setText(null);
                    field_password.setText(null);
                    startActivity(intent_main);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Something wrong please again.", Toast.LENGTH_LONG);
                    toast.show();
                    field_email.setText(null);
                    field_password.setText(null);
                }

            }
        }
    }
}
