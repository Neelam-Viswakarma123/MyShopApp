package com.nv.myshop.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.nv.myshop.R

@Suppress("DEPRECATION")
class LoginActivity : BaseActivity(){
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {
            logInRegisteredUser()
        }
        val tv_register = findViewById<TextView>(R.id.tv_register)
        tv_register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        val tv_forgot_password = findViewById<TextView>(R.id.tv_forgot_password)
        tv_forgot_password.setOnClickListener{

        }
    }
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }
    private fun logInRegisteredUser(){
        if (validateLoginDetails()) {
            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }
            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                 if (task.isSuccessful){
                     showErrorSnackBar("You are logged in Successfully",false)
                 }else{
                     showErrorSnackBar(task.exception!!.message.toString(),true)
                 }
                }
        }
    }
}