package com.example.madcampweek1.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.madcampweek1.R
import com.example.madcampweek1.databinding.FragmentNotificationsBinding
import com.example.madcampweek1.ui.MenuRecommand.MenuRecommendFragment

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val recommendButton: Button = root.findViewById(R.id.recommendButton)
        recommendButton.setOnClickListener {
            recommendFood()
        }

        val rotateButton: Button = root.findViewById(R.id.rotateButton)
        rotateButton.setOnClickListener {
            rotateRoulette()
        }

        val resetButton: Button = root.findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetRoulette()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun recommendFood() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        // Call menu recommend Fragment
        val fragment = MenuRecommendFragment()
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun rotateRoulette() {
        val rotateListener = object: FoodRoulette.RotateListener {
            override fun onRotateStart() {
                binding.rotateResultTv.text = "오늘 먹을 음식의 카테고리는? : "
            }

            override fun onRotateEnd(result: String) {
                binding.rotateResultTv.text = "오늘 먹을 음식의 카테고리는? : $result"
            }
        }

        val toDegrees = (2000..10000).random().toFloat()
        binding.roulette.rotateRoulette(toDegrees, 1000, rotateListener)
    }

    fun resetRoulette() {
        binding.roulette.rotateRoulette(0.0F, 1000, null)
    }
}