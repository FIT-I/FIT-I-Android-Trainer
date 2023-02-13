package com.example.fit_i_trainer.ui.main.matching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.ActivityOpenChatBinding
import java.util.regex.Pattern

class OpenChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpenChatBinding

    var opnelink: String = ""

    val openchatPattern =
        "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?\$"

    private lateinit var confirmlink: TextView // 오픈채팅 링크 정규성


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_chat)

        binding = ActivityOpenChatBinding.inflate(layoutInflater)

        val etopenChat: EditText = findViewById(R.id.ev_openchat)

        val btnend = findViewById<Button>(R.id.btn_end)

        btnend.isEnabled = false

        etopenChat.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            //값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                opnelink = etopenChat.text.toString()

                //색상 변경
                if (opnelink.isNotEmpty())
                    etopenChat.setBackgroundResource(R.drawable.edittext_border)
                else
                    etopenChat.setBackgroundResource(R.drawable.edittext_border_not)

                //값 유무에 따라 활성화 여부
                btnend.isEnabled = isTrue()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        btnend.setOnClickListener {
            val intent = Intent(this, MatchingIngActivity::class.java)
            intent.putExtra("openChatLink", opnelink)
            startActivity(intent) //화면전환
            finish()

        }


    }

    private fun isTrue(): Boolean {
        linkcheck()
        return opnelink.isNotEmpty()
    }

    //오픈채팅 정규성 검사
    private fun linkcheck(): Boolean {
        val pattern = Pattern.compile(openchatPattern)
        val matcher = pattern.matcher(opnelink)

        if (!matcher.find()) {
            Toast.makeText(this, "오픈채팅 형식이 일치하지 않습니다.", Toast.LENGTH_LONG).show()
            return false
        } else
            return true
    }

}