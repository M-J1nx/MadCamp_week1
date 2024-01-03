package com.example.madcampweek1.ui.notifications

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
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
    private var doubleTap = false


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
            if (doubleTap) {
                activity?.finish() // Finish the activity containing the fragment
                // If you want to finish the app regardless of the activity, use:
                // requireActivity().finishAffinity()
            } else {
                doubleTap = true
                // Set a delay for the second tap (in milliseconds)
                val delayMillis = 2000L // 2 seconds delay for the second tap
                binding.roulette.rotateRoulette(0.0F, 1000, null)
                binding.openPokeball.visibility= View.INVISIBLE
                binding.rotateResultTv.text = ""

                // Reset the doubleTap variable after the delay
                Handler().postDelayed({ doubleTap = false }, delayMillis)
            }
        }


        val additionalButton: Button = root.findViewById(R.id.additionalButton)
        additionalButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pokemon.com/us"))
            startActivity(browserIntent)
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
                animatorY.duration = 1000
                animatorY.start()

                val handler = Handler()
                handler.postDelayed({
                    // This code will be executed after a delay of 900 milliseconds
                    binding.popImg.visibility = View.VISIBLE

                    val animatorAlpha: ValueAnimator = ObjectAnimator.ofFloat(binding.popImg, View.ALPHA, 0f, 1f)
                    animatorAlpha.duration = 180
                    animatorAlpha.start()
                }, 950)

            }

            override fun onRotateEnd(pickedCategory: String) {
                val animatorAlpha: ValueAnimator = ObjectAnimator.ofFloat(binding.popImg, View.ALPHA, 1f, 0f)
                animatorAlpha.duration = 150
                animatorAlpha.start()
                val handler = Handler()
                handler.postDelayed({
                    binding.popImg.visibility=View.GONE
                }, 150)

                binding.rotateResultTv.text = "$pickedCategory"

                val bundle = Bundle()
                bundle.putString("pickedCategory", pickedCategory)
                fragment.arguments = bundle
                binding.openPokeball.visibility= View.VISIBLE

            }
        }
        val toDegrees = (2000..10000).random().toFloat()
        binding.roulette.rotateRoulette(toDegrees, 1200, rotateListener)
    }

//    fun resetRoulette() {
//        binding.roulette.rotateRoulette(0.0F, 1000, null)
//        binding.openPokeball.visibility= View.INVISIBLE
//        binding.rotateResultTv.text = ""
//    }
}