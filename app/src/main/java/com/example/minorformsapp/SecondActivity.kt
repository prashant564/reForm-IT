package com.example.minorformsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val extras: Bundle? = intent.extras
        val name = extras!!.getString("name")
        val description = extras.getString("description")
        val url = extras.getString("url")

        textView_form_name.text = name
        textView_form_url.text = url
        button_form_description.text = description
    }
}
