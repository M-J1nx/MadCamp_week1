package com.example.madcampweek1

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

//class ImageAdapter( val imageList: MutableList<Uri>, private val onItemClickListener: (Int) -> Unit) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
//    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageView)
//    }
//
//    inner class ViewHolder(itemView:View):ImageViewHolder (itemView){
//
//        val img = itemView.form_recycler_img
//
//        override fun bind(item) {
//
//            if(item.image != ""){
//                img.setImageURI(Uri.parse(item.image))
//            }
//            img.setOnClickListener {
//                itemClick(adapterPosition,item)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
//
//        return ImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val currentImage = imageList[position]
//        holder.imageView.setImageURI(currentImage)
//
//        holder.itemView.setOnClickListener {
//            onItemClickListener.invoke(position)
//        }
//    }
//    override fun getItemViewType(position: Int): Int {
//        return imageList[position].type
//    }
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
//
//
//
//}

class ImageAdapter(
    private val imageList: MutableList<Uri>,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentImage = imageList[position]
        holder.imageView.setImageURI(currentImage)

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
