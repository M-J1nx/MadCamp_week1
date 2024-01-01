package com.example.madcampweek1.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.madcampweek1.R

class IndividualActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual)


        var nameIntent = intent.getStringExtra("name")
        var nameView: TextView = findViewById(R.id.nameView)
        nameView.setText(nameIntent)

        getSupportActionBar()?.setTitle("${nameIntent}님의 세부정보")


        var numberIntent = intent.getStringExtra("phoneNumber")
        var numberView: TextView = findViewById(R.id.numberView)
        numberView.setText(numberIntent)

        var relIntent = intent.getStringExtra("relationship")
        var relView: TextView = findViewById(R.id.relationshipView)
        relView.setText(relIntent)

        val picIntent = intent.getStringExtra("picture")
        if (picIntent != null) {
            val imageView: ImageView = findViewById(R.id.picture)
            val imageResource =
                resources.getIdentifier(picIntent, "drawable", packageName)
            imageView.setImageResource(imageResource)
        }
    }
}