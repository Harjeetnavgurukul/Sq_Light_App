package com.draw.sq_light_app;

import
        android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class SqLiteApp extends AppCompatActivity {

    public static SQliteHelper mySQliteHelper;

    EditText edit, edit1, edit2;
    ImageView imageView;
    Button btn, btn1;

    final int REQUEST_CODE_GALLERY = 1;
    private Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sq_lite_app);

        edit = findViewById(R.id.ediName);
        edit1 = findViewById(R.id.ediPhone);
        edit2 = findViewById(R.id.edtAge);
        imageView = findViewById(R.id.Img);
        btn = findViewById(R.id.Button);
        btn1 = findViewById(R.id.Button1);

        mySQliteHelper = new SQliteHelper(this, "RECORD.SQlite", null, 1);
        mySQliteHelper.quaryData("CREATE TABLE IF NOT EXISTS RECORD (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age VARCHAR, phone VARCHAR, image BLOB)");


        //OnClick For ImageView.......
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(intent,REQUEST_CODE_GALLERY);

               }
        });


        //Onclick for AddData Button....
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mySQliteHelper.insertData(
                            edit.getText().toString().trim(),
                            edit1.getText().toString().trim(),
                            edit2.getText().toString().trim(),
                            imageViewToByte(imageView)
                    );
                    Toast.makeText(SqLiteApp.this, "Edit successes", Toast.LENGTH_SHORT).show();
                    edit.setText("");
                    edit1.setText("");
                    edit2.setText("");
                    imageView.setImageResource(R.drawable.ic_account_circle_black_24dp);
                }
                catch (Exception exp) {
                    exp.printStackTrace();
                }

            }
        });

        //Onclick For View All Data.....
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SqLiteApp.this, RecordListActivity.class));
            }
        });
    }

    public static byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, str);
        byte[] bytes = str.toByteArray();
        return bytes;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(this, "Don't have any permission", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_CODE_GALLERY &&  requestCode == RESULT_OK){
//            Uri uri = data.getData();
//            CropImage.activity(uri)
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1,1)
//                    .start(this);
//
//        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//            if (requestCode == RESULT_OK){
//                Uri resultUri = result.getUri();
//                imageView.setImageURI(resultUri);
//
//            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null & data.getData() != null) {
            ImageUri = data.getData();
            Picasso.get().load(ImageUri).into(imageView);
        }

    }
}

