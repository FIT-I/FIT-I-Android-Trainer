package com.example.fit_i_trainer.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.GetTrainerHomeResponse
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.FragmentHomeBinding
import com.example.fit_i_trainer.ui.profile.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "FragmentHomeBinding" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnModify = view.findViewById<TextView>(R.id.tv_go_modify)

        fun onBind(data: GetTrainerHomeResponse.Result) {
            binding.tvName.text = data.name
            binding.tvEmail.text=data.email
            binding.tvCategory.text = data.category
            binding.tvGrade.text = data.grade.toString()
            //자격증
            binding.tvCerti.text=data.certificateNum.toString()
            binding.tvSchool.text = data.school
            binding.tvIntro.text = data.contents

            when (data.category) {
                "diet" -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_diet)
                    binding.tvCategory.text="다이어트"
                }
                "pt" -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_pt)
                    binding.tvCategory.text="개인 PT"
                }
                "friend" -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_friend)
                    binding.tvCategory.text="운동친구"
                }
                "rehab" -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_medical)
                    binding.tvCategory.text="재활치료"
                }
                "food" -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_eating)
                    binding.tvCategory.text="식단관리"
                }
            }

            when (data.levelName) {
                "gold" -> binding.ivRank.setImageResource(R.drawable.img_rank_gold)
                "sliver" -> binding.ivRank.setImageResource(R.drawable.img_rank_sliver)
                "bronze" -> binding.ivRank.setImageResource(R.drawable.img_rank_bronze)
            }
        }

        val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
        trainerService.getTrainerHome().enqueue(object :
            Callback<GetTrainerHomeResponse> {
            override fun onResponse(
                call: Call<GetTrainerHomeResponse>,
                response: Response<GetTrainerHomeResponse>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    onBind(response.body()!!.result)
                    Log.d("post", "onResponse 성공: " + response.body().toString());

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("post", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<GetTrainerHomeResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("post", "onFailure 에러: " + t.message.toString());
            }
        })

        btnModify.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)  // 화면 전환을 시켜줌
        }
    }
}