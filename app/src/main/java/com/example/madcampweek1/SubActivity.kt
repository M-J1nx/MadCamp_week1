package com.example.madcampweek1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.madcampweek1.SubActivity
import com.example.madcampweek1.databinding.FragmentDashboardBinding

class SubActivity : AppCompatActivity() {

    private val images = intArrayOf(
        R.drawable.pokea,
        R.drawable.pokeb,
        R.drawable.pokec,
        R.drawable.poked,
        R.drawable.pokee,
        R.drawable.pokef,
        R.drawable.pokeg,
        R.drawable.pokeh,
        R.drawable.pokei,
        R.drawable.pokej,
        R.drawable.pokek,
        R.drawable.pokel,
        R.drawable.pokem,
        R.drawable.poken,
        R.drawable.pokeo,
        R.drawable.pokep,
        R.drawable.pokeq,
        R.drawable.poker,
        R.drawable.pokes,
        R.drawable.poket,
        R.drawable.pokeu,
        R.drawable.pokev
    )


        // Add other image resources here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub) // Ensure this is your SubActivity layout file

        supportActionBar?.setTitle("사진 크게 보기")

        val clickedImageIndex = intent.getIntExtra("clicked_image_index", -1)
        val imageViewL = findViewById<ImageView>(R.id.imageViewL) // Get reference to the ImageView

        if (clickedImageIndex != -1 && clickedImageIndex < images.size) {
            imageViewL.setImageResource(images[clickedImageIndex])
        }

        imageViewL.setOnClickListener{
            finish()
        }

    }


}