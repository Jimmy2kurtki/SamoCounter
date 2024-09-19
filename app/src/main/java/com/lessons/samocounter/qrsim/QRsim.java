package com.lessons.samocounter.qrsim;


import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.lessons.samocounter.R;

import java.io.FileNotFoundException;

public class QRsim extends AppCompatActivity {

    final static int REQUEST_CODE = 1232;

    EditText editTextQrForPdf;
    Button buttonQrForPdf;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrsim);
        askPermission();

        editTextQrForPdf = findViewById(R.id.editText_qr_for_pdf);
        buttonQrForPdf = findViewById(R.id.button_qr_for_pdf);

        buttonQrForPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    void  createPdf (String QrSim) throws FileNotFoundException {

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 1920, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

// размер текста 24

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(24);

        String text = editTextQrForPdf.getText().toString();
        float x = 300;
        float y = 500;

        canvas.drawText(text,x,y,paint);
        document.finishPage(page);


    }

    void askPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }
}