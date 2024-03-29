package com.example.fit_i_trainer.ui.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.request.ModifyTrainerInfoRequest
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.ActivityProfileBinding
import com.example.fit_i_trainer.ui.profile.modify.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

public class ProfileActivity(context: Context): AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val applicationContext = context.applicationContext

    private val imageUri : Uri? = null

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
        val modifyPic= findViewById<ImageButton>(R.id.btn_modify_pic) // 사진수정
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

        // xml onclick 실행시켜줌 -> 오류발생하지 않는감...?
//        modifyBackground.setOnClickListener{
//            dialogbackground(View)
//        }



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
//            sendInfo()
            Log.d("post","사진 수정 success")
//            finish()
        }
    }
    fun dialogbackground(view: View) {

        val dialog = layoutInflater.inflate(R.layout.dialog_background,null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(dialog)
        }
        val dialogbg = build.create()
        dialogbg.show()
        Log.d("post", "dialogbackground success")

        val background = dialog.findViewById<Button>(R.id.btn_album_bg)
        val delete = dialog.findViewById<Button>(R.id.btn_delete_bg)
        val cancel = dialog.findViewById<Button>(R.id.btn_cancel_bg)

        //배경화면 사진 선택
        background.setOnClickListener {
            //갤러리 연동하기
            val intent = Intent(Intent.ACTION_PICK) // intent를 통해 뭘 열까? -> 갤러리
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.action = Intent.ACTION_GET_CONTENT // 갤러리에서 사진 가져오기
            //나중에 사진 선택 후 가져와서의 액션 넣기
            imageResult.launch(intent)
            Toast.makeText(this,"갤러리 이동",Toast.LENGTH_SHORT).show()
            Log.d("post","갤러리 성공")
        }

        //사진 삭제에 대한 코드
        delete.setOnClickListener {
            binding.ivBackgroundPhoto.setImageResource(R.color.main)
            Toast.makeText(this, "프로필 삭제", Toast.LENGTH_SHORT).show()
            Log.d("post", "삭제 성공")
            dialogbg.dismiss()
        }
        // 취소에 대한 실행
        cancel.setOnClickListener {
            dialogbg.dismiss()
            Log.d("post", "취소 성공")
        }

    }
    //

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if (result.resultCode == RESULT_OK){
            //이미지를 받아서 이미지뷰에 적용함
            val imageUri = result.data?.data
            imageUri?.let {
                Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .into(binding.ivBackgroundPhoto)
            }
            Log.d("post","사진 서버 연결 성공")

        }
    }

    fun sendImage(image: MultipartBody.Part){
        val service = RetrofitImpl.getApiClient().create(TrainerService::class.java)
        val call = service.modifyTrainerBackground(image)

        call.enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    Log.d("post","background onResponse 성공:"+response.body().toString())
                }else{
                    Log.d("post","background onResponse 실패:"+response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("post","background onFailure:"+t.message.toString())
            }

        })
    }
}


//calling api
//    @NonNull
//    private fun updateUserProfile() {
//        val file = ProfileActivity(requireContext()).getImageBody(imageUri!!)
//        val requestFile : RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
//        val builder : MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
//
//        builder//whatever data you will pass to the request body
//            .addFormDataPart("profile_photo",file.name,requestFile) // the profile photo
//        //make sure the name (ie profile_photo), matches your api, that is name of key
//
//        val requestBody: RequestBody = builder.build()
//
//        //pass the request body to make your retrofit call
//        updateUserProfile(requestBody)
//    }
