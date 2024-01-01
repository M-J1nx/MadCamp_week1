package com.example.madcampweek1.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampweek1.ImageAdapter
import com.example.madcampweek1.R
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

        val recyclerView: RecyclerView = binding.recyclerView
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager


        val imageList: List<Int> = listOf(
            R.drawable.pokea, R.drawable.pokeb, R.drawable.pokec, R.drawable.poked,
            R.drawable.pokee, R.drawable.pokef,R.drawable.pokeg,R.drawable.pokeh,
            R.drawable.pokei,R.drawable.pokej,R.drawable.pokek,R.drawable.pokel,
            R.drawable.pokem,R.drawable.poken,R.drawable.pokeo,R.drawable.pokep,
            R.drawable.pokeq,R.drawable.poker,R.drawable.pokes,R.drawable.poket,
            R.drawable.pokeu,R.drawable.pokev,)



        val adapter = ImageAdapter(imageList) { position ->
            val intent = Intent(requireActivity(), SubActivity::class.java)
            intent.putExtra("clicked_image_index", position)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

