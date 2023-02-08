package com.example.fit_i_trainer.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.request.ModifyTrainerInfoRequest
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.ActivityProfileBinding
import com.example.fit_i_trainer.ui.profile.modify.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    var costHour : String? = null
    var intro : String? = null
    var name : String? = null
    var serviceDetail : String? = null


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

        fun onBind(data: GetTrainerInfoResponse.Result?) {
            binding.tvTrainerName.text = data?.name
            binding.tvManagePrice.text= data?.cost.toString()
            binding.tvAboutMe.text = data?.intro
            binding.tvAboutService.text =data?.service
            binding.tvTrainerStar.text = data?.grade.toString()
            binding.tvAverageValue.text = data?.grade.toString()
            binding.tvUniversityInfo.text = data?.school
            binding.tvReviewNum.text = data?.reviewDto?.size.toString() //리뷰 총개수

            if (data?.profile != "trainerProfile") {
                Glide.with(this)
                    .load("${data?.profile}")
                    .into(binding.ivTrainerProfile)
                Log.d("post", data?.profile.toString())
            }

            if (data?.background != null) {
                Glide.with(this)
                    .load("${data.background}")
                    .into(binding.ivBackgroundPhoto)
            }

            costHour=data?.cost
            intro=data?.intro
            name=data?.name
            serviceDetail=data?.service

            when (data?.category) {
                "diet" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_diet)
                    binding.tvCategoryPick.text="다이어트"
                }
                "pt" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_pt)
                    binding.tvCategoryPick.text="개인 PT"
                }
                "friend" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_friend)
                    binding.tvCategoryPick.text="운동친구"
                }
                "rehab" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_medical)
                    binding.tvCategoryPick.text="재활치료"
                }
                "food" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_eating)
                    binding.tvCategoryPick.text="식단관리"
                }
            }

            when (data?.levelName) {
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
                    response.body()?.let { onBind(it?.result) }
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

        //카테고리
        modifyCategory.setOnClickListener{
            val intent = Intent(this, ProfileModifyCategoryPickActivity::class.java)
            startActivity(intent)
            finish()
        }

        //관리 비용
        modifyCost.setOnClickListener{
            val intent = Intent(this, ProfileModifyCostActivity::class.java)
            intent.putExtra("modify",ModifyTrainerInfoRequest(
                costHour,intro,name,serviceDetail))
            startActivity(intent)
            finish()
        }

        //소개 글
        modifyMe.setOnClickListener{
            val intent = Intent(this, ProfileModifyMeActivity::class.java)
            intent.putExtra("modify",ModifyTrainerInfoRequest(
                costHour,intro,name,serviceDetail))
            startActivity(intent)
            finish()
        }

        //서비스 상세 설명
        modifyService.setOnClickListener{
            val intent = Intent(this, ProfileModifyServiceActivity::class.java)
            intent.putExtra("modify",ModifyTrainerInfoRequest(
                costHour,intro,name,serviceDetail))
            startActivity(intent)
            finish()
        }

        //사진 및 자격증
        modifyPic.setOnClickListener{
            val intent = Intent(this, ProfileModifyPicActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}