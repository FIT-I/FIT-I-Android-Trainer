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
        val modifyPic= findViewById<ImageButton>(R.id.btn_modify_pic) // ????????????
        val modifyCategory = findViewById<ImageButton>(R.id.btn_category_pick)

        fun onBind(data: GetTrainerInfoResponse.Result?) {
            binding.tvTrainerName.text = data?.name
            binding.tvManagePrice.text= data?.cost.toString()
            binding.tvAboutMe.text = data?.intro
            binding.tvAboutService.text =data?.service
            binding.tvTrainerStar.text = data?.grade.toString()
            binding.tvAverageValue.text = data?.grade.toString()
            binding.tvUniversityInfo.text = data?.school
            binding.tvReviewNum.text = data?.reviewDto?.size.toString() //?????? ?????????

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
                    binding.tvCategoryPick.text="????????????"
                }
                "pt" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_pt)
                    binding.tvCategoryPick.text="?????? PT"
                }
                "friend" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_friend)
                    binding.tvCategoryPick.text="????????????"
                }
                "rehab" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_medical)
                    binding.tvCategoryPick.text="????????????"
                }
                "food" -> {
                    //binding.ivCategoryPickIc.setImageResource(R.drawable.ic_eating)
                    binding.tvCategoryPick.text="????????????"
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
                    // ??????????????? ????????? ????????? ??????
                    response.body()?.let { onBind(it?.result) }
                    Log.d("post", "onResponse ??????: " + response.body().toString());

                    //Toast.makeText(this@ProfileActivity, "???????????? ?????? ??????!", Toast.LENGTH_SHORT).show()

                } else {
                    // ????????? ????????? ??????(???????????? 3xx, 4xx ???)
                    Log.d("post", "onResponse ??????")
                }
            }

            override fun onFailure(call: Call<GetTrainerInfoResponse>, t: Throwable) {
                // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                Log.d("post", "onFailure ??????: " + t.message.toString());
            }
        })

        // xml onclick ??????????????? -> ?????????????????? ?????????...?
//        modifyBackground.setOnClickListener{
//            dialogbackground(View)
//        }



        //?????? ??????
        modifyCost.setOnClickListener{
            val intent = Intent(this, ProfileModifyCostActivity::class.java)
            intent.putExtra("modify",ModifyTrainerInfoRequest(
                costHour,intro,name,serviceDetail))
            startActivity(intent)
            finish()
        }

        //?????? ???
        modifyMe.setOnClickListener{
            val intent = Intent(this, ProfileModifyMeActivity::class.java)
            intent.putExtra("modify",ModifyTrainerInfoRequest(
                costHour,intro,name,serviceDetail))
            startActivity(intent)
            finish()
        }

        //????????? ?????? ??????
        modifyService.setOnClickListener{
            val intent = Intent(this, ProfileModifyServiceActivity::class.java)
            intent.putExtra("modify",ModifyTrainerInfoRequest(
                costHour,intro,name,serviceDetail))
            startActivity(intent)
            finish()
        }

        //?????? ??? ?????????
        modifyPic.setOnClickListener{
            val intent = Intent(this, ProfileModifyPicActivity::class.java)
            startActivity(intent)
//            sendInfo()
            Log.d("post","?????? ?????? success")
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

        //???????????? ?????? ??????
        background.setOnClickListener {
            //????????? ????????????
            val intent = Intent(Intent.ACTION_PICK) // intent??? ?????? ??? ??????? -> ?????????
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.action = Intent.ACTION_GET_CONTENT // ??????????????? ?????? ????????????
            //????????? ?????? ?????? ??? ??????????????? ?????? ??????
            imageResult.launch(intent)
            Toast.makeText(this,"????????? ??????",Toast.LENGTH_SHORT).show()
            Log.d("post","????????? ??????")
        }

        //?????? ????????? ?????? ??????
        delete.setOnClickListener {
            binding.ivBackgroundPhoto.setImageResource(R.color.main)
            Toast.makeText(this, "????????? ??????", Toast.LENGTH_SHORT).show()
            Log.d("post", "?????? ??????")
            dialogbg.dismiss()
        }
        // ????????? ?????? ??????
        cancel.setOnClickListener {
            dialogbg.dismiss()
            Log.d("post", "?????? ??????")
        }

    }
    //

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if (result.resultCode == RESULT_OK){
            //???????????? ????????? ??????????????? ?????????
            val imageUri = result.data?.data
            imageUri?.let {
                Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .into(binding.ivBackgroundPhoto)
            }
            Log.d("post","?????? ?????? ?????? ??????")

        }
    }

    fun sendImage(image: MultipartBody.Part){
        val service = RetrofitImpl.getApiClient().create(TrainerService::class.java)
        val call = service.modifyTrainerBackground(image)

        call.enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    Log.d("post","background onResponse ??????:"+response.body().toString())
                }else{
                    Log.d("post","background onResponse ??????:"+response.errorBody().toString())
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
