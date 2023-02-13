package com.example.fit_i_trainer.ui.main.chat

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fit_i_trainer.data.model.response.GetMatchlistResponse
import com.example.fit_i_trainer.databinding.ActivityChatIngBinding


class ChatIngActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatIngBinding

    fun onBind(data: GetMatchlistResponse.Result){
        binding.tvCustomerName.text = data.name

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityChatIngBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}
