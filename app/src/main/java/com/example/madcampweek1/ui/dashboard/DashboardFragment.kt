package com.example.madcampweek1.ui.dashboard

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
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
    private val REQUEST_GET_IMAGE = 105

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



        binding.getImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_GET_IMAGE)
        }

       val imageResourceIds = arrayOf(
            R.drawable.pokea, R.drawable.pokeb, R.drawable.pokec, R.drawable.poked,
            R.drawable.pokee, R.drawable.pokef, R.drawable.pokeg, R.drawable.pokeh,
            R.drawable.pokei, R.drawable.pokej, R.drawable.pokek, R.drawable.pokel,
            R.drawable.pokem, R.drawable.poken, R.drawable.pokeo, R.drawable.pokep,
            R.drawable.pokeq, R.drawable.poker, R.drawable.pokes, R.drawable.poket,
            R.drawable.pokeu, R.drawable.pokev
        ).toIntArray()

        fun convertDrawableResourcesToUri(context: Context, vararg resourceIds: Int): MutableList<Uri> {
            val uriList = mutableListOf<Uri>()
            resourceIds.forEach { resourceId ->
                val imageUri = resourceId.takeIf { it != 0 }?.let {
                    Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(context.resources.getResourcePackageName(resourceId))
                        .appendPath(context.resources.getResourceTypeName(resourceId))
                        .appendPath(context.resources.getResourceEntryName(resourceId))
                        .build()
                }
                imageUri?.let { uriList.add(it) }
            }
            return uriList
        }


        val imageList: MutableList<Uri> = convertDrawableResourcesToUri(requireContext(), *imageResourceIds)


        val adapter = ImageAdapter(imageList) { position ->
            val intent = Intent(requireActivity(), SubActivity::class.java)
            intent.putExtra("clicked_image_index", position)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        return root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_GET_IMAGE -> {
                    try{
                        var uri = data?.data
                        binding.mainImgView.setImageURI(uri)
                    }catch (e:Exception){}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

