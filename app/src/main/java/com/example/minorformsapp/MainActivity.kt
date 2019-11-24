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
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    val PERMISSION_CODE: Int = 1000
    val IMAGE_CAPTURE_CODE: Int = 1001
    var form_name = ""
    var image_uri: Uri? = null
    var clicked: Boolean = false
    var selectedPhotoUri: Uri? = null
    val IMAGE_PICK_CODE = 5
    private lateinit var bitmap: Bitmap
//    var form_list: ArrayList<Forms> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val form:List<Forms> = listOf(
//            Forms("FORM I","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_FORM%201.pdf?alt=media&token=4d6ea9d0-d4a3-43ea-9e25-197f4e7733d4","This is form of declaration provided by the court in which the publisher declares all the important information about the document to be published ,mainly newspaper"),
//            Forms("F O R M - 28","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_FORM%2028.pdf?alt=media&token=c4fd6a07-69c1-4cd3-a406-ccaf2c6b776d","This is form for CIVIL APPELLATE JURISDICTION. Appellate jurisdiction includes the power to reverse or modify the the lower court's decision. Appellate jurisdiction exists for both civil law and criminal law. In an appellate case, the party that appealed the lower court's decision is called the appellate, and the other party is the appellee."),
//            Forms("FORM IV","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_FORM%20IV.pdf?alt=media&token=7b0ec91c-db41-4ef7-b4fc-544982a7b6f8","Statement about ownership and other particulars about newspaper to be published in the first issue every year after the last day of February"),
//            Forms("FORM 1-A*","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%201-A*.pdf?alt=media&token=d40ad59b-c5c9-4354-a65e-e41120a175b1","Form 1A (to be filled, signed & stamped by a registered medical practitioner with MBBS or above qualification) must be submitted for those seeking a licence to drive a public transport / commercial vehicle, and those above 40 years of age seeking renewal of / issue of fresh driving licence."),
//            Forms("FORM 1","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%201.pdf?alt=media&token=91004a9e-26a2-4972-900a-556ad50dc3f1","Form 1 (self-declaration of applicant's physical fitness to drive) is sufficient for those under 40 years of age, applying for a non-transport (NT) driving licence."),
//            Forms("FORM 2","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%202.pdf?alt=media&token=49fa086b-dafa-49f2-a4db-08c533f3fa95","FORM OF APPLICATION FOR THE GRANT OR RENEWAL OF LEANER'S LICENCE"),
//            Forms("FORM 20","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2020.pdf?alt=media&token=e2173a8d-899e-4e04-a8e6-c28195a10df0","APPLICATION FOR REGISTRATION OF A MOTOR VEHICLE"),
//            Forms("FORM 27","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2027.pdf?alt=media&token=51081557-8a4c-42c5-84c2-01fbf14098bf","Application for assignment of new registration mark on removal of a motor vehicle to another State"),
//            Forms("FORM 28","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2028.pdf?alt=media&token=00aaa745-03bf-4420-ae41-c0d0090854bb","An application for Issue of No Objection Certificate shall be made in Form 28 (in triplicate) to the Registration Authority by which the Vehicle was previously Registered / Transferred. by affixing Rs.3/- Non-Judicial Court Fee Stamp."),
//            Forms("FORM 29","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2029.pdf?alt=media&token=848b7d7b-528b-4243-af58-bdca9d3e56b8","NOTICE OF TRANSFER OF OWNERSHIP OF A MOTOR VEHICLE"),
//            Forms("FORM 3","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%203.pdf?alt=media&token=d71a01e8-9af2-41a3-9436-5af0ebdc9fc9","This form is to be filled if a person is applying for Learner's License"),
//            Forms("FORM 30","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2030.pdf?alt=media&token=d660736e-f1db-4992-9d7d-5547088042fa","APPLICATION FOR INTIMATION AND TRANSFER OF OWNERSHIP OF A MOTOR VEHICLE"),
//            Forms("FORM 34","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2034.pdf?alt=media&token=b2f6d651-327a-43e6-b5ca-3e72c601b3dc","APPLICATION FOR MAKING AN ENTRY OF AN AGREEMENT OF HIREPURCHASE/LEASE/HYPOTHECATION SUBSEQUENT TO REGISTRATION"),
//            Forms("FORM 35","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2035.pdf?alt=media&token=72996500-2ee2-417c-b529-c720e7e0c080","NOTICE OF TERMINATION OF AN AGREEMENT OF HIREPURCHASE/LEASE/HYPOTHECATION"),
//            Forms("FORM B","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FPension%20Form_FORM%20B.pdf?alt=media&token=ae8f43b2-cf01-4625-a782-6c716748e772","FORM OF APPLICATION FOR FAMILY PENSION "),
//            Forms("FORM A","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FPension%20Form_FORM%20A.pdf?alt=media&token=c9354255-57a3-4f6c-813c-b604420a9ce3","FORM OF APPLICATION FOR DISABILITY PENSION "),
//            Forms("FORM 5","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FPension%20Form_FORM%205.pdf?alt=media&token=971b339d-8d96-4e28-b266-e9037ef8127a","Form informing Particulars to be obtained by the Head of Office from the retiring Government servant six months before the date of his retirement"),
//            Forms("FORM 14","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FPension%20Form_FORM%2014.pdf?alt=media&token=14a53331-363f-45c4-b7ef-f77d354d5fe6","Form of application for family pension on death of a Government servant or pensioner or on death or ineligibility of a family pensioner"),
//            Forms("NO. 16 ","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2016.pdf?alt=media&token=64e9f5f9-5f86-4b3c-b176-e068f9a6e436","This form is to filled to inform about the acceptance of sum paid into Court "),
//            Forms("NO. 2 ","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%202.pdf?alt=media&token=a7cc9d2a-3daa-4c9b-8a3d-7b9d07844e32","Form of Summons for an Order in Chambers "),
//            Forms("NO. 20 ","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2020.pdf?alt=media&token=845b3208-c761-43cf-b4ba-25441b26d9d2","This is a form of Affidavit of Service of Summons"),
//            Forms("NO. 22","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2022.pdf?alt=media&token=28a861f0-250f-46fd-97fb-b16215507a43","This form is to imply Certificate of Taxation"),
//            Forms("NO. 25","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2025.pdf?alt=media&token=d94c0a8a-7411-44fb-af6b-7a338c75a56e","Form of Lodgment Schedule"),
//            Forms("NO. 26","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2026.pdf?alt=media&token=66ea049c-aff5-4b46-8bc4-7dc0ea145d4c","This form is about Deposit Repayment Order and Voucher"),
//            Forms("NO. 5","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%205.pdf?alt=media&token=f2863c20-021c-4d45-8f8e-df920b41fc0f","Form of Oath by Translator"),
//            Forms("NO. 6","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%206.pdf?alt=media&token=71474615-8a13-4c9c-b245-db352e55e5d2","This is an application for production of Record "),
//            Forms("NO. 8","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%208.pdf?alt=media&token=b60b96ce-363c-44b4-aef2-32cf78a7b474","This is an application about Memorandum of Appearance in Person"),
//            Forms("NO. 9","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%209.pdf?alt=media&token=93a6b308-482b-4290-ba35-cb13ebe84cdf","Application of Memorandum of Appearance through Advocate-on-Record ")
//        )
//        form_list.addAll(form)
//        sendDataToFirebaseDatabase()
        CameraActivity()
        button_gallery.setOnClickListener {
            clicked = true
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,IMAGE_PICK_CODE)

        }
        button_extract_text.setOnClickListener {
            detectText()
        }
    }

