package com.example.madcampweek1.ui.dashboard

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    val PERMISSIONS_REQUEST_CODE = 100
    val REQUEST_CODE_SUB_ACTIVITY = 123
    private val SELECT_IMAGE_REQUEST = 1
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE)
    var positionMain = 0
    var imageList:MutableList<Uri> = mutableListOf()
    private lateinit var adapter: ImageAdapter

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSIONS_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //권한 허용
                }else{
                    //권한 거부됨
                }
                return
            }
        }
    }
    private fun requestPermission(){
        val permissionCheck = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            // Check if rationale is needed
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ){
                // Rationale needed (user previously denied the request)
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }else{
                // No need for rationale
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }else{
            // Permission already granted
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root




        requestPermission()

        val recyclerView: RecyclerView = binding.recyclerView
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager

        binding.getImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_GET_IMAGE)

        }
        binding.getImageBtn2.setOnClickListener {
            imageList.shuffle()
            adapter.notifyDataSetChanged()


        }
        binding.getImageBtn3.setOnClickListener {
            imageList.removeAt(0)
            adapter.notifyDataSetChanged()


        }
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

        val imageResourceIds = intArrayOf(
            R.drawable.pokea, R.drawable.pokeb, R.drawable.pokec, R.drawable.poked,
            R.drawable.pokee, R.drawable.pokef, R.drawable.pokeg, R.drawable.pokeh,
            R.drawable.pokei, R.drawable.pokej, R.drawable.pokek, R.drawable.pokel,
            R.drawable.pokem, R.drawable.poken, R.drawable.pokeo, R.drawable.pokep,
            R.drawable.pokeq, R.drawable.poker, R.drawable.pokes, R.drawable.poket,
            R.drawable.pokeu, R.drawable.pokev
        )

        imageList = convertDrawableResourcesToUri(requireContext(), *imageResourceIds)
        adapter = ImageAdapter(imageList) { position ->
            val intent = Intent(requireActivity(), SubActivity::class.java)
            intent.putExtra("clicked_image_index", position)
            intent.putExtra("imageList",ArrayList(imageList))
            startActivityForResult(intent,1)
        }
        recyclerView.adapter = adapter

        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val deleteFlag = data?.getIntExtra("delete_flag", 0) // Retrieve delete flag from Intent
            if (deleteFlag == 1) {
                val deleteIndex = data.getIntExtra("delete_index", -1)
                if (deleteIndex != -1) {
                    imageList.removeAt(deleteIndex)
                    adapter.notifyDataSetChanged()
                }
            }

            when (requestCode) {
                REQUEST_GET_IMAGE -> {
                    try {
                        val uri = data?.data
                        uri?.let {uri   ->
                            imageList.add(0, uri)
                            adapter.notifyDataSetChanged()
                        }
                    } catch (e: Exception) {
                        // Handle exceptions
                    }
                }
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

