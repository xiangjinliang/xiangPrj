package com.example.administrator.mytest1;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
//import java.util.jar.Manifest;
import android.Manifest;

public class Main3Activity extends AppCompatActivity {

    private ImageView picture;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button btntake = (Button) findViewById(R.id.btnTaskPhoto);
        picture = (ImageView) findViewById(R.id.picture);
        btntake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File outputImage = new File(getExternalCacheDir(), "output.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(Main3Activity.this, "com.example.administrator.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent cmaIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                cmaIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cmaIntent, TAKE_PHOTO);
            }
        });

        Button btnOpen = (Button) findViewById(R.id.btnopen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Main3Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    openImage();
                }
            }
        });
    }

    private void openImage() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestId, int resultCode, Intent data) {
        switch (requestId) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    handleImageOnkitKat(data);
                }
                break;
            default:
                break;
        }
    }


    @TargetApi(19)
    private void handleImageOnkitKat(Intent data)
    {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String sel = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,sel);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        } else {
            imagePath = uri.getPath();
        }
        display(imagePath);
    }
    private String getImagePath(Uri uri,String sel)
    {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null, sel,null,null);
        if (cursor != null)
        {
            if(cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void display(String imagePath)
    {
        if (imagePath != null )
        {

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);

        }
        else {
            Toast.makeText(this,"failed",Toast.LENGTH_LONG).show();
        }

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grandResults)
    {
        switch (requestCode)
        {
            case 1:
                if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openImage();
                } else
                {
                    Toast.makeText(this, "you are not granted.",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

}
