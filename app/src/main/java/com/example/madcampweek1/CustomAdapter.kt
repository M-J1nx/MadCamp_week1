package com.example.madcampweek1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val localDataSet: ArrayList<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View, pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    // 뷰홀더 클래스
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    // ViewHolder 객체를 생성하고 초기화 함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_phonenumber, parent, false)
        return ViewHolder(view)
    }

    // ViewHolder 안의 내용을 position에 해당되는 데이터로 교체함
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = localDataSet[position]
        holder.textView.text = text

        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(it, position)
        }
    }

    // 전체 데이터의 갯수
    override fun getItemCount(): Int {
        return localDataSet.size
    }
}