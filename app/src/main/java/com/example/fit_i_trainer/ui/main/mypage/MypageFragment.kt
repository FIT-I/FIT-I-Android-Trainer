package com.example.fit_i_trainer.ui.main.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetMypageResponse
import com.example.fit_i_trainer.data.service.CommunalService
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.FragmentMypageBinding
import com.example.fit_i_trainer.ui.main.mypage.notice.MypageNoticeFragment
import com.example.fit_i_trainer.ui.main.mypage.setting.MypageLogoutFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding: FragmentMypageBinding
        get() = requireNotNull(_binding) { "FragmentMypageBinding" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ibsetting = view.findViewById<View>(R.id.ib_setting) as ImageButton
        val tvgotoprofile = view.findViewById<View>(R.id.tv_go_modifyProfile) as TextView

        val ivlocation = view.findViewById<View>(R.id.iv_next_location) as ImageView
        val ivnextreset = view.findViewById<View>(R.id.iv_next_login_reset) as ImageView
        val ivaddcerti = view.findViewById<View>(R.id.iv_next_update) as ImageView
        val ivnextnotice = view.findViewById<View>(R.id.iv_next_notice) as ImageView
        val ivnextpermisson = view.findViewById<View>(R.id.iv_next_permisson) as ImageView

        val swtmy = view.findViewById<View>(R.id.swt_my) as Switch

        fun onBind(data: GetMypageResponse.Result) {
            binding.tvNameM.text = data.userName
            binding.tvEmailM.text = data.email
        }

        val commmunalService = RetrofitImpl.getApiClient().create(CommunalService::class.java)
        commmunalService.getMypage().enqueue(object :
            Callback<GetMypageResponse> {
            override fun onResponse(
                call: Call<GetMypageResponse>,
                response: Response<GetMypageResponse>
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

            override fun onFailure(call: Call<GetMypageResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("post", "onFailure 에러: " + t.message.toString());
            }
        })

        //설정 -로그아웃, 탈퇴하기
        ibsetting.setOnClickListener {
            val mypageSettingFragment = MypageLogoutFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageSettingFragment)
            transaction.commit()
        }

        //프로필 수정
        tvgotoprofile.setOnClickListener {
            val mypageModifyProfileFragment = MypageModifyProfileFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageModifyProfileFragment)
            transaction.commit()
        }

        val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)

        //스위치 눌렀을때 기능 추가하기
        swtmy.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                //체크된 상태 취소시 반응 추가
                trainerService.controlMatchingOnOff().enqueue(object :
                    Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            // 정상적으로 통신이 성공된 경우
                            Log.d("post", "onResponse 성공: " + response.body().toString());
                            Toast.makeText(
                                context,
                                response.body()?.result.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d("post", "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                        Log.d("post", "onFailure 에러: " + t.message.toString());
                    }
                })
            } else {
                //체크된 상태 만들 시 코드
                trainerService.controlMatchingOnOff().enqueue(object :
                    Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            // 정상적으로 통신이 성공된 경우
                            Log.d("post", "onResponse 성공: " + response.body().toString());
                            Toast.makeText(
                                context,
                                response.body()?.result.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d("post", "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                        Log.d("post", "onFailure 에러: " + t.message.toString());
                    }
                })
            }
        }

        //위치 설정
        binding.clLocation.setOnClickListener {
            val intent = Intent(context, MypageLocationActivity::class.java)  // 인텐트를 생성해줌,
            startActivity(intent)  // 화면 전환을 시켜줌
            //finish()
        }


        //비밀번호변경
        binding.clPwchange.setOnClickListener {
            val intent = Intent(context, MypageChangePwActivity::class.java)  // 인텐트를 생성해줌,
            startActivity(intent)  // 화면 전환을 시켜줌
            //finish()
        }

        //자격증 업데이트
        binding.clCertiUp.setOnClickListener {
            val intent = Intent(context, MypageCerfiActivity::class.java)  // 인텐트를 생성해줌,
            startActivity(intent)  // 화면 전환을 시켜줌
        }

        // 공지사항
        binding.clNotice.setOnClickListener {
            val mypageNoticeFragment = MypageNoticeFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageNoticeFragment)
            transaction.commit()
        }

        //이용약관
        binding.clPermission.setOnClickListener {
            val mypagepermissonFragment = MypagePermissonFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypagepermissonFragment)
            transaction.commit()
        }
    }
}