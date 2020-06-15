package com.example.adaptativeanchorbanner

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {

    lateinit var adContainerView: FrameLayout
    lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Call the function to initialize AdMob SDK
        MobileAds.initialize(this) { }

        //get the reference to your FrameLayout
        adContainerView = findViewById(R.id.adView_container)

        //Create an AdView and put it into your FrameLayout
        adView = AdView(this);
        this.adContainerView.addView(adView)

        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        //start requesting banner ads
        loadBanner();

    }

    private fun getAdSize(): AdSize? {
        //Determine the screen width to use for the ad width.
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density

        //you can also pass your selected width here in dp
        val adWidth = (widthPixels / density).toInt()

        //return the optimal size depends on your orientation (landscape or portrait)
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }

    private fun loadBanner() {
        val adRequest: AdRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()
        val adSize = getAdSize()
        // Set the adaptive ad size to the ad view.
        adView.adSize = adSize

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }
}