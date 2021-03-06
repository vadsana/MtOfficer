package com.vadsana.mtofficer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userEditText, passwordEditText;
    private Button button;
    private TextView textView;
    private String strUser, strPassword, strTruePassword;
    private  boolean aBoolean = true;

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

        //For Login
        if (view == button){
         //Get value From Edit text
            String strUser = userEditText.getText().toString().trim();
            String strPassword = passwordEditText.getText().toString().trim();

            //check space
            if (strUser.equals("") || strPassword.equals("")){
                myAlert("มีช่องว่าง นะจ๊ะ");

            }else {
                try {
                    MyGetData myGetData = new MyGetData(MainActivity.this);
                    myGetData.execute("http://swiftcodingthai.com/4mar/getAung.php");

                    String strJSON = myGetData.get();
                    Log.d("19MarchV1", "JSon ==> "+ strJSON);

                    JSONArray jsonArray = new JSONArray(strJSON);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        if (strUser.equals(jsonObject.getString("User"))){
                            aBoolean =false;
                            strTruePassword = jsonObject.getString("Password");
                        }
                    }
                    if (aBoolean){
                        myAlert("User False");
                    }else if (strPassword.equals(strPassword)){
                        startActivity(new Intent(MainActivity.this, ShowOfficerActivity.class));
                        finish();
                    }else {
                        myAlert("Password False");
                    }
                }catch (Exception e){
                    Log.d("19MarchV1", "e ==>" + e.toString());
                }
            }
        }

        //For Register
        if (view == textView){
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));

        }
    }

    private void myAlert(String strMessage) {
        Toast.makeText(MainActivity.this, strMessage, Toast.LENGTH_SHORT).show();
    }
}
