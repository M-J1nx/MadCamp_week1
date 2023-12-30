package com.example.madcampweek1.ui.MenuRecommand

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.madcampweek1.R
import com.example.madcampweek1.databinding.         FragmentMenuRecommendBinding

class MenuRecommendFragment : Fragment() {

    companion object {
        fun newInstance() = MenuRecommendFragment()
    }

    private lateinit var viewModel: MenuRecommendViewModel
    private lateinit var binding: FragmentMenuRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuRecommendBinding.inflate(inflater, container, false)
        val view = binding.root

        val menuRecommend: Button = view.findViewById(R.id.recommendMimikyu)
        menuRecommend.setOnClickListener {
            Log.d("Button", "recommend activated")
        }

        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuRecommendViewModel::class.java)
    }
}
