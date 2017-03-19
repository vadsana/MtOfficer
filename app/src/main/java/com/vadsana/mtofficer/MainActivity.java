package com.vadsana.mtofficer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userEditText, passwordEditText;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindwidget();

        controller();
    } //main method

    private void controller(){
        button.setOnClickListener(MainActivity.this);
        textView.setOnClickListener(MainActivity.this);
    }

    private void bindwidget(){
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtPasserword);
        button = (Button) findViewById(R.id.btnLogin);
        textView = (TextView) findViewById(R.id.txtRegister);
    }
    public void onClick(View view){
        //For Register
        if (view == textView){
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));

        }
    }
}
