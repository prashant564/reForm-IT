package com.example.minorformsapp

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.Document
import com.itextpdf.text.Element.ALIGN_CENTER
import com.itextpdf.text.Font
import com.itextpdf.text.Font.BOLDITALIC
import com.itextpdf.text.FontFactory
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_fill_form_pdf.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import com.itextpdf.text.Font.BOLD as BOLD


class FillFormActivity : AppCompatActivity() {
    var txt:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form_pdf)
        val extras: Bundle? = intent.extras
        txt = extras!!.getString("text")
        editText_form.setText(txt)
        button_save_form.setOnClickListener {
            savePdf()
        }
    }

    private fun savePdf(){
        this@FillFormActivity.runOnUiThread(object:Runnable{
            override fun run() {
                val mdoc = Document()
                val mfileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
                val mfilePath = Environment.getExternalStorageDirectory().toString() + "/" + mfileName + ".pdf"
                try {
                    PdfWriter.getInstance(mdoc, FileOutputStream(mfilePath))
                    mdoc.open()
                    val mText = editText_form.text.toString()
                    val paragraph = Paragraph(mText, FontFactory.getFont(FontFactory.TIMES_BOLD))
                    paragraph.alignment = ALIGN_CENTER
                    paragraph.firstLineIndent
                    mdoc.add(paragraph)
                    mdoc.close()
                    Toast.makeText(baseContext, "$mfileName.pdf\nis saved to\n$mfilePath",Toast.LENGTH_SHORT).show()
                }catch (e: Exception){
                    Toast.makeText(baseContext,e.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}
