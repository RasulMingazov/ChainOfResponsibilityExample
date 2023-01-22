package com.jeanbernad.chainofresponsibilityexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jeanbernad.chainofresponsibilityexample.signup.SignUpFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SignUpFragment()).commit()
    }
}