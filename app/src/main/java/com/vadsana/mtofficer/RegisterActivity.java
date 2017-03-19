package com.vadsana.mtofficer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private EditText nameEditText, userEditText, passwordEditText;
    private Button button;
    private Uri uri;
    private String tag = "18MarchV1";
    private String pathImageString;
    private boolean aBoolean = true; // true ==> nonChoose Image, false ==> Choosed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bindWidget();

        controller();

    }   // Main Method

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            //For Setup Choose Image to ImageView
            try {

                uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // if

        //Find Path Choose Image
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathImageString = cursor.getString(index);
        } else {
            pathImageString = uri.getPath();
        }

        Log.d(tag, "pathImage ==> " + pathImageString);

        aBoolean = false;

    }   // onActivity

    private void controller() {
        imageView.setOnClickListener(RegisterActivity.this);
        button.setOnClickListener(RegisterActivity.this);
    }

    private void bindWidget() {

        imageView = (ImageView) findViewById(R.id.imbHumen);
        nameEditText = (EditText) findViewById(R.id.edtName);
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtPasserword);
        button = (Button) findViewById(R.id.btnRegister);

    }

    @Override
    public void onClick(View view) {

        //for Image
        if (view == imageView) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Please Choose App"), 1);

        }   // if

        //For Button
        if (view == button) {

            //Get Value from EditText
            String strName = nameEditText.getText().toString().trim();
            String strUser = userEditText.getText().toString().trim();
            String strPassword = passwordEditText.getText().toString().trim();

            if (aBoolean) {
                Toast.makeText(RegisterActivity.this, "Please Choose Image", Toast.LENGTH_SHORT).show();
            } else if ((strName.equals("")) || (strUser.length() == 0) || (strPassword.equals(""))) {
                Toast.makeText(RegisterActivity.this, "Please Fill All Blank", Toast.LENGTH_SHORT).show();
            } else {
                uploadToServer(strName, strUser, strPassword);
            }

        }   // if


    }   // onClick

    private void uploadToServer(String strName, String strUser, String strPassword) {

        //Upload Image to Server
        try {

            //Change Policy
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);


            //Upload Image my FTP
            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21,
                    "4mar@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("ImageMaster");
            simpleFTP.stor(new File(pathImageString));
            simpleFTP.disconnect();

            //For Upload Text
            MyPostData myPostData = new MyPostData(RegisterActivity.this,
                    strName,
                    strUser,
                    strPassword,
                    "http://swiftcodingthai.com/4mar/ImageMaster" +
                            pathImageString.substring(pathImageString.lastIndexOf("/")));

            myPostData.execute();

            if (Boolean.parseBoolean(myPostData.get())) {
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Log.d(tag, "e upload Image ==> " + e.toString());
        }






    }   // uploadToServer

}   // Main Class