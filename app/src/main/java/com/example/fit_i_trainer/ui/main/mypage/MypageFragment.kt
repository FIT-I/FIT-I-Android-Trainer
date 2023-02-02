package com.example.fit_i_trainer.ui.main.mypage

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
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.FragmentMypageBinding
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
        val ivnextreset = view.findViewById<View>(R.id.iv_next_login_reset) as ImageView
        val ivaddcerti = view.findViewById<View>(R.id.iv_next_update) as ImageView
        val ivnextnotice = view.findViewById<View>(R.id.iv_next_notice) as ImageView
        val ivnextpermisson = view.findViewById<View>(R.id.iv_next_permisson) as ImageView
        val tvgotoprofile = view.findViewById<View>(R.id.tv_go_modifyProfile) as TextView
        val swtmy = view.findViewById<View>(R.id.swt_my) as Switch


        //설정
        ibsetting.setOnClickListener {
            val mypageSettingFragment = MypageSettingFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageSettingFragment)
            transaction.commit()
        }

        // 공지사항
        ivnextnotice.setOnClickListener {
            val mypageNoticeFragment = MypageNoticeFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageNoticeFragment)
            transaction.commit()
        }
        //프로필 수정
        tvgotoprofile.setOnClickListener {
            val mypageModifyProfileFragment = MypageModifyProfileFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageModifyProfileFragment)
            transaction.commit()
        }
        //이용약관
        ivnextpermisson.setOnClickListener {
            val mypagepermissonFragment = MypagePermissonFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypagepermissonFragment)
            transaction.commit()
        }
        //비밀번호변경
        ivnextreset.setOnClickListener {
            val mypageCertificateFragment = MypageCertificateFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            //transaction.replace(R.id.fl_container, )
            transaction.commit()
        }
        //자격증 업데이트
        ivaddcerti.setOnClickListener {
            val mypageCertificateFragment = MypageCertificateFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageCertificateFragment)
            transaction.commit()
        }
        //스위치 눌렀을때 기능 추가하기

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


    }
}