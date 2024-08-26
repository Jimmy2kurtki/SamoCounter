package com.lessons.samocounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class VremennoeReshenie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vremennoe_reshenie);

        EditText editTextVR = findViewById(R.id.editText_qr_VR);
        Button buttonVR = findViewById(R.id.button_qr_VR);
        ImageView imageViewVR = findViewById(R.id.qr_code);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewVR = findViewById(R.id.textView_qr_VR);

        buttonVR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                try {
                    String adresSim = editTextVR.getText().toString().toUpperCase();
                    String fullAdresSim = "https://wsh.bike?s=" + adresSim;
                    BitMatrix bitMatrix = multiFormatWriter.encode(fullAdresSim, BarcodeFormat.QR_CODE,300,300);

                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    imageViewVR.setImageBitmap(bitmap);
                    textViewVR.setText(adresSim);
                } catch (WriterException e){
                    throw new RuntimeException();
                }
            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(VremennoeReshenie.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
}