    package com.example.madcampweek1

    import android.app.Activity
    import android.content.ContentResolver
    import android.content.Context
    import android.content.Intent
    import android.net.Uri
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.ImageView
    import android.view.ViewGroup
    import android.widget.Button
    import android.widget.ImageButton
    import androidx.fragment.app.Fragment
    import com.example.madcampweek1.SubActivity
    import com.example.madcampweek1.databinding.FragmentDashboardBinding

    class SubActivity : AppCompatActivity() {

        var imageListEnlarge: MutableList<Uri> = mutableListOf()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sub)

            // Retrieve the imageList from the intent
            imageListEnlarge = intent.getParcelableArrayListExtra("imageList") ?: mutableListOf()

            supportActionBar?.title = "사진 크게 보기"
            supportActionBar?.hide()

            val clickedImageIndex = intent.getIntExtra("clicked_image_index", -1)
            val imageViewL = findViewById<ImageView>(R.id.imageViewL)

            if (clickedImageIndex != -1 && clickedImageIndex < imageListEnlarge.size) {
                imageViewL.setImageURI(imageListEnlarge[clickedImageIndex])
            }

            imageViewL.setOnClickListener {
                finish()
            }
            val deleteButton = findViewById<ImageView>(R.id.deleteButton)
            deleteButton.setOnClickListener {
                // Sending data back to DashboardFragment
                val intent = Intent()
                intent.putExtra("delete_index", clickedImageIndex)
                intent.putExtra("delete_flag",1)// Replace "key_name" and "Your data" with your actual data
                setResult(Activity.RESULT_OK, intent)
                finish() // Close SubActivity and return to DashboardFragment
            }
        }
    }