//    private fun sendDataToFirebaseDatabase(){
//        for(i in form_list){
//            val uid = UUID.randomUUID().toString()
//            val ref = FirebaseDatabase.getInstance().getReference("/Forms/$uid")
//            Log.d("Main Activity","$i")
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

        this@MainActivity.runOnUiThread(object: Runnable{
            override fun run() {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "New Picture")
                values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")
                image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
                startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
            }
        })

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
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,90,stream)
            val image = stream.toByteArray()
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }
            val python = Python.getInstance()
            val pythonFile = python.getModule("checkform")
            val helloWorldString = pythonFile.callAttr("checkform",image)
            textView_result.text = helloWorldString.toString()
        }

        this@MainActivity.runOnUiThread(object: Runnable{
            override fun run() {
                if (requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, image_uri)
                    captured_imageView.setImageBitmap(bitmap)
                }

                if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
                    //proceed and check the selected image
                    selectedPhotoUri = data.data
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
                    captured_imageView.setImageURI(selectedPhotoUri)
                }
            }
        })
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
        this@MainActivity.runOnUiThread(object: Runnable{
            override fun run() {
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
        })
    }

    private fun processText(text: FirebaseVisionText){
        this@MainActivity.runOnUiThread(object: Runnable{
            override fun run() {
                val blocks: List<FirebaseVisionText.TextBlock> = text.textBlocks
                var txt = ""
                if(blocks.isEmpty()){
                    Toast.makeText(baseContext,"No text",Toast.LENGTH_SHORT).show()
                    return
                }
                for(block in blocks){
                    txt += block.text + "\n"
                }
                var index = txt.indexOf("\n")
                form_name = txt.substring(0, index)
                textView_result.text = form_name
                Log.d("Main Activity","$form_name")
                searchFormInDatabase(form_name, txt)
            }
        })
    }

    private fun searchFormInDatabase(name:String, text:String){
        val ref = FirebaseDatabase.getInstance().getReference("/Forms")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                this@MainActivity.runOnUiThread(object: Runnable{
                    override fun run() {
                        p0.children.forEach {
                            val forms = it.getValue(Forms::class.java)
                            if(forms!=null){
                                if(name == forms.form_name){
                                    Log.d("Main Activity","${forms.form_description}")
                                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                                    intent.putExtra("text",text)
                                    intent.putExtra("name",forms.form_name)
                                    intent.putExtra("description",forms.form_description)
                                    intent.putExtra("url",forms.form_url)
                                    startActivity(intent)
                                }else{
                                    Toast.makeText(baseContext,"FORM not found",Toast.LENGTH_SHORT).show()
                                }
                            }
                            else{
                                Toast.makeText(baseContext,"No text found in forms, database empty",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        })
    }

}
