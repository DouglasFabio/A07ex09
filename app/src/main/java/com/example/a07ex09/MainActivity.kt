package com.example.a07ex09

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var btnCapturar: Button
    lateinit var imgFoto: ImageView
    lateinit var txtResultadoQRCode: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCapturar = findViewById(R.id.btnCapturar)
        imgFoto = findViewById(R.id.imgFoto)
        txtResultadoQRCode = findViewById(R.id.txtResultadoQRCode)

    }

    val register = registerForActivityResult(ActivityResultContracts.TakePicturePreview())
    {
            image: Bitmap? ->
        imgFoto.setImageBitmap(image)
        val scanner = BarcodeScanning.getClient()
        val bitmap = InputImage.fromBitmap(image!!, 0)
        val result = scanner.process(bitmap)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    val valueType = barcode.valueType
                    when (valueType) {
                        Barcode.TYPE_URL -> {
                            val url = barcode.url!!.url
                            findViewById<TextView>(R.id.txtResultadoQRCode).text = url.toString()
                        }
                    }
                }
            }
            .addOnFailureListener{
                Log.e("======>", it.printStackTrace().toString())
            }
    }

    fun capturarFoto(view: View){
        register.launch(null)
    }
}