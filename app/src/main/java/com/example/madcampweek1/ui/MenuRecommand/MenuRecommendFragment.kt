package com.example.madcampweek1.ui.MenuRecommand

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.example.madcampweek1.ui.home.HomeFragment
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MenuRecommendFragment : Fragment() {

    companion object {
        fun newInstance() = MenuRecommendFragment()
    }

    private lateinit var viewModel: MenuRecommendViewModel
    private lateinit var binding: FragmentMenuRecommendBinding

    private val foodSet = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuRecommendBinding.inflate(inflater, container, false)
        val view = binding.root

        val pickedCategory = arguments?.getString("pickedCategory")

        // JSON 데이터 가져오기
        getJson("Food.json", foodSet, pickedCategory)

        // background menu
        binding.menu.text = foodSet.joinToString("   ")

        val menuRecommend: Button = view.findViewById(R.id.recommendMimikyu)
        menuRecommend.setOnClickListener {

            //pick menu
            var pick = foodSet.random()
            popOut(binding.pickedMenu)

            binding.recommendMimikyu.setIconResource(R.drawable.answer_mimikyu)
            binding.recommendMimikyu.iconTint = null

            val colorDrawable = ColorDrawable(Color.parseColor("#00FF0000"))
            binding.alarm.background = colorDrawable
            binding.alarm.text = ""

            binding.pickedMenu.text = pick
        }

        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuRecommendViewModel::class.java)
    }

    public fun getJson(filename: String,  result: ArrayList<String>,pickedCategory: String?) {
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
                        val food = foodArray.getString(j)
                        val entry = "$food"
                        result.add(entry)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun popOut(view: View) {
        val popOutAnimatorX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.5f, 1.5f)
        popOutAnimatorX.duration = 300
        popOutAnimatorX.start()

        val popOutAnimatorY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.5f, 1.5f)
        popOutAnimatorY.duration = 300
        popOutAnimatorY.start()
    }
}
