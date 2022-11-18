package com.monsterechno.sendmas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GenerateCode extends AppCompatActivity {

    public final static int QRCodeWidth = 500 ;
    Bitmap bitmap;
    private ImageView imageViewQR;
    private Button buttonSave , buttonGen;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        editText = findViewById(R.id.editTextNamber);
        buttonGen = findViewById(R.id.buttonGen);
        buttonSave = findViewById(R.id.buttonSave);
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
                        buttonSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap , "code_scanner"
                                        ,null);
                                Toast.makeText(GenerateCode.this , "Save to galary" , Toast.LENGTH_SHORT)
                                        .show();
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
}