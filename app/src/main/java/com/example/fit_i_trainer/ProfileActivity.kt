package com.example.fit_i_trainer

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

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
            val intent = Intent(this,ProfileAboutMeActivity::class.java)
            startActivity(intent)
        }
        moreAboutMe.setOnClickListener{
            showAboutMe()
        }

        val moreAboutService = findViewById<ImageButton>(R.id.btn_about_service)
        fun showAboutService(){
            val intent = Intent(this,ProfileAboutServiceActivity::class.java)
            startActivity(intent)
        }
        moreAboutService.setOnClickListener{
            showAboutService()
        }
        fun changeCost(){
            val intent = Intent(this,ProfileModifyCostActivity::class.java)
            startActivity(intent)
        }
        modifyCost.setOnClickListener{
            changeCost()
        }

        fun changeMe(){
            val intent = Intent(this,ProfileModifyMeActivity::class.java)
            startActivity(intent)
        }
        modifyMe.setOnClickListener{
            changeMe()
        }

        fun changeService(){
            val intent = Intent(this,ProfileModifyServiceActivity::class.java)
            startActivity(intent)
        }
        modifyService.setOnClickListener{
            changeService()
        }

        fun changePic(){
            val intent = Intent(this,ProfileModifyPicActivity::class.java)
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