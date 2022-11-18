package com.monsterechno.sendmas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class urlmac extends AppCompatActivity {


    private EditText editText;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlmac);

    }

    public void makeurl(View view) {

        textView = findViewById(R.id.text);
        editText  = findViewById(R.id.editTextTextPersonName);
        String s = editText.getText().toString();
        textView.setText("https://wa.me/"+s);

        String url = "https://wa.me/"+s+"?text="+"https://wa.me/"+s;
        Intent i = new Intent(Intent.ACTION_VIEW);

        i.setData(Uri.parse(url));
        startActivity(i);

        closeKeyboard();

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}