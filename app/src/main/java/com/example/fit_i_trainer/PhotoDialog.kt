package com.example.fit_i_trainer

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.fit_i_trainer.databinding.DialogPhotoBinding

class PhotoDialog(context: Context, private val okCallbacks: (String) -> Unit): Dialog(context) {
    //뷰를 띄우기 위해 클래스를 context 인자로 받기
    private lateinit var binding: DialogPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        //뒤로 가기나 빈 화면 터치로 사라지지 않도록
        setCancelable(false)
        //background 투명하게
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //버튼 클릭에 대한 콜백 처리
        //취소하면 다시 백, 탈퇴하면 탙퇴 처리 후 첫화면 돌아가기
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnPhoto.setOnClickListener {
            //탈퇴에 대한 코드 필요
            dismiss()
        }
        btnPhotoDelete.setOnClickListener {
            //사진 삭제에 대한 코드 필요
            dismiss()
        }

    }
}