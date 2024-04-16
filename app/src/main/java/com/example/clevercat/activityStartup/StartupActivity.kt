package com.example.clevercat.activityStartup

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.clevercat.R
import com.example.clevercat.activityMain.MainActivity
import com.example.clevercat.databinding.ActivityStartupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //show logo
        val fadeIn: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.s_fade_in)
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {
                binding.logo.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                startActivity(MainActivity.getIntent(this@StartupActivity));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    overrideActivityTransition(
                        OVERRIDE_TRANSITION_OPEN,
                        R.anim.s_fade_in,
                        R.anim.s_fade_out
                    )
                } else {
                    overridePendingTransition(R.anim.s_fade_in, R.anim.s_fade_out);
                }
            }
        })
        //animation start
        binding.logo.startAnimation(fadeIn)
    }

}