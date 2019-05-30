package com.example.anothertimdatxe.widget

import android.util.Log
import android.text.TextUtils;
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class TokenPhoneSms(var mActivity: AppCompatActivity, var mListener: TokenPhoneSms.SendTokenSmsListener) {

    private lateinit var mAuth: FirebaseAuth
    private var verificationInProgress = false
    private lateinit var mVerificationId: String
    private lateinit var mResendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var mCodeView: TextView? = null

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }

    fun initDataPhoneSms() {
        //Init before getting Instance Firebase
        FirebaseApp.initializeApp(mActivity)
        //getInstance
        mAuth = FirebaseAuth.getInstance()
        //Create mCallBacks object to get update abour state of the verification process
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //this method is called when verification is done without user interactions
            override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
                Log.d(TAG, "onVerificationCompleted: ${p0?.smsCode}")
            }

            //this method is called on failure instances such as when sending SMS fails or number format is not valid
            override fun onVerificationFailed(p0: FirebaseException?) {
                Log.w(TAG, "onVerificationFailed", p0)
                mListener.onSendSmsFailed()
            }

            //this method is called when SMS is successfully sent to the number
            override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(p0, p1)
                Log.d(TAG, "onCodeSent: $p0")
                mVerificationId = p0!!
                mResendToken = p1!!
                mListener.onSendSmsSuccess()
            }
        }

    }

    fun startPhoneNumberVerification(phone: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,              //Phone for verification
                1,                  // Timeout duration
                TimeUnit.MINUTES,   // Unit of timeout
                mActivity,          // Activity (for callback binding)
                mCallbacks          // mCallBacks object
        )
    }

    fun resendPhoneNumberVerification(phone: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                1,
                TimeUnit.MINUTES,
                mActivity,
                mCallbacks,
                mResendToken
        )
    }

    fun confirmTokenPhoneSms(tvCodeUser: TextView) {
        mCodeView = tvCodeUser
        var code: String = mCodeView?.text.toString()
        verifyPhoneNumberWithCode(mVerificationId, code)
    }

    private fun verifyPhoneNumberWithCode(mVerificationId: String, code: String) {
        var credential = PhoneAuthProvider.getCredential(mVerificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithPhoneAuthCredential:Success")
                mListener.onVerifySmsSuccess()
            } else {
                Log.d(TAG, "signInWithPhoneAuthCredential:Failed")
                mListener.onVerifySmsFailed()
            }
        }
    }

    interface SendTokenSmsListener {
        fun onVerifySmsSuccess()
        fun onVerifySmsFailed()
        fun onSendSmsSuccess()
        fun onSendSmsFailed()
    }
}