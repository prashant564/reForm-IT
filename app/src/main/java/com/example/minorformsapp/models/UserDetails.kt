package com.example.minorformsapp.models

class UserDetails(val uid: String, val name: String, val age: String, val father_name: String,
                  val aadhar_no: String, val pan_no: String, val address:String, val phone_no:String, val email: String,
                  val gender: String, val password: String){

    constructor(): this("","","","","","",
        "","","","","")

                  }
