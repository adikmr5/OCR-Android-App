package com.example.android.searchyourdoubts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button imageBtn,continueBtn;
    private ImageView imageView;
    private TextView textView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap2;
    Uri uri,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageBtn=findViewById(R.id.camera_btn);
        imageView=findViewById(R.id.image);
        continueBtn=findViewById(R.id.continue_btn);
        textView=findViewById(R.id.test_output);

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(MainActivity.this);


            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"here",Toast.LENGTH_SHORT);

                showOutput();
            }
        });
    }




//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageBitmap2=imageBitmap;
//            imageView.setImageBitmap(imageBitmap);
//        }
//    }
    private void showOutput() {

        FirebaseVisionImage image = null;
        try {
            image = FirebaseVisionImage.fromFilePath(this,pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirebaseVisionTextRecognizer textRecognizer= FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                textView.setText("");
                displayText(firebaseVisionText);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("he", "fail");
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT);
            }
        });
    }

    private void displayText(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> blockList=firebaseVisionText.getTextBlocks();
        if(blockList.size()==0){
            Toast.makeText(this,"No Text Found",Toast.LENGTH_SHORT);
        }
        else{
            for (FirebaseVisionText.TextBlock block:firebaseVisionText.getTextBlocks()){
                String text=block.getText();
                textView.setText(text);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this,""+requestCode+"  "+resultCode,Toast.LENGTH_SHORT).show();
        if (requestCode == 200 && resultCode== Activity.RESULT_OK) {
            Uri imageuri=CropImage.getPickImageResultUri(this,data);
            imageView.setImageURI(imageuri);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)) {
                uri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);

            }
            else{
                startCrop(imageuri);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                imageView.setImageURI(result.getUri());
                pass=result.getUri();

            }
        }
    }

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
        .setGuidelines(CropImageView.Guidelines.ON)
        .setMultiTouchEnabled(true)
        .start(this);
    }

}