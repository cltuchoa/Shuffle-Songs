package com.uchoa.shuffle.songs.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.uchoa.shuffle.songs.R
import com.uchoa.shuffle.songs.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        startAnimations()
    }

    private fun startAnimations() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        anim.duration = 2000
        start_image_logo.startAnimation(anim)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Handler().postDelayed({ callTabActivity() }, 1000)
            }

        })
        start_image_title.startAnimation(anim)
    }

    private fun callTabActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}