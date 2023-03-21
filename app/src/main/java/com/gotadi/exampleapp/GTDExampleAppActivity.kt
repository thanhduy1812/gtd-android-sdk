package com.gotadi.exampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.gotadi.AndroidGotadiSDK.GotadiSearchBookActivity

class GTDExampleAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_app)
        val button = findViewById<Button>(R.id.myappButton)
        button.setOnClickListener {
            val flutterHomeActivity = GotadiSearchBookActivity()
            val intent = Intent(this, GotadiSearchBookActivity::class.java)
            startActivity(intent)
        }
    }
}