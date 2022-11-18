package com.monsterechno.sendmas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private EditText editText2;
    private Button button , button_massege;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         editText = findViewById(R.id.editTextTextPersonName);
         editText2 = findViewById(R.id.editTextTextPersonName2);
         button = findViewById(R.id.button);

         editText.addTextChangedListener(log);
         editText2.addTextChangedListener(log);


         editText2.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 if (v.getId()==R.id.editTextTextPersonName2){
                     v.getParent().requestDisallowInterceptTouchEvent(true);
                     switch (event.getAction() & MotionEvent.ACTION_MASK)
                     {
                         case MotionEvent.ACTION_UP: v.getParent().requestDisallowInterceptTouchEvent(false);
                         break;
                     }
                 }
                 return false;
             }
         });

         button_massege = findViewById(R.id.sendmessage);


    }

    private TextWatcher log = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String numper = editText.getText().toString().trim();
            String ma = editText2.getText().toString().trim();
            button.setEnabled(!numper.isEmpty() && !ma.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    public void send(View view) {
        EditText editText = findViewById(R.id.editTextTextPersonName);
        EditText editText2 = findViewById(R.id.editTextTextPersonName2);
        TextView textView = findViewById(R.id.text);
         String s = editText.getText().toString();
         textView.setText("https://wa.me/"+s);
        String t  = editText2.getText().toString();

        String url = "https://wa.me/"+s+"?text="+t;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    public void ceariat(View view) {
        Intent intent = new Intent(this,qrgin.class);
        startActivity(intent);
    }

    public void URL(View view) {
        Intent intent = new Intent(this,urlmac.class);
        startActivity(intent);
    }


    public void hiden_key(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);

    }


    public void hiden_key1(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);

    }

    public void hiden_key2(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);

    }
}