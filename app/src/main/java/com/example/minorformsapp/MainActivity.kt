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
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText


class MainActivity : AppCompatActivity() {

    val PERMISSION_CODE: Int = 1000
    val IMAGE_CAPTURE_CODE: Int = 1001
    var image_uri: Uri? = null
    var clicked: Boolean = false
    var selectedPhotoUri: Uri? = null
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendDataToFirebaseDatabase()
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
    

    private fun sendDataToFirebaseDatabase(){

        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/Forms/RTO/${uid}")
        val forms = Forms(uid,
            "RTO_FORM 1-A*",
            "https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%201-A*.pdf?alt=media&" +
                "token=d40ad59b-c5c9-4354-a65e-e41120a175b1",
            "Form 1A (to be filled, signed & stamped by a registered medical practitioner with MBBS or above qualification) must b" +
                "e submitted for those seeking a licence to drive a public transport/commercial vehicle, and those above 40 years of age seeking renewal of/issue of fresh driving licence.")
        ref.setValue(forms)
    }
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
        var txt: String = ""
        if(blocks.isEmpty()){
            Toast.makeText(baseContext,"No text",Toast.LENGTH_SHORT).show()
            return
        }
        for(block in blocks){
            txt += block.text + "\n"
        }
        textView_result.text = txt
        Log.d("Main Activity","${txt}")
    }
}
