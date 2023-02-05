package com.example.fit_i_trainer.ui.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R

class ProfileActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val modifyBackground = findViewById<ImageButton>(R.id.btn_modify_background)
        val modifyCost = findViewById<ImageButton>(R.id.btn_modify_cost)
        val modifyMe= findViewById<ImageButton>(R.id.btn_modify_me)
        val modifyService= findViewById<ImageButton>(R.id.btn_modify_service)
        val modifyPic= findViewById<ImageButton>(R.id.btn_modify_pic)
        val modifyCategory = findViewById<ImageButton>(R.id.btn_category_pick)
        val moreAboutMe = findViewById<ImageButton>(R.id.btn_about_me)
        fun showAboutMe(){
            val intent = Intent(this, ProfileAboutMeActivity::class.java)
            startActivity(intent)
        }
        moreAboutMe.setOnClickListener{
            showAboutMe()
        }
        fun background(){
            val intent=Intent(this,ProfileModifyBackgroundActivity::class::class.java)
            startActivity(intent)
        }
        modifyBackground.setOnClickListener{
            AlertDialog.Builder(this)
            val builder = AlertDialog.Builder(this)
            val arr = arrayOf("앨범에서 선택","배경사진 삭제","취소")
            builder.setItems(arr,object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?,pos:Int){
                    if(pos == 0){
                        //앨범에서 선택
                        background()
                    }
                    else if(pos ==1){
                        //배경사진 삭제

                    }
                  // Toast.makeText(baseContext,"${arr[pos]}",Toast.LENGTH_SHORT).show()
                }
                        })
                .create()
                .show()
        }

        val moreAboutService = findViewById<ImageButton>(R.id.btn_about_service)
        fun showAboutService(){
            val intent = Intent(this, ProfileAboutServiceActivity::class.java)
            startActivity(intent)
        }
        moreAboutService.setOnClickListener{
            showAboutService()
        }
        fun changeCost(){
            val intent = Intent(this, ProfileModifyCostActivity::class.java)
            startActivity(intent)
        }
        modifyCost.setOnClickListener{
            changeCost()
        }

        fun changeMe(){
            val intent = Intent(this, ProfileModifyMeActivity::class.java)
            startActivity(intent)
        }
        modifyMe.setOnClickListener{
            changeMe()
        }

        fun changeService(){
            val intent = Intent(this, ProfileModifyServiceActivity::class.java)
            startActivity(intent)
        }
        modifyService.setOnClickListener{
            changeService()
        }

        fun changePic(){
            val intent = Intent(this, ProfileModifyPicActivity::class.java)
            startActivity(intent)
        }
        modifyPic.setOnClickListener{
            changePic()
        }
        fun changeCategory(){
            val intent = Intent(this,ProfileModifyCategoryPickActivity::class.java)
            startActivity(intent)
        }
        modifyCategory.setOnClickListener{
            changeCategory()
        }
    }

}