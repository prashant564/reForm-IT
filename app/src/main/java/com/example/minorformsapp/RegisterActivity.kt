package com.example.minorformsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minorformsapp.models.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register.*

class RegisterActivity : AppCompatActivity(){

    private val TAG = "RegisterActivity"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth = FirebaseAuth.getInstance()
        supportActionBar?.title = "REGISTER"

        button_signup.setOnClickListener {

            checkUser()
        }

        textView_already_have_an_account.setOnClickListener {

            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
        }

    }


    private fun checkUser() {

        if (editText_username.toString().isEmpty()) {
            editText_username.error = "Please enter username"
            editText_username.requestFocus()
            return
        }
        if (editText_age.toString().isEmpty()) {
            editText_age.error = "Please enter age"
            editText_age.requestFocus()
            return
        }
        if (editText_father_name.toString().isEmpty()) {
            editText_father_name.error = "Please enter father's name"
            editText_father_name.requestFocus()
            return
        }
        if (editText_aadhar_no.toString().isEmpty()) {
            editText_aadhar_no.error = "Please enter aadhar no."
            editText_aadhar_no.requestFocus()
            return
        }
        if (editText_pan_no.toString().isEmpty()) {
            editText_pan_no.error = "Please enter pan no."
            editText_pan_no.requestFocus()
            return
        }
        if (editText_address.toString().isEmpty()) {
            editText_address.error = "Please enter address"
            editText_address.requestFocus()
            return
        }
        if (editText_phone_no.toString().isEmpty()) {
            editText_phone_no.error = "Please enter phone number"
            editText_phone_no.requestFocus()
            return
        }
        if (editText_gender.toString().isEmpty()) {
            editText_gender.error = "Please enter gender"
            editText_gender.requestFocus()
            return
        }
        if (editText_email.toString().isEmpty()) {
            editText_email.error = "Please enter email"
            editText_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(editText_email.text.toString()).matches()) {
            editText_email.error = "Please enter valid email"
            editText_email.requestFocus()
            return
        }
        if (editText_password.toString().isEmpty()) {
            editText_password.error = "Please enter password"
            editText_password.requestFocus()
            return
        }


        auth.createUserWithEmailAndPassword(editText_email.text.toString(), editText_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    saveUserToFirebaseDatabase()
                    Log.d(TAG, "createUserWithEmail:success")
                    startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))

                }
                else{
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun saveUserToFirebaseDatabase(){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/userDetails/$uid")
        val userDetails = UserDetails( uid, editText_username.text.toString(),editText_age.text.toString(), editText_father_name.text.toString(),
            editText_aadhar_no.text.toString(), editText_pan_no.text.toString(), editText_address.text.toString(), editText_phone_no.text.toString(),
            editText_email.text.toString(), editText_gender.text.toString(), editText_password.text.toString())
        ref.setValue(userDetails)
    }
}