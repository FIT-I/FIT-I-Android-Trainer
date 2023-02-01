package com.example.fit_i_trainer.ui.main.home

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.FragmentHomeBinding
import com.example.fit_i_trainer.ui.profile.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private val rankImgArray = arrayOf(
        R.drawable.img_rank_gold,
        R.drawable.img_rank_sliver,
        R.drawable.img_rank_bronze,
    )


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

        fun onBind(data: GetTrainerInfoResponse.Result) {
            binding.tvName.text = data.name
            //binding.tvEmail.text=data.
            binding.tvIntro.text = data.intro
            binding.tvSchool.text = data.school
            binding.tvGrade.text = data.grade.toString()
            binding.tvCategory.text = data.category

            when (data.category) {
                "다이어트" -> binding.ivCategory.setImageResource(R.drawable.ic_diet)
                "개인 PT" ->binding.ivCategory.setImageResource(R.drawable.ic_pt)
                "운동친구" -> binding.ivCategory.setImageResource(R.drawable.ic_friend)
                "재활치료" ->binding.ivCategory.setImageResource(R.drawable.ic_medical)
                "식단관리" -> binding.ivCategory.setImageResource(R.drawable.ic_eating)
            }

            when (data.levelName) {
                "gold" -> binding.ivRank.setImageResource(R.drawable.img_rank_gold)
                "sliver" -> binding.ivRank.setImageResource(R.drawable.img_rank_sliver)
                "bronze" -> binding.ivRank.setImageResource(R.drawable.img_rank_bronze)
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

        btnModify.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)  // 화면 전환을 시켜줌
            //Toast.makeText(context,"비밀번호가 변경되었습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, pw1 + " changePW", Toast.LENGTH_SHORT).show()
        }
    }
}