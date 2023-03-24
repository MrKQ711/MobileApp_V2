package com.thai.salesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.*
import kotlinx.android.synthetic.main.activity_total.*
import java.math.BigDecimal

class TotalAct : AppCompatActivity() {

    var config:PayPalConfiguration?=null
    var amount:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        config = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(UserInfo.client_id)

        var url =
            "http://10.20.22.114/SalesWeb/get_total.php?bill_no=" + intent.getStringExtra("bno")

        var rq: RequestQueue = Volley.newRequestQueue(this)
        var sr = StringRequest(Request.Method.GET, url, { response ->

            total_tv.text = response
            amount = response.toDouble()

        }, { _ ->
            Toast.makeText(this, "Failed!", Toast.LENGTH_LONG).show()
        })

        rq.add(sr)

        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(intent)

        paypal_btn.setOnClickListener {
            makePayment()
        }

    }

    private fun makePayment() {
        val payment = PayPalPayment(BigDecimal.valueOf(amount), "USD", "Payment for Sales App",
            PayPalPayment.PAYMENT_INTENT_SALE)
        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
        resultLauncher.launch(intent)
    }

    @Suppress("DEPRECATION")
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val confirmation = result.data?.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION) as? PaymentConfirmation
                if (confirmation != null) {
                    Toast.makeText(this, "Payment successful!", Toast.LENGTH_LONG).show()
                }
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(this, "Payment cancelled.", Toast.LENGTH_LONG).show()
            }
            PaymentActivity.RESULT_EXTRAS_INVALID -> {
                Toast.makeText(this, "Invalid payment.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }

}