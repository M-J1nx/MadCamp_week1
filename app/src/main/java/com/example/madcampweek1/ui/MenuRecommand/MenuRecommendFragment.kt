package com.example.madcampweek1.ui.MenuRecommand

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.madcampweek1.R
import com.example.madcampweek1.databinding.FragmentMenuRecommendBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MenuRecommendFragment : Fragment() {

    companion object {
        fun newInstance() = MenuRecommendFragment()
    }

    private lateinit var viewModel: MenuRecommendViewModel
    private lateinit var binding: FragmentMenuRecommendBinding

    private var foodSet = ArrayList<String>()
    private var selectedTimeSet: ArrayList<Int> = ArrayList<Int>(List(100) { 0 })
    private var selectIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuRecommendBinding.inflate(inflater, container, false)
        val view = binding.root

        val pickedCategory = arguments?.getString("pickedCategory")


        val menuRecommend: AppCompatImageButton = view.findViewById(R.id.recommend)
        menuRecommend.setOnClickListener {

            // JSON 데이터 가져오기
            getJson("Food.json", foodSet, pickedCategory)
            updateUI()
        }

        val confirmButton: AppCompatImageButton = view.findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            if(selectedTimeSet[selectIndex-1]>10) selectedTimeSet[selectIndex-1]=10
            else selectedTimeSet[selectIndex-1]+=1
            // binding.selectedBar.text = selectedTimeSet[selectIndex-1].toString()
            putStar(selectedTimeSet[selectIndex-1])
        }

        val resetButton: AppCompatImageButton = view.findViewById(R.id.resetButton)
        resetButton.setOnClickListener{
            selectedTimeSet[selectIndex-1]=0
            updateUI()
        }

        return binding.root
    }

    private fun putStar(time:Int) {
        var star = StringBuilder()
        repeat(time) {
            star.append("★")
        }
        binding.selected.text = star
    }

    private fun updateUI() {
        //pick menu
        var pick = foodSet.random()
        val resultList = convertJsonToList(pick)
        popOut(binding.pickedMenu)

        binding.NoNumber.text = resultList[1]
        binding.pickedMenu.text = resultList[0]
        binding.type.text = resultList[3]

        selectIndex = resultList[1].toInt()
        if (selectedTimeSet[selectIndex-1]==0) binding.selected.text ="한 번도 선택하지 않았습니다!"
        else putStar(selectedTimeSet[selectIndex-1])

        binding.comment.text = resultList[2]

        val imageView:ImageView = binding.imageView
        val backView:ImageView = binding.imageViewBackground
        when (resultList[3]) {
            "한식" -> {
                imageView.setImageResource(R.drawable.korean_food)
                backView.setImageResource(R.drawable.korean_background)
            }
            "중식" -> {
                imageView.setImageResource(R.drawable.chinese_food)
                backView.setImageResource(R.drawable.chinese_background)
            }
            "일식" -> {
                imageView.setImageResource(R.drawable.japanese_food)
                backView.setImageResource(R.drawable.japen_background)
            }
            "양식" -> {
                imageView.setImageResource(R.drawable.western_food)
                backView.setImageResource(R.drawable.western_background)
            }
            "분식" -> {
                imageView.setImageResource(R.drawable.instant_food)
                backView.setImageResource(R.drawable.instant_background)
            }
            "간편식" -> {
                imageView.setImageResource(R.drawable.snackbar_food)
                backView.setImageResource(R.drawable.snackbar_background)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun convertJsonToList(jsonString: String): List<String> {
        val jsonObject = JSONObject(jsonString)
        val resultList = mutableListOf<String>()

        // JSON 객체의 키(속성)들을 반복
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            val key = iterator.next() as String
            resultList.add(jsonObject.getString(key))
        }

        return resultList
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuRecommendViewModel::class.java)
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
        val popOutAnimatorX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.5f, 1.0f)
        popOutAnimatorX.duration = 300
        popOutAnimatorX.start()

        val popOutAnimatorY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.5f, 1.0f)
        popOutAnimatorY.duration = 300
        popOutAnimatorY.start()
    }
}
