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
                    // ??????????????? ????????? ????????? ??????
                    onBind(response.body()!!.result)
                    Log.d("post", "onResponse ??????: " + response.body().toString());
                    //Toast.makeText(this@ProfileActivity, "???????????? ?????? ??????!", Toast.LENGTH_SHORT).show()

                } else {
                    // ????????? ????????? ??????(???????????? 3xx, 4xx ???)
                    Log.d("post", "onResponse ??????")
                }
            }

            override fun onFailure(call: Call<GetMypageResponse>, t: Throwable) {
                // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                Log.d("post", "onFailure ??????: " + t.message.toString());
            }
        })

        //?????? -????????????, ????????????
        ibsetting.setOnClickListener {
            val mypageSettingFragment = MypageLogoutFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageSettingFragment)
            transaction.commit()
        }

        //????????? ??????
        tvgotoprofile.setOnClickListener {
            val mypageModifyProfileFragment = MypageModifyProfileFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageModifyProfileFragment)
            transaction.commit()
        }

        val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)

        //????????? ???????????? ?????? ????????????
        swtmy.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                //????????? ?????? ????????? ?????? ??????
                trainerService.controlMatchingOnOff().enqueue(object :
                    Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            // ??????????????? ????????? ????????? ??????
                            Log.d("post", "onResponse ??????: " + response.body().toString());
                            Toast.makeText(
                                context,
                                response.body()?.result.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            // ????????? ????????? ??????(???????????? 3xx, 4xx ???)
                            Log.d("post", "onResponse ??????")
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                        Log.d("post", "onFailure ??????: " + t.message.toString());
                    }
                })
            } else {
                //????????? ?????? ?????? ??? ??????
                trainerService.controlMatchingOnOff().enqueue(object :
                    Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            // ??????????????? ????????? ????????? ??????
                            Log.d("post", "onResponse ??????: " + response.body().toString());
                            Toast.makeText(
                                context,
                                response.body()?.result.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            // ????????? ????????? ??????(???????????? 3xx, 4xx ???)
                            Log.d("post", "onResponse ??????")
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                        Log.d("post", "onFailure ??????: " + t.message.toString());
                    }
                })
            }
        }

        //?????? ??????
        binding.clLocation.setOnClickListener {
            val intent = Intent(context, MypageLocationActivity::class.java)  // ???????????? ????????????,
            startActivity(intent)  // ?????? ????????? ?????????
            //finish()
        }


        //??????????????????
        binding.clPwchange.setOnClickListener {
            val intent = Intent(context, MypageChangePwActivity::class.java)  // ???????????? ????????????,
            startActivity(intent)  // ?????? ????????? ?????????
            //finish()
        }

        //????????? ????????????
        binding.clCertiUp.setOnClickListener {
            val intent = Intent(context, MypageCerfiActivity::class.java)  // ???????????? ????????????,
            startActivity(intent)  // ?????? ????????? ?????????
        }

        // ????????????
        binding.clNotice.setOnClickListener {
            val mypageNoticeFragment = MypageNoticeFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypageNoticeFragment)
            transaction.commit()
        }

        //????????????
        binding.clPermission.setOnClickListener {
            val mypagepermissonFragment = MypagePermissonFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container, mypagepermissonFragment)
            transaction.commit()
        }
    }
}