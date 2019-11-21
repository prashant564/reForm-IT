package com.example.minorformsapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minorformsapp.models.Forms

class sample: AppCompatActivity(){
    var form_list: ArrayList<Forms> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val form:List<Forms> = listOf(
            Forms("FORM I","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_FORM%201.pdf?alt=media&token=4d6ea9d0-d4a3-43ea-9e25-197f4e7733d4","This is form of declaration provided by the court in which the publisher declares all the important information about the document to be published ,mainly newspaper"),
            Forms("F O R M - 28","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_FORM%2028.pdf?alt=media&token=c4fd6a07-69c1-4cd3-a406-ccaf2c6b776d","This is form for CIVIL APPELLATE JURISDICTION. Appellate jurisdiction includes the power to reverse or modify the the lower court's decision. Appellate jurisdiction exists for both civil law and criminal law. In an appellate case, the party that appealed the lower court's decision is called the appellate, and the other party is the appellee."),
            Forms("FORM IV","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_FORM%20IV.pdf?alt=media&token=7b0ec91c-db41-4ef7-b4fc-544982a7b6f8","Statement about ownership and other particulars about newspaper to be published in the first issue every year after the last day of February"),
            Forms("FORM 1-A*","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%201-A*.pdf?alt=media&token=d40ad59b-c5c9-4354-a65e-e41120a175b1","Form 1A (to be filled, signed & stamped by a registered medical practitioner with MBBS or above qualification) must be submitted for those seeking a licence to drive a public transport / commercial vehicle, and those above 40 years of age seeking renewal of / issue of fresh driving licence."),
            Forms("FORM 1","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%201.pdf?alt=media&token=91004a9e-26a2-4972-900a-556ad50dc3f1","Form 1 (self-declaration of applicant's physical fitness to drive) is sufficient for those under 40 years of age, applying for a non-transport (NT) driving licence."),
            Forms("FORM 2","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%202.pdf?alt=media&token=49fa086b-dafa-49f2-a4db-08c533f3fa95","FORM OF APPLICATION FOR THE GRANT OR RENEWAL OF LEANER'S LICENCE"),
            Forms("FORM 20","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2020.pdf?alt=media&token=e2173a8d-899e-4e04-a8e6-c28195a10df0","APPLICATION FOR REGISTRATION OF A MOTOR VEHICLE"),
            Forms("FORM 27","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2027.pdf?alt=media&token=51081557-8a4c-42c5-84c2-01fbf14098bf","Application for assignment of new registration mark on removal of a motor vehicle to another State"),
            Forms("FORM 28","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2028.pdf?alt=media&token=00aaa745-03bf-4420-ae41-c0d0090854bb","An application for Issue of No Objection Certificate shall be made in Form 28 (in triplicate) to the Registration Authority by which the Vehicle was previously Registered / Transferred. by affixing Rs.3/- Non-Judicial Court Fee Stamp."),
            Forms("FORM 29","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2029.pdf?alt=media&token=848b7d7b-528b-4243-af58-bdca9d3e56b8","NOTICE OF TRANSFER OF OWNERSHIP OF A MOTOR VEHICLE"),
            Forms("FORM 3","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%203.pdf?alt=media&token=d71a01e8-9af2-41a3-9436-5af0ebdc9fc9","This form is to be filled if a person is applying for Learner's License"),
            Forms("FORM 30","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2030.pdf?alt=media&token=d660736e-f1db-4992-9d7d-5547088042fa","APPLICATION FOR INTIMATION AND TRANSFER OF OWNERSHIP OF A MOTOR VEHICLE"),
            Forms("FORM 34","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2034.pdf?alt=media&token=b2f6d651-327a-43e6-b5ca-3e72c601b3dc","APPLICATION FOR MAKING AN ENTRY OF AN AGREEMENT OF HIREPURCHASE/LEASE/HYPOTHECATION SUBSEQUENT TO REGISTRATION"),
            Forms("FORM 35","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FRTO_FORM%2035.pdf?alt=media&token=72996500-2ee2-417c-b529-c720e7e0c080","NOTICE OF TERMINATION OF AN AGREEMENT OF HIREPURCHASE/LEASE/HYPOTHECATION"),
            Forms("FORM B","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FPension%20Form_FORM%20B.pdf?alt=media&token=ae8f43b2-cf01-4625-a782-6c716748e772","FORM OF APPLICATION FOR FAMILY PENSION "),
            Forms("FORM A","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FPension%20Form_FORM%20A.pdf?alt=media&token=c9354255-57a3-4f6c-813c-b604420a9ce3","FORM OF APPLICATION FOR DISABILITY PENSION "),
            Forms("FORM 5","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FPension%20Form_FORM%205.pdf?alt=media&token=971b339d-8d96-4e28-b266-e9037ef8127a","Form informing Particulars to be obtained by the Head of Office from the retiring Government servant six months before the date of his retirement"),
            Forms("FORM 14","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FPension%20Form_FORM%2014.pdf?alt=media&token=14a53331-363f-45c4-b7ef-f77d354d5fe6","Form of application for family pension on death of a Government servant or pensioner or on death or ineligibility of a family pensioner"),
            Forms("NO. 16 ","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2016.pdf?alt=media&token=64e9f5f9-5f86-4b3c-b176-e068f9a6e436","This form is to filled to inform about the acceptance of sum paid into Court "),
            Forms("NO. 2 ","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%202.pdf?alt=media&token=a7cc9d2a-3daa-4c9b-8a3d-7b9d07844e32","Form of Summons for an Order in Chambers "),
            Forms("NO. 20 ","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2020.pdf?alt=media&token=845b3208-c761-43cf-b4ba-25441b26d9d2","This is a form of Affidavit of Service of Summons"),
            Forms("NO. 22","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2022.pdf?alt=media&token=28a861f0-250f-46fd-97fb-b16215507a43","This form is to imply Certificate of Taxation"),
            Forms("NO. 25","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2025.pdf?alt=media&token=d94c0a8a-7411-44fb-af6b-7a338c75a56e","Form of Lodgment Schedule"),
            Forms("NO. 26","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%2026.pdf?alt=media&token=66ea049c-aff5-4b46-8bc4-7dc0ea145d4c","This form is about Deposit Repayment Order and Voucher"),
            Forms("NO. 5","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%205.pdf?alt=media&token=f2863c20-021c-4d45-8f8e-df920b41fc0f","Form of Oath by Translator"),
            Forms("NO. 6","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%206.pdf?alt=media&token=71474615-8a13-4c9c-b245-db352e55e5d2","This is an application for production of Record "),
            Forms("NO. 8","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%208.pdf?alt=media&token=b60b96ce-363c-44b4-aef2-32cf78a7b474","This is an application about Memorandum of Appearance in Person"),
            Forms("NO. 9","https://firebasestorage.googleapis.com/v0/b/minorformsapp.appspot.com/o/Forms%2FCOURT_NO.%209.pdf?alt=media&token=93a6b308-482b-4290-ba35-cb13ebe84cdf","Application of Memorandum of Appearance through Advocate-on-Record ")
        )
        form_list.addAll(form)
    }
}