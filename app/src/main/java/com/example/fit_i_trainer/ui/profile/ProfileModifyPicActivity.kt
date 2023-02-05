package com.example.fit_i_trainer.ui.profile


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.ActivityProfileModifyPicBinding
import com.example.fit_i_trainer.databinding.ActivityProfileModifyPicBinding.inflate

class ProfileModifyPicActivity: AppCompatActivity() {
    private lateinit var launcher: ActivityResultLauncher<Intent>
    val binding by lazy { ActivityProfileModifyPicBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun init() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = checkNotNull(result.data)
                val imageUri = intent.data
//                imageview.setImageURI(imageUri)
                    Glide.with(this)
                    .load(imageUri)
                    .into(binding.imageview)
            }
        }

        binding.btnAlbum.setOnClickListener {
            val intent = Intent().also { intent ->
                intent.type = "image/"
                intent.action = Intent.ACTION_GET_CONTENT
            }
            launcher.launch(intent)
        }
    }
}