package com.monsterechno.sendmas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class qrgin extends AppCompatActivity {
    // widgets
    private static int REQUEST_CODE = 100 ;
    private EditText editText;
    private Button buttonGen , button5 , buttonCame;
    ImageView imageView;
    Bitmap qrBits;

    // vars
    public static final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgin);
        editText = findViewById(R.id.editTextTextPersonName3);
        buttonGen = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        editText.addTextChangedListener(log);
        button5 = findViewById(R.id.button5);
        buttonCame = findViewById(R.id.buttonCame);

        buttonGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(qrgin.this , GenerateCode.class);
                startActivity(intent);

                //https://telegram.me/+201021849411     Telegram

                /*
                imageView.setVisibility(View.VISIBLE);
                button5.setVisibility(View.VISIBLE);
                String num = ("https://wa.me/"+editText.getText().toString().trim());
                int x = QRGContents.ImageType.IMAGE_PNG;
                QRGEncoder qrgEncoder = new QRGEncoder(num,null,QRGContents.Type.TEXT ,800);
                qrBits =  qrgEncoder.getBitmap();
                imageView.setImageBitmap(qrBits);
                closeKeyboard();
                 */
            }
        });


        buttonCame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });

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
            buttonGen.setEnabled(!numper.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    // camera permission

    public void checkPermission(String permission, int requestCode){
                if (ContextCompat.checkSelfPermission(qrgin.this, permission)
                        == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(qrgin.this, new String[]{permission},
                            requestCode);
                }else {
                    Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
                }
    }


}