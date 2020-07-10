package com.example.biometricauth

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var biometricPrompt: BiometricPrompt? = null

    var createBubble: Button? = null

    var builder: Notification.Builder? = null
    var notificationManager: NotificationManager? = null
    var channel: NotificationChannel? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val biometricManager = BiometricManager.from(this)
        val isCapable = when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> true
            else -> false
        }

        var executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "$errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)

                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()

                    startActivity(Intent(this@MainActivity, HomePageActivity::class.java))

                    finish()

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }


            })


        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for app")
            .setSubtitle("Log in using your biometric credential")
            .setDeviceCredentialAllowed(true)
            .setConfirmationRequired(true)
            .build()


        //   .setNegativeButtonText("Use account password")
        // .setConfirmationRequired(true)

        btnBioAuth.setOnClickListener {

            if (isCapable) {
                biometricPrompt!!.authenticate(promptInfo)
            } else {
                Toast.makeText(
                    applicationContext, "Authentication Not Supported",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }

        }

        btnFaceAuth.setOnClickListener {


            val packageManager: PackageManager = this.packageManager
            val hasFace = packageManager.hasSystemFeature(PackageManager.FEATURE_FACE)
            Toast.makeText(
                applicationContext, hasFace.toString(),
                Toast.LENGTH_SHORT
            )
                .show()


        }


    }

}
