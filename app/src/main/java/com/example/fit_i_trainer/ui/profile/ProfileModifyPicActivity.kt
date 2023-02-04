package com.example.fit_i_trainer.ui.profile

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.ActivityProfileModifyPicBinding

class ProfileModifyPicActivity: AppCompatActivity() {
    var photoUri: Uri? = null
    lateinit var storagePermission:ActivityResultLauncher<String>
    lateinit var galleryLauncher:ActivityResultLauncher<String>
    val binding by lazy{ ActivityProfileModifyPicBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_modify_pic)
        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
            if(isGranted){
                setViews()
            }else{
                Toast.makeText(baseContext,"외부 저장소 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
//                uri->
//            binding.imagePreview.setImageURI(uri)
        }
        storagePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val goBack = findViewById<ImageButton>(R.id.ib_back_arrow)
        goBack.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent) //화면전환
            finish()
        }
    }
    fun setViews(){
        binding.btnAlbum.setOnClickListener{
            openGallery()
        }
    }
    fun openGallery(){
        galleryLauncher.launch("image/*")

    }
}