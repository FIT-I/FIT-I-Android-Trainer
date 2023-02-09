package com.example.fit_i_trainer.ui.profile.modify

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R

class PicAdapter(): RecyclerView.Adapter<PicAdapter.ViewHolder>() {
    lateinit var imageList: ArrayList<Uri>
    lateinit var context: Context

    constructor(imageList: ArrayList<Uri>, context: Context):this(){
        this.imageList = imageList
        this.context = context
    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.item_profile_pic,parent,false)
        return ViewHolder(view)

    }
    //데이터 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        Glide.with(context)
            .load(imageList[position])//이미지 위치
            .into(holder.galleryView)//보여줄 위치
    }
    //아이템 갯수
    override fun getItemCount(): Int{
        return imageList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val galleryView : ImageView = view.findViewById(R.id.iv_picture_item)
    }
}