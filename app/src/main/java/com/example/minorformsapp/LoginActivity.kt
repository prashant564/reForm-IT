package com.example.minorformsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(){

    private val TAG = "LoginActivity"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textView_create_account.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        auth = FirebaseAuth.getInstance()

        button_login.setOnClickListener {
            checkLogin()

        }
    }

    private fun checkLogin(){

        if (editText_login_email.text.toString().isEmpty()) {
            editText_login_email.error = "Please enter email"
            editText_login_email.requestFocus()
            return
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(editText_email.text.toString()).matches()) {
//            editText_email.error = "Please enter valid email"
//            editText_email.requestFocus()
//            return
//        }

        if (editText_login_password.text.toString().isEmpty()) {
            editText_login_password.error = "Please enter password"
            editText_login_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(editText_login_email.text.toString(), editText_login_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()


                }
            }
    }

    fun updateUI(currentUser: FirebaseUser?){
        if(currentUser!=null){


            Toast.makeText(baseContext,"Login successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

}