package com.example.biometricauth

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class BubbleActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble)
        val voiceCall = findViewById<View>(R.id.voice_call) as ImageButton
        voiceCall.setOnClickListener(this)
        val sendMessage = findViewById<View>(R.id.send) as ImageButton
        sendMessage.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.voice_call -> Toast.makeText(
                this@BubbleActivity,
                "Calling contact",
                Toast.LENGTH_SHORT
            ).show()
            R.id.send -> Toast.makeText(this@BubbleActivity, "Sending message", Toast.LENGTH_SHORT)
                .show()
        }
    }
}