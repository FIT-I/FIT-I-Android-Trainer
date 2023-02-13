package com.example.fit_i_trainer.ui.main.chat

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.data.model.response.GetChatResponse
import com.example.fit_i_trainer.databinding.ItemChatBinding

class ChatAdapter(private val dataList: List<GetChatResponse.Result>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemChatBinding):
            RecyclerView.ViewHolder(binding.root){

                fun onBind(position: Int){
                    if (dataList[position].customerProfile == "customerProfile"|| dataList[position].customerProfile != null){
                        Glide.with(itemView)
                            .load("${dataList[position].customerProfile}")
                            .into(binding.ivChatProfile)
                        Log.d("post",dataList[position].customerProfile)
                    }
                    binding.tvChatIm.text = dataList[position].trainerName
//                    binding.tvChatMent.text = dataList[position].pickUp
//                    binding.tvChatLocation.text = dataList[position].customerLocation
                    if(dataList[position].pickUp == "TRAINER_GO"){
                        binding.tvChatMent.text = "트레이너님이 와주세요"
                        binding.tvChatLocation.text = dataList[position].customerLocation
                    }else if (dataList[position].pickUp == "CUSTOMER_GO"){
                        binding.tvChatMent.text = "제가 직접 갈게요"
                        binding.tvChatLocation.text = dataList[position].trainerLocation
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,ChatIngActivity::class.java)
            intent.putExtra(
                "chat",ChatRoom(
                    dataList[position].openChatLink,
                    dataList[position].trainerId,
                    dataList[position].trainerName,
                    dataList[position].trainerGrade,
                    dataList[position].trainerSchool,
                    dataList[position].customerId,
                    dataList[position].customerName,
                    dataList[position].pickUp,
                    dataList[position].customerLocation,
                    dataList[position].createdAt,
                    dataList[position].matchingId,
                    dataList[position].trainerProfile,
                    dataList[position].trainerLocation,
                    dataList[position].customerProfile
                ))
            startActivity(holder.itemView.context, intent,null)

        }
    }

}