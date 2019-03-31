package com.google.android.gms.samples.vision.ocrreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Output_activity extends AppCompatActivity {

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    ImageView imageView;
    Button imgButton;
    private final int requestCode = 20;
    String message =  "";

    //EditText output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_activity);
        //output=(EditText) findViewById(R.id.img_output);
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("key");
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        imgButton = findViewById(R.id.button5);

        imageView = findViewById(R.id.imageView5);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        TextView noteInfo = findViewById(R.id.noteInfo0);
        noteInfo.setText(message);
    }

    //public String getMessage() {
    //    return message;
    //}

    //@Override
    //public boolean onKeyDown(int keyCode, KeyEvent event)
    //{
    //    if ((keyCode == KeyEvent.KEYCODE_BACK))
    //    {
    //        finish();
    //    }
    //    return super.onKeyDown(keyCode, event);
    //}

    private void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Output_activity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                }
                //img = bitmap;

                imageView.setImageBitmap(bitmap);

            } else if (requestCode == SELECT_FILE) {

                // Uri selectImgUri = data.getData();
                //  imageView.setImageURI(selectImgUri);
                //Bundle extras2 = data.getExtras();
                //if (extras2 != null) {
                //    Bitmap photo = extras2.getParcelable("data");
//
                //    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                //    if (photo != null) {
                //        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                //    }
//
                //    //img = photo;
//
                //    imageView.setImageBitmap(photo);
//
                //}

            }

            imgButton.setText ("NEXT");
            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Output_activity.this, CompareImages.class);

                    //Intent intent2=new Intent(this,CompareImages.class);
                    intent.putExtra("msg", message);
                    startActivity(intent);
                }
            });
        }
    }
}

