package com.example.ecommerceadroid

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.ecommerceadroid.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        go_login_to_register_btn.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        login_btn.setOnClickListener {
            loginUserMethod()
        }
    }

    private fun loginUserMethod(){
        var email = email.text.toString()
        var password = password.text.toString()
        when{
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is Required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is Required", Toast.LENGTH_LONG).show()
            else -> {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Sign In")
                progressDialog.setMessage("please wait for sometimes, sign in processing")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                var mAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        progressDialog.dismiss()
                        Toast.makeText(this, "You are logged in successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        //redirectHomeActivity(email)
                    }else{
                        progressDialog.dismiss()
                        var eMessage = task.exception
                        Toast.makeText(this, "SignIn Error: $eMessage", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }



}