package com.monsterechno.sendmas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.OutputStream;

public class GenerateCode extends AppCompatActivity {

    public final static int QRCodeWidth = 500 ;
    private Bitmap bitmap;
    private ImageView imageViewQR;
    private Button buttonSave , buttonGen , buttonShare ;
    private EditText editText;
    private OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        editText = findViewById(R.id.editTextNamber);
        editText.addTextChangedListener(log);
        buttonGen = findViewById(R.id.buttonGen);
        buttonSave = findViewById(R.id.buttonSave);
        buttonShare = findViewById(R.id.buttonShare2);
        imageViewQR = findViewById(R.id.imageViewQR);

        buttonGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() == 0 ){
                    Toast.makeText(GenerateCode.this , "Enter What's number first" , Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        bitmap = textToImageEncode(editText.getText().toString().trim());
                        imageViewQR.setVisibility(View.VISIBLE);
                        imageViewQR.setImageBitmap(bitmap);
                        buttonSave.setVisibility(View.VISIBLE);
                        buttonShare.setVisibility(View.VISIBLE);
                        buttonSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap , "code_scanner"
                                        ,null);
                                Toast.makeText(GenerateCode.this , "Save to galary" , Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                        buttonShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*
                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("image/*");
                                sharingIntent.putExtra(Intent.EXTRA_STREAM, bitmap);
                                startActivity(sharingIntent);

                                Intent chooser = Intent.createChooser(sharingIntent, "Share photo");
                                if (sharingIntent.resolveActivity(getPackageManager()) != null ){
                                    startActivity(chooser);
                                }

                                 */

                            }
                        });

                    }catch (WriterException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // gen QR

    private Bitmap textToImageEncode(String value) throws WriterException {

        BitMatrix bitMatrix;

        try{
            bitMatrix = new MultiFormatWriter().encode(value ,
                    BarcodeFormat.DATA_MATRIX.QR_CODE , QRCodeWidth , QRCodeWidth , null );
        }catch (IllegalArgumentException e){
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y<bitMatrixHeight; y++ ){
            int offSet = y*bitMatrixWidth;
            for ( int x = 0; x < bitMatrixWidth; x ++ ){
                pixels[offSet + x] = bitMatrix.get(x , y)?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth , bitMatrixHeight , Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels,0 , 500 , 0 , 0 , bitMatrixWidth , bitMatrixHeight );
        return bitmap;
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
}