package com.example.fit_i_trainer.ui.main.mypage.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.ActivityMypageNoticeMoreBinding

class MypageNoticeMoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageNoticeMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageNoticeMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<NoticeData>("notice")

        binding.tvNoticeTitle.text = data!!.title
        binding.tvNoticeContent.text = data.contents
        Log.d("post", data.toString())
    }
}