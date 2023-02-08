package com.example.fit_i_trainer.ui.main.mypage

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.GetMypageResponse
import com.example.fit_i_trainer.data.service.CommunalService
import com.example.fit_i_trainer.databinding.FragmentMypageModifyProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.System.load


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
                pintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // 여러개 가져오기
                pintent.action = Intent.ACTION_GET_CONTENT // 갤러리에서 사진 가져오기
                imageResult.launch(pintent)

                Toast.makeText(context, "갤러리 이동", Toast.LENGTH_SHORT).show()
                Log.d("post", "갤러리 성공")
            }


            delete.setOnClickListener {
                Toast.makeText(context, "프로필 삭제", Toast.LENGTH_SHORT).show()
                Log.d("post", "삭제 성공")
            }


            cancel.setOnClickListener {
                dialog.dismiss()
                Log.d("post", "취소 성공")
            }


        }
    }

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
//            val data: Intent? = null
//            if (data?.clipData == null) {
//                val count = data?.clipData!!.itemCount
//                if (count > 1) {
//                    Toast.makeText(context, "사진은 최대 1장 선택할 수 있습니다.", Toast.LENGTH_LONG).show()
//                    return@registerForActivityResult
//                }
//                for (i in 1 until 2) {
//                    val imageUri = data.clipData!!.getItemAt(i).uri
//                    imageUri?.let {
//                        Glide.with(this)
//                            .load(imageUri)
//                            .fitCenter()
//                            .into(binding.ivProfileIng)
//                    }
//선택한 후 버튼 띄우기를 어떻게 해야할까????!!!1
            //이미지를 받으면 이미지뷰에 적용함
            val imageUri = result.data?.data
            imageUri?.let {
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .into(binding.ivProfileIng)
//                }
            }
        }
    }
}
















