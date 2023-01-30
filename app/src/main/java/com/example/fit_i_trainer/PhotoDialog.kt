package com.example.fit_i_trainer

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException

class PhotoDialog : AppCompatActivity() {
    private var img1: ImageView? = null
    private var imgUri: Uri? = null
    private var photoURI: Uri? = null
    private var albumURI: Uri? = null
    private var mCurrentPhotoPath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_photo)

        fun makeDialog() {
            val alt_bld = AlertDialog.Builder(this)
            alt_bld.setPositiveButton("앨범에서 선택",
                DialogInterface.OnClickListener { dialog, id -> // 사진 촬영 클릭
                    Log.v("알림", "다이얼로그 > 사진 선택")
                    selectAlbum()

                }).setNeutralButton("프로필 사진 삭제",
                DialogInterface.OnClickListener { dialogInterface, id ->
                    Log.v("알림", "다이얼로그 > 사진 삭제")

                    takePhoto()
                }).setNegativeButton("취소   ",
                DialogInterface.OnClickListener { dialog, id ->
                    Log.v("알림", "다이얼로그 > 취소 선택")

                    // 취소 클릭. dialog 닫기.
                    dialog.cancel()
                })
            val alert = alt_bld.create()
            alert.show()
        }
    }

    //사진 찍기 클릭
    fun takePhoto() {
        Log.v("알림", "저장공간에 접근 불가능")

//        // 촬영 후 이미지 가져옴
//        val state = Environment.getExternalStorageState()
//
//        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (Environment.MEDIA_MOUNTED == state) {
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (intent.resolveActivity(packageManager) != null) {
//                var photoFile: File? = null
//                try {
//                    photoFile = createImageFile()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//                if (photoFile != null) {
//                    val providerURI: Uri = FileProvider.getUriForFile(this, packageName, photoFile)
//                    imgUri = providerURI
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI)
//                    startActivityForResult(intent, FROM_CAMERA)
//                }
//            }
//        } else {
//            Log.v("알림", "저장공간에 접근 불가능")
//            return
//        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val imgFileName = System.currentTimeMillis().toString() + ".jpg"
        var imageFile: File? = null
        val storageDir =
            File(Environment.getExternalStorageDirectory().toString() + "/Pictures", "ireh")
        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x $storageDir")
            storageDir.mkdirs()
        }
        Log.v("알림", "storageDir 존재함 $storageDir")
        imageFile = File(storageDir, imgFileName)
        mCurrentPhotoPath = imageFile.absolutePath
        return imageFile
    }

    //앨범 선택 클릭
    fun selectAlbum() {


        //앨범 열기
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        startActivityForResult(intent, FROM_ALBUM)
    }

    fun galleryAddPic() {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(mCurrentPhotoPath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        sendBroadcast(mediaScanIntent)
        Toast.makeText(this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            FROM_ALBUM -> {


                //앨범에서 가져오기
                if (data!!.data != null) {
                    try {
                        var albumFile: File? = null
                        albumFile = createImageFile()
                        photoURI = data.data
                        albumURI = Uri.fromFile(albumFile)
                        galleryAddPic()
                        img1!!.setImageURI(photoURI)

                        //cropImage();
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.v("알림", "앨범에서 가져오기 에러")
                    }
                }
            }
        }
    }
        companion object {
        private const val FROM_CAMERA = 0
        const val FROM_ALBUM = 1
    }
}


