package com.example.fit_i_trainer.ui.main.mypage

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetMypageResponse
import com.example.fit_i_trainer.data.service.CommunalService
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.FragmentMypageModifyProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


private const val MAX_WIDTH = 1280
private const val MAX_HEIGHT = 960

class MypageModifyProfileFragment : Fragment() {
    private var _binding: FragmentMypageModifyProfileBinding? = null
    private val binding: FragmentMypageModifyProfileBinding
        get() = requireNotNull(_binding) { "FragmentMypageModifyProfileBinding" }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageModifyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnphoto = view.findViewById<View>(R.id.btn_click_photo)
        val ibpre = view.findViewById<View>(R.id.ib_pre1)

        fun onBind(data: GetMypageResponse.Result) {
            binding.tvName.text = data.userName
            binding.tvEmail.text = data.email
            binding.tvAddress.text = data.location
        }

        val commmunalService = RetrofitImpl.getApiClient().create(CommunalService::class.java)
        commmunalService.getMypage().enqueue(object :
            Callback<GetMypageResponse> {
            override fun onResponse(
                call: Call<GetMypageResponse>,
                response: Response<GetMypageResponse>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    onBind(response.body()!!.result)
                    Log.d("post", "onResponse 성공: " + response.body().toString());
                    //Toast.makeText(this@ProfileActivity, "비밀번호 찾기 성공!", Toast.LENGTH_SHORT).show()
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("post", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<GetMypageResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("post", "onFailure 에러: " + t.message.toString());
            }
        })


        //이전
        ibpre.setOnClickListener {
            val mypageFragment = MypageFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

            //이전 화면으로 이동
            transaction.replace(R.id.fl_container, mypageFragment)
            transaction.commit()

        }

        //사진 수정 버튼
        btnphoto.setOnClickListener {

            val dialogphoto = layoutInflater.inflate(R.layout.dialog_photo, null)
            val build = AlertDialog.Builder(view.context).apply {
                setView(dialogphoto)
            }
            val dialog = build.create()
            dialog.show()
            Log.d("post ", "dialog success")

            val photo = dialogphoto.findViewById<Button>(R.id.btn_photo)
            val delete = dialogphoto.findViewById<Button>(R.id.btn_photo_delete)
            val cancel = dialogphoto.findViewById<Button>(R.id.btn_cancel)

            //프로필 사진 선택
            photo.setOnClickListener {
                //갤러리 연동 기능 추가하기
                val pintent = Intent(Intent.ACTION_PICK) //intent를 통해서 뭘 열까? -> 갤러리
                pintent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                pintent.action = Intent.ACTION_GET_CONTENT // 갤러리에서 사진 가져오기
                //갤러리에서 선택 후 이미지뷰에 띄우는 기능 구현
                imageResult.launch(pintent)

                Toast.makeText(context, "갤러리 이동", Toast.LENGTH_SHORT).show()
                Log.d("post", "갤러리 성공")

            }
            //사진 삭제
            delete.setOnClickListener {
//                val profile = view.findViewById<View>(R.id.iv_profile_ing)
                binding.ivProfileIng.setImageResource(R.drawable.ic_profile)

                val DeleteService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
                DeleteService.deleteTrainerProflie().enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("post", "delete onResponse 성공:" + response.body().toString())
                        } else {
                            Log.d("post", "delete onResponse 실패:" + response.errorBody().toString())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        Log.d("post", "delete onFailure :" + t.message.toString())
                    }

                })

                Toast.makeText(context, "프로필 삭제", Toast.LENGTH_SHORT).show()
                Log.d("post", "삭제 성공")
                dialog.dismiss()
            }
            //취소
            cancel.setOnClickListener {
                dialog.dismiss()
                Log.d("post", "취소 성공")
            }
        }
    }

    //이미지 뷰에 띄우기
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            //이미지를 받으면 이미지뷰에 적용함
            val imageUri = result.data?.data
            val bitmap: Bitmap? = null;
            imageUri.let {
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .into(binding.ivProfileIng)
            }

            val path = optimizeBitmap(requireContext(),imageUri!!)
            val filePart = addImageFileToRequestBody(path!!,"file")

            sendImage(filePart)
        }
    }

    fun sendImage(image: MultipartBody.Part){
        val service = RetrofitImpl.getApiClient().create(TrainerService::class.java)
        service.modifyTrainerProfile(image).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    Log.d("post","onResponse 성공:" + response.body().toString())

                }else{
                    Log.d("post","onResponse 실패:"+response.code().toString())
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("post","onFailure :"+t.message.toString())
            }

        })
    }
    //이미지 'FormData'에 담기
  fun addImageFileToRequestBody(path: String, name: String): MultipartBody.Part{
        val imageFile = File(path)

        val fileRequestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name,imageFile.name,fileRequestBody)
    }

    fun optimizeBitmap(context: Context, uri: Uri): String? {
        try {
            val storage = context.cacheDir // 임시 파일 경로
            val fileName = String.format("%s.%s", UUID.randomUUID(), "jpg")// 임시 파일 이름

            val tempFile = File(storage, fileName)
            tempFile.createNewFile() // 임시 파일 생성

            //저장된 이름을 가진 파일에 쓸 파일 출력 스트림을 만듬
            val fos = FileOutputStream(tempFile)

            //비트맵을 압축하는 메소드
            decodeBitmapFromUri(uri,context)?.apply {
                compress(Bitmap.CompressFormat.JPEG, 100, fos)
                recycle()
            } ?: throw NullPointerException();

            fos.flush()
            fos.close()
            return tempFile.absolutePath // 임시파일 저장경로 리턴
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "FileUtil = ${e.message}")
        }
        return null
    }
    //여러장을 저장할거라서 UUID.randomUUID를 통해 이름을 생성
    //내부 저장소(앱에서만 접근이 가능한 저장소) . 외부저장소(어떤 앱에서든 접근 가능한 저장 공간)
    //파일관련에서는 try-catch문은 필수??
    //Exception 발생시 e.printstacktrace()로 찍으면 모든 부분을 보여주는데, 외부에 노출되면 큰일이ㅁ
    //fileoutstream -> 데이터를 파일에 바이트 스트림으로 저장하기 위해 사용
    //flush() 더 이상 출력 될 데이터가 없을때
    //colse() 더이상 사용하지 않을때


    //최적화 bitmap 반환
    private fun decodeBitmapFromUri(uri: Uri, context: Context): Bitmap? {
       //인자값으로 넘어온 입력 스트림을 나중에 사용하기 위해 저장하는
        val input = BufferedInputStream(context.contentResolver.openInputStream(uri))

        input.mark(input.available()) // 입력 스트림의 특정 위치를 기억

        var bitmap: Bitmap?

        BitmapFactory.Options().run {
            inJustDecodeBounds = true
            bitmap= BitmapFactory.decodeStream(input,null,null)

            input.reset() // 입력 스트림의 마지막 mark된 위치로 재설정

            inSampleSize = calculateInSampleSize(this)
            inJustDecodeBounds = false

            bitmap= BitmapFactory.decodeStream(input,null,this)?.apply {
                //회전된 이미지 되돌리기
                rotateImageIfRequired(context,bitmap!!,uri)
            }
        }
        input.close()
        return bitmap
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > MAX_HEIGHT || width > MAX_WIDTH) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= MAX_HEIGHT && halfWidth / inSampleSize >= MAX_WIDTH) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
    private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap? {
        val input = context.contentResolver.openInputStream(uri) ?: return null

        val exif = if (Build.VERSION.SDK_INT > 23) {
            ExifInterface(input)
        } else {
            ExifInterface(uri.path!!)
        }

        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

}























