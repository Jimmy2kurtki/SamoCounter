package com.lessons.samocounter.qrsim

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.lessons.samocounter.MainActivity
import com.lessons.samocounter.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class VremennoeReshenie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vremennoe_reshenie)

        val editTextVR: EditText = findViewById(R.id.editText_qr_VR)
        val buttonVR: Button = findViewById(R.id.button_qr_VR)
        val imageViewVR: ImageView = findViewById(R.id.qr_code)
        @SuppressLint("MissingInflatedId", "LocalSuppress")
        val textViewVR: TextView = findViewById(R.id.textView_qr_VR)

        buttonVR.setOnClickListener {
            val multiFormatWriter = MultiFormatWriter()

            try {
                val adresSim = editTextVR.text.toString().toUpperCase()
                val fullAdresSim = "https://wsh.bike?s=$adresSim"
                val bitMatrix = multiFormatWriter.encode(fullAdresSim, BarcodeFormat.QR_CODE, 300, 300)

                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.createBitmap(bitMatrix)

                imageViewVR.setImageBitmap(bitmap)
                //saveImage(bitmap)
                textViewVR.text = adresSim
            } catch (e: WriterException) {
                throw RuntimeException(e)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }

    @Throws(IOException::class)
    private fun saveImage(bitmap: Bitmap): Boolean {
        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString())
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/QR")
            }
            val imageUri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { contentResolver.openOutputStream(it) }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).toString() + File.separator + "QR"

            val file = File(imagesDir)

            if (!file.exists()) {
                file.mkdir()
            }

            val image = File(imagesDir, "${System.currentTimeMillis()}.png")
            fos = FileOutputStream(image)
        }

        return fos?.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) } ?: false
    }
    override fun onBackPressed() {
        super.onBackPressed()
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            // Handle exception if needed
        }
    }
}
