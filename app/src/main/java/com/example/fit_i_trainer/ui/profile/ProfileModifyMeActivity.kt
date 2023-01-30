package com.example.fit_i_trainer.ui.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R

class ProfileModifyMeActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_modify_me)
        //객체생성
        val edit : EditText = findViewById(R.id.tv_about_me_context)
        val done = findViewById<Button>(R.id.btn_done)
        val countText = findViewById<TextView>(R.id.tv_char_num)

        done.isEnabled = false
        edit.addTextChangedListener(object:TextWatcher{
            var maxText =""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                maxText = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(edit.length()>500){
//                    Toast.makeText(this@ProfileModifyMeActivity,"최대 500자까지 입력 가능합니다.",
//                    Toast.LENGTH_SHORT).show()
//                    edit.setText(maxText)
//                    edit.setSelection(edit.length())
//                    countText.setText("${edit.length()}")
//                }
            }

            override fun afterTextChanged(p0: Editable?) {
                done.isEnabled = true
            }
        })

        //완료버튼
        done.setOnClickListener{
//            val intent1 = Intent(this,ProfileAboutMeActivity::class.java)
//            intent1.putExtra("contextMe",edit.text.toString())
//            startActivity(intent1)
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        //뒤로가기
        val goBack = findViewById<ImageButton>(R.id.ib_back_arrow)
        goBack.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}