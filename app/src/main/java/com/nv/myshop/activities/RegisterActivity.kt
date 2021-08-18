package com.nv.myshop.activities
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nv.myshop.R

@Suppress("DEPRECATION")
class RegisterActivity : BaseActivity() {
    private lateinit var et_first_name: EditText
    private lateinit var et_last_name:EditText
    private lateinit var et_email:EditText
    private lateinit var et_password:EditText
    private lateinit var et_confirm_password:EditText
    private lateinit var cb_terms_and_condition:CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        et_first_name = findViewById(R.id.et_first_name)
        et_last_name= findViewById(R.id.et_last_name)
        et_email=findViewById(R.id.et_email)
        et_password=findViewById(R.id.et_password)
        et_confirm_password=findViewById(R.id.et_confirm_password)
        cb_terms_and_condition=findViewById(R.id.cb_terms_and_condition)

        val btn_register = findViewById(R.id.btn_register) as Button
        btn_register.setOnClickListener {
            registerUser()
        }
        val tv_login = findViewById(R.id.tv_login) as TextView
        tv_login.setOnClickListener {
            // Launch the register screen when the user clicks on the text.
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateRegisterDetails(): Boolean {
        return when {

            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
                //showErrorSnackBar("Thanks for Registering!", false)
                true
            }
        }
    }
    private fun registerUser() {
        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {


            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_email.text.toString().trim { it <= ' ' }
            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            showErrorSnackBar(
                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
                                false
                            )
                            FirebaseAuth.getInstance().signOut()
                            finish()
                        } else {
                            // If the registering is not successful then show error message.
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }
}