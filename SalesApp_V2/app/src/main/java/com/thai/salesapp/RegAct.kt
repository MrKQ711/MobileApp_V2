package com.thai.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_reg.*

class RegAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        reg_signup.setOnClickListener {

            // Get data
            val mobile = reg_mobile.text.toString()
            val password = reg_password.text.toString()
            val confirm = reg_confirm.text.toString()
            val name = reg_name.text.toString()
            val address = reg_address.text.toString()

            // check data required
            if (mobile.isEmpty()) {
                Toast.makeText(this, "Mobile field is required.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password field is required.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (confirm.isEmpty()) {
                Toast.makeText(this, "Confirm password field is required.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                Toast.makeText(this, "Name field is required.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (address.isEmpty()) {
                Toast.makeText(this, "Address field is required.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }



            // Regex
            val mobilePattern = "^\\d{10}$"
            val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
            val namePattern = """^[a-zA-Z\s]+$"""
            val addressPattern = """^[a-zA-Z0-9\s]+$"""


            // Check Regex
            if (!mobile.matches(mobilePattern.toRegex())) {
                // Mobile invalid
                // have enough 10 numbers
                Toast.makeText(this, "Mobile is invalid. Do not enough ten numbers.", Toast.LENGTH_LONG).show()
            } else if (!name.matches(namePattern.toRegex())) {
                // Name invalid
                // only accept lowercase and uppercase characters
                Toast.makeText(this, "Name is invalid. Only accept lowercase, uppercase characters and whitespace.", Toast.LENGTH_LONG).show()
            } else if (!address.matches(addressPattern.toRegex())) {
                // Address invalid
                // only accept lowercase and uppercase characters
                Toast.makeText(this, "Address is invalid. Only accept lowercase, uppercase characters and whitespace.", Toast.LENGTH_LONG).show()
            } else if (!password.matches(passwordPattern.toRegex())) {
                // Password invalid
                // only accept longer than 8 characters, includes lowercase and uppercase letters and has 1 special character
                Toast.makeText(this, "Password is invalid. Only accept longer than 8 characters, includes lowercase and uppercase letters and has 1 special character", Toast.LENGTH_LONG).show()
            } else if (!password.equals(confirm)) {
                // Password not match Confirm Password
                Toast.makeText(this, "Password does not match.", Toast.LENGTH_LONG).show()
            } else {
                // All fields are valid, perform registration
                val url =
                    "http://10.20.22.114/SalesWeb/add_user.php?mobile=$mobile&password=$password&name=$name&address=$address"

                val rq:RequestQueue=Volley.newRequestQueue(this)
                val sr=StringRequest(Request.Method.GET,url,{ response ->
                    if(response.equals("0")){
                        Toast.makeText(this, "Mobile already used", Toast.LENGTH_LONG).show()
                    } else {
                        UserInfo.mobile=mobile
                        Toast.makeText(this, "Registration successfully", Toast.LENGTH_LONG).show()
                        var i = Intent(this, MainActivity::class.java)
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