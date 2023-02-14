package com.example.fit_i_trainer.ui.main.matching

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.data.model.response.GettrainerResponse
import com.example.fit_i_trainer.databinding.ItemmatchingBinding

class MatchingAdapter(private val dataList: List<GettrainerResponse.Result>) :
    RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    // 2 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    // 3 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    // 4 setItemClickListener로 설정한 함수 설정

    private lateinit var itemClickListener: OnItemClickListener

//작동방식은 itemClickListener(어댑터) <-> OnItemClickListener <-> setItemClickListener(액티비티)


    inner class ViewHolder(private val binding: ItemmatchingBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(position: Int) {
            binding.matchingIm.text = dataList[position].name
            binding.matchingDate.text = dataList[position].orderDate
//            binding.matchingMent.text = dataList[position].pickUpType
            if(dataList[position].pickUpType == "TRAINER_GO"){
                binding.matchingMent.text = "트레이너님이 와주세요"
            }else if (dataList[position].pickUpType == "CUSTOMER_GO"){
                binding.matchingMent.text = "제가 직접 갈게요"
            }

            if (dataList[position].profile == "customerProfile"|| dataList[position].profile != null){
                Glide.with(itemView)
                    .load("${dataList[position].profile}")
                    .into(binding.matchingProfile)
                Log.d("post",dataList[position].profile)
            }

            if (dataList[position].pickUpType == "TRAINER_GO") {
                binding.matchingMent.text = "트레이너님이 와주세요"
            } else if (dataList[position].pickUpType == "CUSTOMER_GO") {
                binding.matchingMent.text = "제가 직접 갈게요"
            }

            when (dataList[position].profile) {//캐릭터 아이콘
                "customerProfile1" -> binding.matchingProfile.setImageResource(R.drawable.img_char1)
                "customerProfile2" -> binding.matchingProfile.setImageResource(R.drawable.img_char2)
                "customerProfile3" -> binding.matchingProfile.setImageResource(R.drawable.img_char3)
                "customerProfile4" -> binding.matchingProfile.setImageResource(R.drawable.img_char4)
                "customerProfile5" -> binding.matchingProfile.setImageResource(R.drawable.img_char5)
                "customerProfile6" -> binding.matchingProfile.setImageResource(R.drawable.img_char6)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)

        //아이템 클릭시 매칭명세표로 이동
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, MatchingIngActivity::class.java)
            intent.putExtra("matchingId", dataList[position].matchingId)

            startActivity(holder.itemView.context, intent,null)
        }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            ItemmatchingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int =
        dataList.size

}




