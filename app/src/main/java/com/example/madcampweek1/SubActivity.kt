package com.example.madcampweek1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SubActivity : AppCompatActivity() {

    private val images = intArrayOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g,
        R.drawable.h,
        R.drawable.i,
        R.drawable.dog,
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g,
        R.drawable.h,
        R.drawable.i,
        R.drawable.dog
    )


        // Add other image resources here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub) // Ensure this is your SubActivity layout file


        getSupportActionBar()?.setTitle("사진 크게 보기")

        val clickedImageIndex = intent.getIntExtra("clicked_image_index", -1)
        val imageViewL = findViewById<ImageView>(R.id.imageViewL) // Get reference to the ImageView

        if (clickedImageIndex != -1 && clickedImageIndex < images.size) {
            imageViewL.setImageResource(images[clickedImageIndex])
        }
    }
}