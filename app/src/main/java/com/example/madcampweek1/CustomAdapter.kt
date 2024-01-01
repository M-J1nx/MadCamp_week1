package com.example.madcampweek1

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampweek1.ui.home.IndividualActivity

class CustomAdapter(private val localDataSet: ArrayList<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View, pos: Int)
    }

    // 뷰홀더 클래스
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.name)
        val numberView: TextView = itemView.findViewById(R.id.phoneNumber)
        val pictureView: ImageView = itemView.findViewById(R.id.picture)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition)
            }
        }
    }

    // ViewHolder 객체를 생성하고 초기화 함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_phonenumber, parent, false)
        return ViewHolder(view)
    }

    // ViewHolder 안의 내용을 position에 해당되는 데이터로 교체함
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("test", "$localDataSet")
        val individualArray = localDataSet[position].split(",").map{it.trim()}
        holder.nameView.text = individualArray[0]
        holder.numberView.text = individualArray[1]

        var size = individualArray.size
        Log.d("test", "$individualArray has $size")

        val context = holder.itemView.context
        if (individualArray.size > 3) {
            val imageName = individualArray[3]
            val imageResource = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            holder.pictureView.setImageResource(imageResource)
        }

        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(it, position)
            // Toast.makeText(holder.itemView.context, "$text 클릭됨", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, IndividualActivity::class.java)
            intent.putExtra("name", individualArray[0])
            intent.putExtra("phoneNumber", individualArray[1])
            intent.putExtra("relationship", individualArray[2])
            intent.putExtra("picture", individualArray.getOrNull(3))
            context.startActivity(intent)
        }
    }

    // 전체 데이터의 갯수
    override fun getItemCount(): Int {
        return localDataSet.size
    }
}