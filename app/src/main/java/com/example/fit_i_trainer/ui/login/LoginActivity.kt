package com.example.fit_i_trainer.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import com.example.fit_i_trainer.App
import com.example.fit_i_trainer.MySharedPreferences
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl.getApiClientWithOutToken
import com.example.fit_i_trainer.data.model.request.LoginRequest
import com.example.fit_i_trainer.data.model.response.LoginResponse
import com.example.fit_i_trainer.data.service.AccountsService
import com.example.fit_i_trainer.ui.signup.SignupPermissionActivity
import com.example.fit_i_trainer.databinding.ActivityLoginBinding
import com.example.fit_i_trainer.ui.main.MainActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken

import com.kakao.sdk.common.model.AuthErrorCause.*
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    val PREFERENCE = "fit-i-trainer"

    private val TAG = this.javaClass.simpleName

//    private var email: String = ""
//    private var gender: String = ""
//    private var name: String = ""

    var email : String=""
    var pw: String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val etEmail : EditText = findViewById(R.id.et_emailL)
        val etPW : EditText = findViewById(R.id.et_pwL)

        val btnLogin : Button = findViewById(R.id.btn_login)

        // SharedPreferences 안에 값이 저장되어 있지 않을 때 -> Login
        if(MySharedPreferences.getUserId(this).isNullOrBlank()
            || MySharedPreferences.getUserPw(this).isNullOrBlank()) {
            //Login()
        }
        else { // SharedPreferences 안에 값이 저장되어 있을 때 -> MainActivity로 이동
            //
            Toast.makeText(this, "${MySharedPreferences.getUserId(this)}, 자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //비밀번호 찾기
        val findPW = findViewById<TextView>(R.id.tv_go_findPW)
        findPW.setOnClickListener {
            val intent = Intent(this, LoginFindPwActivity::class.java)
            startActivity(intent)  // 화면 전환을 시켜줌
            //finish()
        }

        //회원가입하기
        val signIn = findViewById<TextView>(R.id.tv_go_signIn)
        signIn.setOnClickListener {
            val intent = Intent(this, SignupPermissionActivity::class.java)
            startActivity(intent)  // 화면 전환을 시켜줌
            //finish()
        }


        //버튼 비활성화
        btnLogin.isEnabled = false

        //EditText 값 있을때만 버튼 활성화
        etEmail.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력값 담기
                email = etEmail.text.toString()

                //stroke 색상변경
                if(email.isNotEmpty())
                    etEmail.setBackgroundResource(R.drawable.edittext_border)
                else
                    etEmail.setBackgroundResource(R.drawable.edittext_border_not)

                //값 유무에 따른 활성화 여부
                btnLogin.isEnabled = isTrue() //있다면 true 없으면 false
            }
            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //EditText 값 있을때만 버튼 활성화
        etPW.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력값 담기
                pw = etPW.text.toString()

                //stroke 색상변경
                if(pw.isNotEmpty())
                    etPW.setBackgroundResource(R.drawable.edittext_border)
                else
                    etPW.setBackgroundResource(R.drawable.edittext_border_not)

                //값 유무에 따른 활성화 여부
                btnLogin.isEnabled = isTrue() //있다면 true 없으면 false
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        //SharedPref.openSharedPrep(this)

        //로그인 버튼 -> 메인
        //회원여부 판단하는 코드 작성 필요
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginSplashActivity::class.java)
            val loginRequest = LoginRequest(email,pw)

            val service= getApiClientWithOutToken().create(AccountsService::class.java)
            service.logIn(loginRequest).enqueue(object: Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if(response.isSuccessful){
                        when(response.body()?.code){ // 정상적으로 통신이 성공된 경우
                            1000 -> {
                                Log.d("post", "onResponse 성공: " + response.body().toString());
                                MySharedPreferences.setUserId(this@LoginActivity, email)
                                MySharedPreferences.setUserPw(this@LoginActivity, pw)

                                App.token_prefs.accessToken = response.body()!!.result.accessToken
                                App.token_prefs.refreshToken = response.body()!!.result.refreshToken

                                Toast.makeText(this@LoginActivity, email+", 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                                startActivity(intent)  // 화면 전환을 시켜줌
                                finish()
                            }

                            else -> {
                                Log.d("post", "onResponse 오류: " + response.body().toString());
                                Toast.makeText(this@LoginActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("post", "onResponse 실패")
                        Toast.makeText(this@LoginActivity, response.body()!!.message, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("post", "onFailure 에러: " + t.message.toString());
                }
            })
        }



        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginSplashActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        val kakao_login_button = findViewById<ImageButton>(R.id.ib_kakao_login_button) // 로그인 버튼

        kakao_login_button.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        val naverClientId = getString(R.string.naver_client_id)
        val naverClientSecret = getString(R.string.naver_client_secret)
        val naverClientName = getString(R.string.app_name)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret, naverClientName)

        setLayoutState(false)

        val naver_login_button = findViewById<ImageButton>(R.id.ib_naver_Login_Button) // 로그인 버튼
        naver_login_button.setOnClickListener {
            startNaverLogin()
        }



//        binding.tvNaverLogout.setOnClickListener {
//            startNaverLogout()
//        }
//        binding.tvNaverDeleteToken.setOnClickListener {
//            startNaverDeleteToken()
//        }
    }

    /**
     * 로그인
     * authenticate() 메서드를 이용한 로그인 */
    private fun startNaverLogin() {
        var naverToken: String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
//                binding.tvResult.text = "id: ${userId} \ntoken: ${naverToken}"
                setLayoutState(true)
                Toast.makeText(this@LoginActivity, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(
                    this@LoginActivity, "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        /** OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)

            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(
                    this@LoginActivity, "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    /**
     * 로그아웃
     * 애플리케이션에서 로그아웃할 때는 다음과 같이 NaverIdLoginSDK.logout() 메서드를 호출합니다. */
    private fun startNaverLogout() {
        NaverIdLoginSDK.logout()
        setLayoutState(false)
        Toast.makeText(this@LoginActivity, "네이버 아이디 로그아웃 성공!", Toast.LENGTH_SHORT).show()
    }

    /**
     * 연동해제
     * 네이버 아이디와 애플리케이션의 연동을 해제하는 기능은 다음과 같이 NidOAuthLogin().callDeleteTokenApi() 메서드로 구현합니다.
    연동을 해제하면 클라이언트에 저장된 토큰과 서버에 저장된 토큰이 모두 삭제됩니다.
     */
    private fun startNaverDeleteToken() {
        NidOAuthLogin().callDeleteTokenApi(this, object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
                setLayoutState(false)
                Toast.makeText(this@LoginActivity, "네이버 아이디 토큰삭제 성공!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }

            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
            }
        })
    }

    private fun setLayoutState(login: Boolean) {
        if (login) {
            val intent = Intent(this, LoginSplashActivity::class.java)  // 인텐트를 생성해줌,
            startActivity(intent)  // 화면 전환을 시켜줌
            finish()
//            binding.tvNaverLogin.visibility = View.GONE
//            binding.tvNaverLogout.visibility = View.VISIBLE
//            binding.tvNaverDeleteToken.visibility = View.VISIBLE
        } else {
//            binding.tvNaverLogin.visibility = View.VISIBLE
//            binding.tvNaverLogout.visibility = View.GONE
//            binding.tvNaverDeleteToken.visibility = View.GONE
//            binding.tvResult.text = ""
//        }
        }

//
//
//        val naver_login_button = findViewById<ImageButton>(R.id.naver_Login_Button) // 로그인 버튼
//
//        naver_login_button.setOnClickListener {
//            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
//                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
//            } else {
//                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
//            }
//        }
//
//
//        //네이버 로그인
//        binding.run {
//            naverLoginButton.setOnClickListener {
//                val oAuthLoginCallback = object : OAuthLoginCallback {
//                    override fun onSuccess() {
//                        // 네이버 로그인 API 호출 성공 시 유저 정보를 가져온다
//                        NidOAuthLogin().callProfileApi(object :
//                            NidProfileCallback<NidProfileResponse> {
//                            override fun onSuccess(result: NidProfileResponse) {
//                                name = result.profile?.name.toString()
//                                email = result.profile?.email.toString()
//                                gender = result.profile?.gender.toString()
//                                Log.e(TAG, "네이버 로그인한 유저 정보 - 이름 : $name")
//                                Log.e(TAG, "네이버 로그인한 유저 정보 - 이메일 : $email")
//                                Log.e(TAG, "네이버 로그인한 유저 정보 - 성별 : $gender")
//                            }
//
//                            override fun onError(errorCode: Int, message: String) {
//                                //
//                            }
//
//                            override fun onFailure(httpStatus: Int, message: String) {
//                                //
//                            }
//                        })
//                    }
//
//                    override fun onError(errorCode: Int, message: String) {
//                        val naverAccessToken = NaverIdLoginSDK.getAccessToken()
//                        Log.e(TAG, "naverAccessToken : $naverAccessToken")
//                    }
//
//                    override fun onFailure(httpStatus: Int, message: String) {
//                        //
//                    }
//                }
//
//                NaverIdLoginSDK.initialize(
//                    this@LoginActivity,
//                    getString(R.string.naver_client_id),
//                    getString(R.string.naver_client_secret),
//                    "FIT-I"
//                )
//                NaverIdLoginSDK.authenticate(this@LoginActivity, oAuthLoginCallback)
//            }
//        }
    }
    private fun isTrue(): Boolean {
        return email.isNotEmpty()&&pw.isNotEmpty()
    }
}