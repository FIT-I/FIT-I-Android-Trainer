package com.example.fit_i_trainer

import android.app.Dialog
import android.content.ComponentCallbacks
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.example.fit_i_trainer.databinding.DialogAccountBinding
import com.example.fit_i_trainer.databinding.DialogPhotoBinding

class PhotoDialog(context: Context, private val okCallbacks: (String) -> Unit) : Dialog(context) {
    private lateinit var binding: DialogPhotoBinding
    val i: Boolean = true
    val ivprofile = findViewById<View>(R.id.iv_profile_ing)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        //뒤로 가거나 빈 화면 터치로 사라지지 않도록
        setCancelable(false)
        //배경 투명하게
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        //버튼 클릭에 대한 콜백 처리
        //취소하면 백, 사진 삭제하면(프로필 사진 변경 되도록), 사진 선택하면(갤러리로 이동)
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnPhotoDelete.setOnClickListener{
            changeImage()
        }

        btnPhoto.setOnClickListener {

        }
    }
    private fun changeImage(){
            ivprofile.setVisibility(View.VISIBLE)

        }

    }


