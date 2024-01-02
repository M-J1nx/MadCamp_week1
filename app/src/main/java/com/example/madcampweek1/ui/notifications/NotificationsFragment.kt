package com.example.madcampweek1.ui.notifications

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.madcampweek1.R
import com.example.madcampweek1.databinding.FragmentNotificationsBinding
import com.example.madcampweek1.ui.MenuRecommand.MenuRecommendFragment

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private  val iDuration: Long=800


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Call menu recommend Fragment
    val fragment = MenuRecommendFragment()
    private var pickedCategory: String = ""

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
        binding.rotateResultTv.bringToFront()

        val recommendButton: Button = root.findViewById(R.id.recommendButton)
        recommendButton.setOnClickListener {

            val parentViewGroup: ViewGroup = root.findViewById(R.id.notificationContainer)

            val triangle: View = parentViewGroup.findViewById(R.id.triangle)
            val rotateButton: Button = parentViewGroup.findViewById(R.id.rotateButton)
            val resetButton: Button = parentViewGroup.findViewById(R.id.resetButton)
            val recommendButton: Button = parentViewGroup.findViewById(R.id.recommendButton)


            parentViewGroup.removeView(triangle)
            parentViewGroup.removeView(rotateButton)
            parentViewGroup.removeView(resetButton)
            parentViewGroup.removeView(recommendButton)

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
        binding.rotateResultTv.text = ""
        binding.additionalButton.visibility=View.GONE
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.notificationContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }



    fun rotateRoulette() {
        val rotateListener = object: FoodRoulette.RotateListener {
            override fun onRotateStart() {
                binding.closePokeball
                binding.rotateResultTv.text = ""
                binding.openPokeball.visibility= View.INVISIBLE
                val animatorY: ValueAnimator = ObjectAnimator.ofFloat(binding.closePokeball, "translationY", -90f, 80f,-55f,60f,0f)
                animatorY.duration = 900
                animatorY.start()
            }

            override fun onRotateEnd(pickedCategory: String) {
                binding.rotateResultTv.text = "$pickedCategory"
                val bundle = Bundle()
                bundle.putString("pickedCategory", pickedCategory)
                fragment.arguments = bundle
                binding.openPokeball.visibility= View.VISIBLE

            }
        }
        val toDegrees = (2000..10000).random().toFloat()
        binding.roulette.rotateRoulette(toDegrees, 1000, rotateListener)
    }

    fun resetRoulette() {
        binding.roulette.rotateRoulette(0.0F, 1000, null)
        binding.openPokeball.visibility= View.INVISIBLE
        binding.rotateResultTv.text = ""
    }
}