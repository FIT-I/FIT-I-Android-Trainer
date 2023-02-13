package com.example.fit_i_trainer.ui.main.mypage

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetMypageResponse
import com.example.fit_i_trainer.data.service.CommunalService
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.FragmentMypageModifyProfileBinding
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


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
                imageResult.launch(pintent)

                Toast.makeText(context, "갤러리 이동", Toast.LENGTH_SHORT).show()
                Log.d("post", "갤러리 성공")

            }
            //사진 삭제
            delete.setOnClickListener {
//                val profile = view.findViewById<View>(R.id.iv_profile_ing)

                Toast.makeText(context, "프로필 삭제", Toast.LENGTH_SHORT).show()
                Log.d("post", "삭제 성공")
                dialog.dismiss() }
            //취소
            cancel.setOnClickListener {
                dialog.dismiss()
                Log.d("post", "취소 성공") }
        }
    }
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK ) {
            //이미지를 받으면 이미지뷰에 적용함
            val context : Context? = null
            val imageUri = result.data?.data

//            val file = File(absolutelyPath(imageUri, context!!)) -> 이놈이 문제인듯
//            val requestFile = RequestBody.create("image/*".toMediaType(),file)
//            //val requestFile = file.toRequestBody("",toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("profileFile",file.name,requestFile)


            imageUri.let {
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .into(binding.ivProfileIng)
            }
//
//                //프로필 수정 api 연결하기
//                val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)//레트로핏
//                val call = trainerService.modifyTrainerProfile(body)//통신 api 패스 설정
//
//                call.enqueue(object : Callback<BaseResponse> {
//                    override fun onResponse(
//                        call: Call<BaseResponse>, response: Response<BaseResponse>
//                    ) {
//                        if (response.isSuccessful) {
//                            //정상적으로 통신된 경우
//                            Log.d("post", "프로필 onResponse 성공:" + response.body().toString())
//                            Toast.makeText(applicationContext, "통신성공", Toast.LENGTH_SHORT).show()
//
//                        } else {
//                            //통신 실패
//                            Log.d("post", "프로필 onResponse 실패:" + response.code().toString())
//                            Toast.makeText(applicationContext, "통신실패", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
//                        Log.d("post", "onFailure 에러 :" + t.message.toString())
//                    }
//                })


//            //이미지 파일에 담기// 전송하기 위해서 서버에
//            val file = File(absolutelyPath(imageUri, context!!))
//            //request형식으로 바꿔줌.
//            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//            //멀티파트를 이용할 수 있는 데이터로 만들어줌
//            val body = MultipartBody.Part.createFormData("profile", file.name, requestFile)
//
//            Log.d("post", file.name)
//            //api 연결하기
//            sendImage(body)

        }
    }
    //해당 함수는 나중에 인텐트로 가져온 이미지를 절대경로롤 변경해주는 함수이다.
    fun absolutelyPath(path: Uri?, context: Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = c?.getString(index!!)
        return result!!
    }
    //웹 서버로 이미지 전송

}



















