package com.example.minorformsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_auto_form_fill.*
import java.util.*

class AutoFormFillActivity : AppCompatActivity() {

    lateinit var mTextToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_form_fill)
        val extras: Bundle? = intent.extras
        val txt = extras!!.getString("txt")
//        Log.d("AutoFormFillActivity","$txt")
        mTextToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) {
                mTextToSpeech.language = Locale.ENGLISH
            }
        })
        button_aff_speak.setOnClickListener {
            var current_line: String
            for (i in txt.lines()) {
                textView_aff.text = i
                speakLinebyLine(i)
            }
        }

    }

    private fun speakLinebyLine(i:String){
        Log.d("AutoFormFillActivity", "$i")
        button_aff_speak.setOnClickListener {
            if (i == "") {
                Toast.makeText(baseContext, "Enter Text", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, i, Toast.LENGTH_SHORT).show()
                mTextToSpeech.speak(i, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
    }
}
