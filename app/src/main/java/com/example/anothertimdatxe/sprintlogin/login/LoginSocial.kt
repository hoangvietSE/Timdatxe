package com.example.anothertimdatxe.sprintlogin.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.anothertimdatxe.entity.FacebookResponse
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson

class LoginSocial(var mActivity: Activity, var mListener: LoginSocialListener) {
    private lateinit var mFacebookCallbackManager: CallbackManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        const val RC_SIGN_IN = 9001
        const val TAG = "GoogleSignIn"
    }

    init {
        mFacebookCallbackManager = CallbackManager.Factory.create()
    }

    fun loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(mActivity, listOf("public_profile", "email"))
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                var facebookId: String? = result!!.accessToken.userId
//                Log.d("myLog", facebookId.toString())
                var parameter: Bundle = Bundle()
                parameter.putString("fields", "id,name,email")
                GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "me",
                        parameter,
                        HttpMethod.GET,
                        GraphRequest.Callback { response ->
                            var json = response.jsonObject.toString()
                            var facebookResponse: FacebookResponse = Gson().fromJson(json, FacebookResponse::class.java)
                            var full_name: String = facebookResponse.name
                            var email: String = facebookResponse.email
                            mListener.onVerifyLoginSocialSuccess(facebookId!!, full_name
                                    ?: "", email ?: "", "facebook")
                        }
                ).executeAsync()
            }

            override fun onCancel() {
                Log.d("myLog", "cancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d("myLog", error!!.message)
            }

        })
    }

    fun loginGoogle() {
        configRequestGoogle(mActivity)
        signInGoogle()
    }

    private fun signInGoogle() {
        var signIntent = mGoogleSignInClient.signInIntent
        mActivity.startActivityForResult(signIntent, RC_SIGN_IN)
    }

    private fun configRequestGoogle(mActivity: Activity) {
        var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(mActivity, gso)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResultTask(task)
        } else {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInResultTask(completedTask: Task<GoogleSignInAccount>) {
        try {
            var account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            mListener.onVerifyLoginSocialSuccess(account?.id ?: "", account?.displayName
                    ?: "", account?.email ?: "", "google")
        } catch (e: ApiException) {
            Log.d(TAG, "Error Code: ${e.statusCode}")
        }
    }

    interface LoginSocialListener {
        fun onVerifyLoginSocialSuccess(socialId: String, full_name: String, email: String, socialType: String)
    }
}