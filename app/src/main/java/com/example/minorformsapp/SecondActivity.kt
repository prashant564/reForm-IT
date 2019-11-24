package com.example.minorformsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*

class SecondActivity : AppCompatActivity() {

    lateinit var mTextToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val extras: Bundle? = intent.extras
        val name = extras!!.getString("name")
        val description = extras.getString("description")
        val url = extras.getString("url")
        val text = extras.getString("text")

        textView_form_name.text = name
        textView_form_description.text = description
        mTextToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if(it!=TextToSpeech.ERROR){
                mTextToSpeech.language = Locale.ENGLISH
            }
        })
        button_speak.setOnClickListener {
            val toSpeech = textView_form_description.text.toString()
            if(toSpeech==""){
                Toast.makeText(baseContext,"Enter Text",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(baseContext,toSpeech,Toast.LENGTH_SHORT).show()
                mTextToSpeech.speak(toSpeech,TextToSpeech.QUEUE_FLUSH,null)
            }
        }
        button_stop.setOnClickListener {
            if(mTextToSpeech.isSpeaking) {
                mTextToSpeech.stop()
            }else{
                Toast.makeText(baseContext,"Not Speaking",Toast.LENGTH_SHORT).show()
            }
        }
        button_open_pdf.setOnClickListener {
            val intent = Intent(this, FillFormActivity::class.java)
            intent.putExtra("text",text)
            startActivity(intent)
        }
    }

    override fun onPause() {
        if(mTextToSpeech.isSpeaking) {
            mTextToSpeech.stop()
        }
        super.onPause()
    }
}


