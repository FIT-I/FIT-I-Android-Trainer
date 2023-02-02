package com.example.fit_i_trainer.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.ActivityLoginBinding
import com.example.fit_i_trainer.databinding.ActivityProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val modifyBackground = findViewById<ImageButton>(R.id.btn_modify_background)
        val modifyCost = findViewById<ImageButton>(R.id.btn_modify_cost)
        val modifyMe= findViewById<ImageButton>(R.id.btn_modify_me)
        val modifyService= findViewById<ImageButton>(R.id.btn_modify_service)
        val modifyPic= findViewById<ImageButton>(R.id.btn_modify_pic)
        val modifyCategory = findViewById<ImageButton>(R.id.btn_category_pick)
        val moreAboutMe = findViewById<ImageButton>(R.id.btn_about_me)

        fun onBind(data: GetTrainerInfoResponse.Result) {
            binding.tvTrainerName.text = data.name
            //binding.tvEmail.text=data.
            binding.tvAboutMe.text = data.intro
            binding.tvTrainerStar.text = data.grade.toString()
            binding.tvUniversityInfo.text = data.school
            binding.tvAverage.text = data.grade.toString()
            binding.tvCategoryPick.text = data.category

//            when (data.category) {
//                "다이어트" -> binding.grade.setImageResource(R.drawable.ic_diet)
//                "개인 PT" ->binding.ivCategory.setImageResource(R.drawable.ic_pt)
//                "운동친구" -> binding.ivCategory.setImageResource(R.drawable.ic_friend)
//                "재활치료" ->binding.ivCategory.setImageResource(R.drawable.ic_medical)
//                "식단관리" -> binding.ivCategory.setImageResource(R.drawable.ic_eating)
//            }

            when (data.levelName) {
                "gold" -> binding.ivTrainerGrade.setImageResource(R.drawable.img_rank_gold)
                "sliver" -> binding.ivTrainerGrade.setImageResource(R.drawable.img_rank_sliver)
                "bronze" -> binding.ivTrainerGrade.setImageResource(R.drawable.img_rank_bronze)
            }
        }

        val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
        trainerService.getTrainerInfo().enqueue(object :
            Callback<GetTrainerInfoResponse> {
            override fun onResponse(
                call: Call<GetTrainerInfoResponse>,
                response: Response<GetTrainerInfoResponse>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    onBind(response.body()!!.result)
                    Log.d("post", "onResponse 성공: " + response.body().toString());
                    //Toast.makeText(this@ProfileActivity, "비밀번호 찾기 성공!", Toast.LENGTH_SHORT).show()

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("post", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<GetTrainerInfoResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("post", "onFailure 에러: " + t.message.toString());
            }
        })




        fun showAboutMe(){
            val intent = Intent(this, ProfileAboutMeActivity::class.java)
            startActivity(intent)
            finish()
        }
        moreAboutMe.setOnClickListener{
            showAboutMe()
        }

        val moreAboutService = findViewById<ImageButton>(R.id.btn_about_service)
        fun showAboutService(){
            val intent = Intent(this, ProfileAboutServiceActivity::class.java)
            startActivity(intent)
            finish()

        }
        moreAboutService.setOnClickListener{
            showAboutService()
        }
        fun changeCost(){
            val intent = Intent(this, ProfileModifyCostActivity::class.java)
            startActivity(intent)
            finish()

        }
        modifyCost.setOnClickListener{
            changeCost()
        }

        fun changeMe(){
            val intent = Intent(this, ProfileModifyMeActivity::class.java)
            startActivity(intent)
            finish()

        }
        modifyMe.setOnClickListener{
            changeMe()
        }

        fun changeService(){
            val intent = Intent(this, ProfileModifyServiceActivity::class.java)
            startActivity(intent)
            finish()

        }
        modifyService.setOnClickListener{
            changeService()
        }

        fun changePic(){
            val intent = Intent(this, ProfileModifyPicActivity::class.java)
            startActivity(intent)
            finish()

        }
        modifyPic.setOnClickListener{
            changePic()
        }
        fun changeCategory(){
            val intent = Intent(this,ProfileModifyCategoryPickActivity::class.java)
            startActivity(intent)
            finish()

        }
        modifyCategory.setOnClickListener{
            changeCategory()
        }
    }
}