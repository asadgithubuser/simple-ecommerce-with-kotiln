package com.example.ecommerceadroid

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.email
import kotlinx.android.synthetic.main.activity_register.password

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        go_register_to_login_btn.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        register_btn.setOnClickListener {
            var fullname = fullName.text.toString()
            var email = email.text.toString()
            var password = password.text.toString()
            when{
                TextUtils.isEmpty(fullname) -> Toast.makeText(this, "Full Name should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(email) -> Toast.makeText(this, "Email should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(password) -> Toast.makeText(this, "Password should not be null", Toast.LENGTH_LONG).show()
                else ->{
                    var progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Sign Up")
                    progressDialog.setMessage("please wait, while may we take few seconds.")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()

                    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            saveRegisterDetails(fullname, email, password, progressDialog)
                        }else{
                            var errMessage = task.exception.toString()
                            Toast.makeText(this, "SignUp Error: $errMessage", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun saveRegisterDetails(fullname: String, email: String, password: String, progressDialog: ProgressDialog) {
        var currentUid = FirebaseAuth.getInstance().currentUser!!.uid
        var userRef = FirebaseDatabase.getInstance().reference.child("Users")
        var userMap = HashMap<String, Any>()

        userMap["uid"] = currentUid
        userMap["name"] = fullname
        userMap["email"] = email
        userMap["password"] = password
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/bhariwala-f7aa6.appspot.com/o/userImage%2Fprofile.png?alt=media&token=791f691b-3b40-4ba0-9bbd-199bed29f218"

        userRef.child(currentUid).setValue(userMap).addOnCompleteListener { task ->
            if(task.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(this, "Account has been created successfully", Toast.LENGTH_LONG).show()

                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()


            }else{
                progressDialog.dismiss()
                Toast.makeText(this, "Error: "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }
        }

    }

}