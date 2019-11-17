package com.example.minorformsapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.android.synthetic.main.activity_view_pdf.*
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.lang.ref.WeakReference



class ViewPdfActivity : AppCompatActivity() {
    var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pdf)
        val extras: Bundle? = intent.extras
        url = extras!!.getString("url")
        RetrievePdfStream(this).execute(url)
    }
    companion object{
        class RetrievePdfStream internal constructor(context: ViewPdfActivity): AsyncTask<String,Void,InputStream>(){
            lateinit var inputStream: BufferedInputStream
            private val activityReference: WeakReference<ViewPdfActivity> = WeakReference(context)
            override fun doInBackground(vararg params: String?): InputStream {
                try {
                    val url = URL(params[0])
                    val urlConnection = url.openConnection() as HttpURLConnection
                    if(urlConnection.responseCode==200){
                        inputStream = BufferedInputStream(urlConnection.inputStream)
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                }
                return inputStream
            }
            override fun onPostExecute(result: InputStream?) {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                activity.pdfView.fromStream(inputStream).load()
            }
        }
    }

}
