package com.example.plant_disease_detection;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plant_disease_detection.ml.PestDetection;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Pest_Detection_Activity extends AppCompatActivity {

    TextView result, result2,  classified, clickHere;
    ImageView imageView;
    ImageButton cameraButton, uploadButton;

    int imageSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_detection);

        result2 = findViewById(R.id.result2);
        result = findViewById(R.id.result);
        cameraButton = findViewById(R.id.Button);
        uploadButton = findViewById(R.id.uploadButton);
        imageView = findViewById(R.id.imageview);


        clickHere = findViewById(R.id.click_here);
        classified = findViewById(R.id.classified);


        clickHere.setVisibility(View.GONE);

        classified.setVisibility(View.GONE);
        result.setVisibility(View.GONE);
        result2.setVisibility(View.GONE);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            handleImageCapture(data);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            handleImageUpload(data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleImageCapture(Intent data) {
        Bitmap image = (Bitmap) data.getExtras().get("data");
        processImage(image);
    }

    private void handleImageUpload(Intent data) {
        Uri selectedImage = data.getData();
        try {
            Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            processImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processImage(Bitmap image) {
        int dimension = Math.min(image.getWidth(), image.getHeight());
        image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
        imageView.setImageBitmap(image);


        clickHere.setVisibility(View.VISIBLE);

        classified.setVisibility(View.VISIBLE);
        result.setVisibility(View.VISIBLE);
        result2.setVisibility(View.VISIBLE);

        image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
        classifyImage(image);
    }

    private void classifyImage(Bitmap image) {
        try {
            PestDetection model = PestDetection.newInstance(getApplicationContext());

            //create input for reference
            TensorBuffer inputFeatureO = TensorBuffer.createFixedSize(new int[]{1,224,224,3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());


            //get 1D array of 224*224 pixels in image
            int[] intValue = new int[imageSize * imageSize];
            image.getPixels(intValue, 0, image.getWidth(), 0, 0,image.getWidth(), image.getHeight());

            //iterate over pixels and extract R,G,B  values, add to bytebuffer
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValue[pixel++]; //RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f/255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f/255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f/255.f));
                }
            }
            inputFeatureO.loadBuffer(byteBuffer);

            //run model inference and gets result

            PestDetection.Outputs outputs = model.process(inputFeatureO);
            TensorBuffer outputFeatureO = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidence = outputFeatureO.getFloatArray();

            //find the index...
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidence.length;i++){
                if(confidence[i] > maxConfidence){
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }
            String[] classes = {
                    "Ants", "Bees", "Beetle", "Catterpillar", "Earthworms", "Earwig", "Grasshopper", "Moth", "Slug", "Snail",
                    "Wasp", "Weevil", "Armyworm", "Brown Marmorated Stink Bug", "Cabbage Looper", "Citrus Canker",
                    "Colorado Potato Beetle", "Corn Borer", "Corn Earworm", "Dragonfly", "Fruit Fly", "Honey Bee", "Ladybug",
                    "Mantis", "Mosquito", "Spider Mites", "Thrip", "Tomato Hornworm", "Western Corn Rootworm"
            };



            if (maxConfidence < 0.98) {
                result2.setText("Detected image Does not Contains Pest. Please try again.");
                result2.setTextColor(Color.RED);
                result.setVisibility(View.GONE);
                for (int i = 0; i < confidence.length; i++) {
                    System.out.println("Class: " + classes[i] + ", Confidence: " + confidence[i]);
                }
            }
            else {

                for (int i = 0; i < confidence.length; i++) {
                    System.out.println("Class: " + classes[i] + ", Confidence: " + confidence[i]);
                }
                result2.setText("Dangerous effect of : " + classes[maxPos]);
                result2.setTextColor(Color.parseColor("#000000"));
                result.setTextColor(Color.parseColor("#000000"));
                result.setText(classes[maxPos]);
                result2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.google.com/search?q=" + result2.getText())));
                    }
                });
            }

            model.close();




        } catch (IOException e) {

        }

    }
}
