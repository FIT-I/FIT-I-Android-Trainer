package com.example.fit_i_trainer.ui.main.mypage

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetMypageResponse
import com.example.fit_i_trainer.data.service.CommunalService
import com.example.fit_i_trainer.data.service.TrainerService
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

        val btnphoto = view.findViewById<View>(R.id.btn_click_photo)
        val ibpre  = view.findViewById<View>(R.id.ib_pre1)

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

        //사진 수정 버튼
        btnphoto.setOnClickListener {

            val dialogphoto = layoutInflater.inflate(R.layout.dialog_photo, null)
            val build = AlertDialog.Builder(view.context).apply {
                setView(dialogphoto)
            }
            val dialog = build.create()
            dialog.show()
            Log.d("post ", "dialog success")

            val photo = dialogphoto.findViewById<Button>(R.id.btn_photo)
            val delete = dialogphoto.findViewById<Button>(R.id.btn_photo_delete)
            val cancel = dialogphoto.findViewById<Button>(R.id.btn_cancel)

            //프로필 사진 선택
            photo.setOnClickListener {
                //갤러리 연동 기능 추가하기
                val pintent = Intent(Intent.ACTION_PICK) //intent를 통해서 뭘 열까? -> 갤러리
                pintent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                pintent.action = Intent.ACTION_GET_CONTENT // 갤러리에서 사진 가져오기
                //갤러리에서 선택 후 이미지뷰에 띄우는 기능 구현
                imageResult.launch(pintent)

                Toast.makeText(context, "갤러리 이동", Toast.LENGTH_SHORT).show()
                Log.d("post", "갤러리 성공")

            }
            //사진 삭제
            delete.setOnClickListener {
//                val profile = view.findViewById<View>(R.id.iv_profile_ing)
                binding.ivProfileIng.setImageResource(R.drawable.ic_profile)

                val DeleteService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
                DeleteService.deleteTrainerProflie().enqueue(object : Callback<BaseResponse>{
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("post", "delete onResponse 성공:" +response.body().toString())
                        }else{
                            Log.d("post","delete onResponse 실패:"+response.errorBody().toString())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        Log.d("post","delete onFailure :"+t.message.toString())
                    }

                })

                Toast.makeText(context, "프로필 삭제", Toast.LENGTH_SHORT).show()
                Log.d("post", "삭제 성공")
                dialog.dismiss()
            }
            //취소
            cancel.setOnClickListener {
                dialog.dismiss()
                Log.d("post", "취소 성공")
            }
        }
    }
    //이미지 뷰에 띄우기
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            //이미지를 받으면 이미지뷰에 적용함
            val imageUri = result.data?.data
            val bitmap : Bitmap? = null;
            imageUri.let {
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .into(binding.ivProfileIng)
            }


        }
    }


}



















