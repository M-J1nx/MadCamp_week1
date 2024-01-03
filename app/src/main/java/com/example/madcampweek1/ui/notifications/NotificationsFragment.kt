package com.example.madcampweek1.ui.notifications

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import com.example.madcampweek1.R
import com.example.madcampweek1.databinding.FragmentNotificationsBinding
import com.example.madcampweek1.ui.MenuRecommand.MenuRecommendFragment
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

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
    private var foodCategory = ArrayList<String>()
    private var foodSetString = ""
    private var addedItemIndex=59

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



        val handler = Handler()
        val maxIterations = 12 // Number of times to toggle visibility

        var iterationCount = 0
        val delay = 300L // Delay in milliseconds

        fun toggleVisibility(isImg1Visible: Boolean) {
            if (iterationCount < maxIterations) {
                if (isImg1Visible) {
                    binding.loadingImg1.visibility = View.VISIBLE
                    binding.loadingImg2.visibility = View.INVISIBLE
                } else {
                    binding.loadingImg1.visibility = View.INVISIBLE
                    binding.loadingImg2.visibility = View.VISIBLE
                }

                handler.postDelayed({
                    iterationCount++
                    toggleVisibility(!isImg1Visible)
                }, delay)
        }
        if (iterationCount==maxIterations){

            binding.loadingImg1.visibility=View.GONE
            binding.loadingImg2.visibility=View.GONE
            binding.loadingback.visibility=View.GONE
            binding.loadingText.visibility=View.GONE

        }
        }

// Start the toggling process
        toggleVisibility(true)










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
            // connectWebSite()
            printDialogue()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun connectWebSite() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pokemon.com/us"))
        startActivity(browserIntent)
    }

    fun printDialogue() {
        val regionArray = resources.getStringArray(R.array.signup_select_type)

        AlertDialog.Builder(requireContext())
            .setView(R.layout.item_dialogue)
            .show()
            .also { alertDialog ->

                if (alertDialog == null) {
                    return@also
                }

                val addedFoodName = alertDialog.findViewById<EditText>(R.id.addItemName)?.text

                val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dialogue_dropdown, regionArray)
                val autoCompleteTextView = alertDialog.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
                autoCompleteTextView.setAdapter(arrayAdapter)

                autoCompleteTextView.setOnItemClickListener { adapterView: AdapterView<*>, view2: View, i: Int, l: Long ->
                    val pickedCategory = autoCompleteTextView.text.toString()
                    getJson("Food.json", foodCategory, pickedCategory)
                    foodSetString = foodCategory.joinToString(", ")
                    alertDialog.findViewById<TextView>(R.id.existFood).text = "$foodSetString"
                }

                val commentAdded = alertDialog.findViewById<EditText>(R.id.addItemComment)?.text

                val URLAdded = alertDialog.findViewById<EditText>(R.id.addItemURL)?.text

                val submitButton = alertDialog.findViewById<Button>(R.id.addItemButton)
                submitButton?.setOnClickListener {
                    val bundle = Bundle()

                    pickedCategory = autoCompleteTextView.text.toString()
                    val foodExists = foodCategory.contains(addedFoodName.toString())
                    if (!foodExists) {
                        var totalIndex = addedItemIndex++
                        bundle.putString("addedFoodName", addedFoodName?.toString())
                        bundle.putString("pickedCategory", pickedCategory)
                        bundle.putString("commentAdded", commentAdded?.toString())
                        bundle.putInt("addedIndex", totalIndex)
                        bundle.putString("newUrl",URLAdded.toString())

                        fragment.arguments = bundle

                        foodSetString += ", $addedFoodName"
                        foodCategory.add(addedFoodName.toString())
                        alertDialog.findViewById<TextView>(R.id.existFood).text = "$foodSetString"

                    } else {
                        var addWarn = Toast.makeText(requireContext(), "이미 추가된 음식입니다.", Toast.LENGTH_SHORT)
                        addWarn.show()
                        Handler().postDelayed(Runnable {
                            run(){
                                addWarn.cancel()
                            }
                        },1000)
                    }
                }

                val exitButton = alertDialog.findViewById<Button>(R.id.exitItemButton)
                exitButton?.setOnClickListener {
                    alertDialog.dismiss()

                }
            }
    }

    fun callExistFood() {

    }

    fun recommendFood() {
        binding.rotateResultTv.text = ""
        binding.additionalButton.visibility=View.GONE
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.notificationContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    public fun getJson(filename: String, result: ArrayList<String>, pickedCategory: String?) {
        try {
            result.clear()

            val inputStream = requireContext().assets.open(filename)
            val reader = BufferedReader(InputStreamReader(inputStream))

            val buffer = StringBuffer()
            var line: String? = reader.readLine()

            while (line != null) {
                buffer.append("$line\n")
                line = reader.readLine()
            }

            val jsonData = buffer.toString()
            val jsonArray = JSONArray(jsonData)

            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                val category = jsonObject.getString("category")

                if (pickedCategory == null || category == pickedCategory) {
                    val foodArray = jsonObject.getJSONArray("food")
                    for (j in 0 until foodArray.length()) {
                        val foodObject = foodArray.getJSONObject(j)
                        val foodName = foodObject.getString("name")
                        result.add(foodName)

                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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