package com.thai.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_reg.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_signup.setOnClickListener {
            var i = Intent(this, RegAct::class.java)
            startActivity(i)
        }

        login_btn.setOnClickListener {

            val mobile = login_mobile.text.toString()
            val password = login_password.text.toString()

            // check data required
            if (mobile.isEmpty()) {
                Toast.makeText(this, "Mobile field is required.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password field is required.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Regex
            val mobilePattern = "^\\d{10}$"
            val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"

            // Check Regex
            if (!mobile.matches(mobilePattern.toRegex())) {
                // Mobile invalid
                // have enough 10 numbers
                Toast.makeText(this, "Mobile is invalid. Do not enough ten numbers.", Toast.LENGTH_LONG).show()
            } else if (!password.matches(passwordPattern.toRegex())) {
                // Password invalid
                // only accept longer than 8 characters, includes lowercase and uppercase letters and has 1 special character
                Toast.makeText(this, "Password is invalid. Only accept longer than 8 characters, includes lowercase and uppercase letters and has 1 special character", Toast.LENGTH_LONG).show()
            } else {
                // All fields are valid, perform registration
                val url =
                    "http://10.20.22.114/SalesWeb/login.php?mobile=$mobile&password=$password"

                val rq: RequestQueue = Volley.newRequestQueue(this)
                val sr= StringRequest(Request.Method.GET,url,{ response ->
                    if(response.equals("0")){
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    } else {

                        UserInfo.mobile=login_mobile.text.toString()
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_LONG).show()
                        val i = Intent(this, HomeAct::class.java)
                        startActivity(i)
                    }
                }, { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                })
                rq.add(sr)
            }
        }
    }
}