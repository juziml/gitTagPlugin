package com.gt.myplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grock.deptestlib.LibTest
import com.grock.myplugin.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LibTest.test()
        setContentView(R.layout.activity_main)
    }
}
