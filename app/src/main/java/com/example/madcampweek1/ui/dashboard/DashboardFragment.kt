package com.example.madcampweek1.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.madcampweek1.SubActivity
import com.example.madcampweek1.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Accessing views using binding
        val imageViewList = listOf(
            binding.imageViewA,
            binding.imageViewB,
            binding.imageViewC,
            binding.imageViewD,
            binding.imageViewE,
            binding.imageViewF,
            binding.imageViewG,
            binding.imageViewH,
            binding.imageViewI,
            binding.imageViewDog,
            binding.imageViewAA,
            binding.imageViewBB,
            binding.imageViewCC,
            binding.imageViewDD,
            binding.imageViewEE,
            binding.imageViewFF,
            binding.imageViewGG,
            binding.imageViewHH,
            binding.imageViewII,
            binding.imageViewDogDog


            // Add additional ImageViews here if needed
        )

        // Set OnClickListener for all ImageViews using a loop
        val imageClickListener = View.OnClickListener { view:View->
            val intent = Intent(requireActivity(), SubActivity::class.java)
            val index = imageViewList.indexOf(view)

            if (index != -1) {
                intent.putExtra("clicked_image_index", index)
                startActivity(intent)
            }
        }
        imageViewList.forEach { imageView ->
            imageView.setOnClickListener(imageClickListener)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
