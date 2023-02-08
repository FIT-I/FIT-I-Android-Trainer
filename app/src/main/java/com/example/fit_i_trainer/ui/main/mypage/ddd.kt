package com.example.fit_i_trainer.ui.main.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R

class ddd : AppCompatActivity() {
//    private val TAG = this.javaClass.simpleName
//    var imageview: ImageView? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        imageview = imageview!!.findViewById(R.id.about_service_icon)
//        imageview.setOnClickListener(View.OnClickListener { v: View? ->
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            launcher.launch(intent)
//        })
//    }
//
//    var launcher = registerForActivityResult<Intent, ActivityResult>(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == RESULT_OK) {
//            Log.e(TAG, "result : $result")
//            val intent = result.data
//            Log.e(TAG, "intent : $intent")
//            val uri = intent!!.data
//            Log.e(TAG, "uri : $uri")
//            //                        imageview.setImageURI(uri);
//            Glide.with(this@ddd)
//                .load(uri)
//                .into(imageview!!)
//        }
//    }
}