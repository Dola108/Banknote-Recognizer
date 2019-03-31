package com.google.android.gms.samples.vision.ocrreader;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.opencv.imgproc.Imgproc.HISTCMP_BHATTACHARYYA;
import static org.opencv.imgproc.Imgproc.TM_CCOEFF_NORMED;
import static org.opencv.imgproc.Imgproc.calcHist;
import static org.opencv.imgproc.Imgproc.compareHist;

public class CompareImages extends AppCompatActivity {

    Scalar RED = new Scalar(255, 0, 0);
    Scalar GREEN = new Scalar(0, 255, 0);
    private String img1;
    private String img2;
    Button verifyButton;
    ImageView imgv1, imgv2;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        final double[] image_difference = new double[1];

        Button galleryButton = (Button) findViewById(R.id.button6);
        verifyButton = (Button) findViewById(R.id.button4);

        //final int dmg1 = R.drawable.real10tk;
        //final int dmg2 = R.drawable.sample10tk;

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                //******code for crop image
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);

                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        //verifyButton.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View view) {
        //        try {
        //            compare_image(dmg1, dmg2);
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        //        //print image_difference
        //        //TextView text = findViewById(R.id.textView10);
        //        //text.setText((int) image_difference[0]);
        //    }
        //});
    }

    @SuppressLint("SetTextI18n")
    private void compare_image(int dmg1, Bitmap dmg2) throws IOException {
        Mat image_1 = Utils.loadResource(this, dmg1, CvType.CV_8UC4);
        //Mat image_2 = Utils.loadResource(this, dmg2, CvType.CV_8UC4);
        Mat image_2 = new Mat();
        Bitmap bmp32 = dmg2.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, image_2);

        imgv1 = findViewById(R.id.imageView);
        imgv2 = findViewById(R.id.imageView3);

        Bitmap bm = Bitmap.createBitmap(image_1.cols(), image_1.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(image_1, bm);

        Bitmap cm = Bitmap.createBitmap(image_2.cols(), image_2.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(image_2, cm);

        imgv1.setImageBitmap(bm);
        imgv2.setImageBitmap(cm);

        double commutativeImageDiff = this.get_image_difference(image_1, image_2);
        TextView t = findViewById(R.id.textView10);

        int minImgDiff = 1;
        if (commutativeImageDiff < minImgDiff) {
            TextView text = findViewById(R.id.textView11);
            //text.setText("" + getDominantColor(image));
            text.setText("Match Percentage");
            t.setText(commutativeImageDiff*100 + "%");
        }
        else {
            TextView text = findViewById(R.id.textView11);
            text.setText("Not Matched");
            //t.setText(commutativeImageDiff*100 + "");
        }
    }

    @SuppressLint("SetTextI18n")
    private double get_image_difference(Mat image_1, Mat image_2) {
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        MatOfInt histsize = new MatOfInt(256);

        calcHist(Arrays.asList(image_1), new MatOfInt(0), new Mat(), image_1, histsize, ranges);
        calcHist(Arrays.asList(image_2), new MatOfInt(0), new Mat(), image_2, histsize, ranges);

        double img_hist_diff = compareHist(image_1, image_2, HISTCMP_BHATTACHARYYA);// img_hist_diff = 0

        int result_cols = image_1.cols() - image_2.cols() + 1;
        int result_rows = image_1.rows() - image_2.rows() + 1;
        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        Imgproc.matchTemplate(image_1, image_2, result, TM_CCOEFF_NORMED);

        Core.MinMaxLocResult minMaxLocRes = Core.minMaxLoc(result);

        double accuracy = minMaxLocRes.maxVal;
        Point location = minMaxLocRes.maxLoc;

        double img_template_diff = 1 - accuracy;    //accuracy = 1

        return (img_hist_diff / 10) + img_template_diff;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Bitmap bitmap = extras2.getParcelable("data");

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                byte[] byteArray = bStream.toByteArray();

                //Intent anotherIntent = new Intent(this, anotherActivity.class);
                //anotherIntent.putExtra("image", byteArray);
                //startActivity(anotherIntent);
                //finish();
                //Bitmap bmp;

                //byteArray = getIntent().getByteArrayExtra("image");
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                imgv2 = findViewById(R.id.imageView3);
                imgv2.setImageBitmap(bitmap);

                final Bitmap finalBitmap = bitmap;

                //Bundle extras = getIntent().getExtras();
                //final String msg = (String) extras.get("msg");
                //final String msg = getIntent().getExtras().getString("msg");


                verifyButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            //String msg = ((TextView) findViewById(R.id.noteInfo0)).getText().toString();
                            Intent intent = getIntent();
                            String msg = intent.getStringExtra("msg");
                            Toast.makeText(CompareImages.this, "$"+msg+"$",
                                    Toast.LENGTH_LONG).show();
                            int dmg1 = R.drawable.bdt10b;
                            if (msg == "TEN") {
                                dmg1 = R.drawable.bdt10b;
                            } else if (msg == "FIVE") {
                                dmg1 = R.drawable.bdt100b;
                            } else if (msg == "TWENTY") {
                                dmg1 = R.drawable.bdt100b;
                            } else if (msg == "FIFTY") {
                                dmg1 = R.drawable.bdt100b;
                            } else if (msg == "ONE HUNDRED") {
                                dmg1 = R.drawable.bdt100b;
                            } else if (msg =="FIVE HUNDRED") {
                                dmg1 = R.drawable.bdt500b;
                            } else if (msg == "ONE THOUSAND") {
                                dmg1 = R.drawable.bdt100b;
                            }
                            else {
                                final CharSequence[] items = {"2 TAKA", "5 TAKA", "10 TAKA", "20 TAKA", "50 TAKA", "100 TAKA", "500 TAKA", "1000 TAKA"};

                                AlertDialog.Builder builder = new AlertDialog.Builder(CompareImages.this);
                                builder.setTitle("Select Banknote");
                                builder.setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        final int dmg1;
                                        if (items[i].equals("2 TAKA")) {
                                            dmg1 = R.drawable.bdt2b;
                                        } else if (items[i].equals("5 TAKA")) {
                                            dmg1 = R.drawable.bdt5b;
                                        }  else if (items[i].equals("10 TAKA")) {
                                            dmg1 = R.drawable.bdt10b;
                                        } else if (items[i].equals("20 TAKA")) {
                                            dmg1 = R.drawable.bdt20b;
                                        } else if (items[i].equals("50 TAKA")) {
                                            dmg1 = R.drawable.bdt50b;
                                        } else if (items[i].equals("100 TAKA")) {
                                            dmg1 = R.drawable.bdt100b;
                                        } else if (items[i].equals("500 TAKA")) {
                                            dmg1 = R.drawable.bdt500b;
                                        } else if (items[i].equals("1000 TAKA")) {
                                            dmg1 = R.drawable.bdt1000b;
                                        } else {
                                            dmg1 = R.drawable.bdt10b;
                                        }

                                        try {
                                            compare_image(dmg1, finalBitmap);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                builder.show();
                            }
                            compare_image(dmg1, finalBitmap);
                            //compare_image(dmg1, finalBitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //print image_difference
                        //TextView text = findViewById(R.id.textView10);
                        //text.setText((int) image_difference[0]);
                    }
                });
                //   //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                //   //if (photo != null) {
                //   //    photo.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                //   //}
//
                //   ////img = photo;
//
                //   //imgv2 = findViewById(R.id.imageView3);
                //   //imgv2.setImageBitmap(photo);
            }


        }
    }
}
