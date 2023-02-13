package com.example.fit_i_trainer.ui.profile.modify

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.ActivityProfileModifyPicBinding
import com.example.fit_i_trainer.ui.profile.ProfileActivity

class ProfileModifyPicActivity: AppCompatActivity() {

    lateinit var binding : ActivityProfileModifyPicBinding
    lateinit var picAdapter: PicAdapter
    var imageList: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileModifyPicBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //갤러리 연결 버튼
        var getImage_btn = findViewById<ImageButton>(R.id.btn_album)
        //백 버튼
        //다이알로그 띄우는 버튼
        var cancel = findViewById<ImageButton>(R.id.btn_delete_pic)



        //어뎁터 초기화
        picAdapter = PicAdapter(imageList,this)

        //리사이클러뷰 설정
        //3개의 열을 갖는 그리드 레이아웃 매니저를 설정함. 기본값은 세로방향
        val gridLayoutManager = GridLayoutManager(applicationContext,3)
        binding.rcProfilePic.layoutManager = gridLayoutManager
        binding.rcProfilePic.adapter = picAdapter


        //사진삭제 -> 다이알로그 띄움 // 나중에 딜리트 서버 들어가야함
        cancel.setOnClickListener {
            val dialog = layoutInflater.inflate(R.layout.dialog_delete,null)
            val build = AlertDialog.Builder(this).apply {
                setView(dialog)
            }
            val dialogdd = build.create()
            dialogdd.show()
            Log.d("post", "dialog success")

            val cancel = dialogdd.findViewById<Button>(R.id.btn_cancel_dd)
            val delete = dialogdd.findViewById<Button>(R.id.btn_delete_dd)

            //취소
            cancel.setOnClickListener{
                dialogdd.dismiss()
            }
            delete.setOnClickListener{
                dialogdd.dismiss()

            }


        }





        getImage_btn.setOnClickListener{
            var intent = Intent(Intent.ACTION_PICK) // intent 통해서 뭘 할까??-> 갤러리
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.action = Intent.ACTION_GET_CONTENT //  갤러리에서 사진 가져오기
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true) //다중 선택 가능함
            imageResult.launch(intent)
            Toast.makeText(this,"갤러리 이동",Toast.LENGTH_SHORT).show()
            Log.d("post","갤러리 이동 성공")

        }

//        val mAdapter = PicAdapter(this,userList
//       recyclerview.adapter = madapter
        //girdLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

    }

    //결과 가져오기
    private val imageResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            if (it.data!!.clipData !=null){//다중 이미지

                //선택한 이미지 갯수
                val count = it.data!!.clipData!!.itemCount
                for (index in 0 until count){
                    //이미지 담기
                    val imageUri = it.data!!.clipData!!.getItemAt(index).uri
                    //이미지 추가
                    imageList.add(imageUri)
                }
            }else{//싱글 이미지
                val imageUri = it.data!!.data
                imageList.add(imageUri!!)
            }

            //적용
            picAdapter.notifyDataSetChanged()

        }
    }


}
