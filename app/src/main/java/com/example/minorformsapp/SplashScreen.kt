package com.example.minorformsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.lang.Exception

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        imageView_logo.setBackgroundResource(R.drawable.app_logo)

        val background = object : Thread() {

            override fun run() = try {
                sleep(2000)
                verifyUserIsLoggedIn()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
        background.start()


    }

    private fun verifyUserIsLoggedIn(){

        val uid = FirebaseAuth.getInstance().uid
        if(uid==null){
            val intent = Intent(baseContext, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
//            overridePendingTransition(R.anim.from_left_out,R.anim.from_right_in)

        }
        else{
            val intent = Intent(baseContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
//            overridePendingTransition(R.anim.from_left_out,R.anim.from_right_in)
        }

    }
}

