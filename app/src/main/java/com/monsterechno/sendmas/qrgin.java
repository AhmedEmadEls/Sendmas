package com.monsterechno.sendmas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ImageWriter;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class qrgin extends AppCompatActivity {

    private static int REQUEST_CODE = 100 ;
    private EditText editText;
    private Button button , button5 ;
    ImageView imageView;
    private OutputStream outputStream;
    Bitmap qrBits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgin);
        editText = findViewById(R.id.editTextTextPersonName3);
        button = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        editText.addTextChangedListener(log);
        button5 = findViewById(R.id.button5);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setVisibility(View.VISIBLE);
                button5.setVisibility(View.VISIBLE);
                String num = ("https://wa.me/"+editText.getText().toString().trim());
                int x = QRGContents.ImageType.IMAGE_PNG;
                QRGEncoder qrgEncoder = new QRGEncoder(num,null,QRGContents.Type.TEXT ,800);
                qrBits =  qrgEncoder.getBitmap();
                imageView.setImageBitmap(qrBits);
                closeKeyboard();

            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(qrgin.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                   saveImage();
                } else {
                    askPermission();
                }
            }
        });
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(qrgin.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveImage();
            }else{
                Toast.makeText(qrgin.this , "Please provide the required permissons" , Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void saveImage() {
        File dir = new File(Environment.getExternalStorageDirectory(),"SaveImage");
        if (!dir.exists()){
            dir.mkdir();
        }
        Bitmap bitmap = qrBits;
        File file = new File(dir,System.currentTimeMillis()+".png");
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG , 100 ,outputStream);
        Toast.makeText(qrgin.this, "Succesfule saved" , Toast.LENGTH_SHORT).show();

        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private TextWatcher log = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String numper = editText.getText().toString().trim();
            button.setEnabled(!numper.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


}