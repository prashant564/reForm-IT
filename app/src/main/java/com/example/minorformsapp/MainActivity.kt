package com.example.minorformsapp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.minorformsapp.models.Forms
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.util.Log
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    val PERMISSION_CODE: Int = 1000
    val IMAGE_CAPTURE_CODE: Int = 1001
    var form_name = ""
    var image_uri: Uri? = null
    var clicked: Boolean = false
    var selectedPhotoUri: Uri? = null
    private lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        sendDataToFirebaseDatabase()
        CameraActivity()
        button_gallery.setOnClickListener {
            clicked = true
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,5)

        }
        button_extract_text.setOnClickListener {
            detectText()
        }
    }
//    private fun sendDataToFirebaseDatabase(){
//        for(i in form_list){
//            val uid = UUID.randomUUID().toString()
//            val ref = FirebaseDatabase.getInstance().getReference("/Forms/$uid")
////            Log.d("Main Activity","$i")
//            ref.setValue(i)
//        }
//    }
    private fun CameraActivity() {
        button_click_image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkCallingOrSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {
                    //persmission denied
                    val permission = arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    openCamera()
                }
            } else {
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(baseContext, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, image_uri)
            captured_imageView.setImageBitmap(bitmap)
//            if (! Python.isStarted()) {
//                Python.start(AndroidPlatform(this));
//            }

        }

        if (requestCode == 5 && resultCode == Activity.RESULT_OK && data != null) {
            //proceed and check the selected image
            selectedPhotoUri = data.data
//            if(bitmap.)
//            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
//            captured_imageView.setImageBitmap(bitmap)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun detectText(){
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        val result = detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                processText(firebaseVisionText)
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
    }

    private fun processText(text: FirebaseVisionText){
        val blocks: List<FirebaseVisionText.TextBlock> = text.textBlocks
        var txt = ""
        if(blocks.isEmpty()){
            Toast.makeText(baseContext,"No text",Toast.LENGTH_SHORT).show()
            return
        }
        for(block in blocks){
            txt += block.text + '\n'
        }
//        var index = txt.indexOf("\n")
//        form_name = txt.substring(0, index)
        textView_result.text = txt
        Log.d("Main Activity","$txt")
        val intent = Intent(this@MainActivity, AutoFormFillActivity::class.java)
        intent.putExtra("txt",txt)
        startActivity(intent)
//        searchFormInDatabase(form_name)

    }

    private fun searchFormInDatabase(name:String){
        val ref = FirebaseDatabase.getInstance().getReference("/Forms")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val forms = it.getValue(Forms::class.java)
                    if(forms!=null){
                        if(name == forms.form_name){
                            Log.d("Main Activity","${forms.form_description}")
                            val intent = Intent(this@MainActivity, SecondActivity::class.java)
                            intent.putExtra("name",forms.form_name)
                            intent.putExtra("description",forms.form_description)
                            intent.putExtra("url",forms.form_url)
                            intent.putExtra("url",forms.form_url)
                            startActivity(intent)
                        }else{
//                            Toast.makeText(baseContext,"FORM not found",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(baseContext,"No text found in forms, database empty",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

}
