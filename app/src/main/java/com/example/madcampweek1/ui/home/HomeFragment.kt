package com.example.madcampweek1.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampweek1.CustomAdapter
import com.example.madcampweek1.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var phoneNumberSet = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // JSON 데이터 가져오기
        getJson("Number.json", phoneNumberSet)

        // RecyclerView 설정
        val recyclerView: RecyclerView = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager

        val customAdapter = CustomAdapter(phoneNumberSet)
        recyclerView.adapter = customAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    public fun getJson(filename: String, result: ArrayList<String>) {
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

                val name = jsonObject.getString("name")
                val phoneNumber = jsonObject.getString("phoneNumber")
                val relationship = jsonObject.getString("relationship")
                val picture = jsonObject.getString("picture")

                val entry = if (picture.isNotBlank()) {
                    "$name,$phoneNumber,$relationship,$picture"
                } else {
                    "$name,$phoneNumber,$relationship"
                }

                result.add(entry)
            }
            result.sort()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
