package com.example.fit_i_trainer

import android.Manifest
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import coil.load
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ProfileModifyPicActivity: AppCompatActivity() {
    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(applicationContext, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    val btnAlbum = findViewById<ImageButton>(R.id.btn_album)
    val imgLoad = findViewById<ImageView>(R.id.img_load)
    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imgLoad.load(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission.launch(permissionList)

        btnAlbum.setOnClickListener {
            readImage.launch("image/*")
        }
    }
}