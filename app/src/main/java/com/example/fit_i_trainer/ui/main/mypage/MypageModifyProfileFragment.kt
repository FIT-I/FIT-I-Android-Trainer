package com.example.fit_i_trainer.ui.main.mypage

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.GetMypageResponse
import com.example.fit_i_trainer.data.service.CommunalService
import com.example.fit_i_trainer.databinding.FragmentMypageModifyProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageModifyProfileFragment : Fragment() {
    private var _binding: FragmentMypageModifyProfileBinding? = null
    private val binding: FragmentMypageModifyProfileBinding
        get() = requireNotNull(_binding) { "FragmentMypageModifyProfileBinding" }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageModifyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ibpre = view.findViewById<View>(R.id.ib_pre)
        val btnphoto = view.findViewById<View>(R.id.btn_click_photo)

        fun onBind(data: GetMypageResponse.Result) {
            binding.tvName.text = data.userName
            binding.tvEmail.text = data.email
            binding.tvAddress.text = data.location
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

        //이전
        ibpre.setOnClickListener {
            val mypageFragment = MypageFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            //이전 화면으로 이동
            transaction.replace(R.id.fl_container, mypageFragment)
            transaction.commit()

        }
        btnphoto.setOnClickListener {
//            photo(view)
            val dialogphoto = layoutInflater.inflate(R.layout.dialog_photo, null)
            val build = AlertDialog.Builder(view.context).apply {
                setView(dialogphoto)
            }
            val dialog = build.create()
            dialog.show()
            Log.d("post ","dialog success")

            val photo = dialogphoto.findViewById<Button>(R.id.btn_photo)
            val delete = dialogphoto.findViewById<Button>(R.id.btn_photo_delete)
            val cancel = dialogphoto.findViewById<Button>(R.id.btn_cancel)

            //프로필 사진 선택
            photo.setOnClickListener {
                Toast.makeText(context,"갤러리 이동",Toast.LENGTH_SHORT).show()
                Log.d("post","갤러리 성공")
            }
            delete.setOnClickListener {
                Toast.makeText(context,"프로필 삭제",Toast.LENGTH_SHORT).show()
                Log.d("post","삭제 성공")
            }
            cancel.setOnClickListener {
                dialog.dismiss()
                Log.d("post","취소 성공")
            }


        }
    }

//    fun photo(view: View) {
//        val dialogphoto = layoutInflater.inflate(R.layout.dialog_photo, null)
//
//        val build = AlertDialog.Builder(view.context).apply {
//            setView(dialogphoto)
//        }
//        val dialog = build.create()
//        dialog.show()

//        //갤러리 사진 추가
//        btnph.setOnClickListener {
//            Toast.makeText(view.context, "add photo", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        }
//        //프로필 사진 삭제
//        btnde.setOnClickListener {
//            Toast.makeText(view.context, "delete photo", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        }
//        //취소
//        btnca.setOnClickListener {
//            Toast.makeText(view.context, "cancel", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        }
//
//
//        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
//        transaction.commit()
//    }

}
