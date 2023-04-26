package com.gotadi.exampleapp

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.gotadi.AndroidGotadiSDK.GotadiAdapter
import com.gotadi.AndroidGotadiSDK.GotadiCallback
import com.gotadi.AndroidGotadiSDK.GotadiPartnerSetting
import com.gotadi.AndroidGotadiSDK.GotadiSearchBookActivity

class GTDExampleAppActivity : AppCompatActivity() {
    private var gotadiAdapter: GotadiAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_app)
        val button = findViewById<Button>(R.id.myappButton)
        //Call API and get gotadi Token
        gotadiAdapter = GotadiAdapter(this, setting = GotadiPartnerSetting("uat", "vib", "vi","token"))
        button.setOnClickListener {
            val intent = gotadiAdapter?.createGotadiIntent()
            intent?.let {
                startActivity(intent)
            }
            gotadiAdapter?.actionHandler?.paymentCallback = {result: String? ->
                println("GTDExampleAppActivity")
                println(result)
            }
            gotadiAdapter?.actionHandler?.bookkingResultCallback = object : GotadiCallback {
                override fun onCallPayment(gotadiActivity: Context, bookingNumber: String) {
                    println("bookkingResultCallback - onCallPayment")
                    gotadiActivity.startActivity(Intent(gotadiActivity, GTDPartnerPaymentActivity::class.java))
                    println(bookingNumber)
                }
            }
        }
    }

    private fun Context?.getParentActivity() : AppCompatActivity? = when {
        this is ContextWrapper -> if (this is AppCompatActivity) this else this.baseContext.getParentActivity()
        else -> null
    }

    override fun onDestroy() {
        super.onDestroy()
        println("GTDExampleAppActivity is destroy")
    }
}