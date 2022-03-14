package com.example.food.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.Model.Meal
import com.example.food.R
import kotlinx.android.synthetic.main.item_save.view.*

class saveAdapter:RecyclerView.Adapter<saveAdapter.viewHolder> (){
    inner class viewHolder(item:View):RecyclerView.ViewHolder(item)
    private val differCallback=object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal== newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
         return oldItem == newItem
        }

    }
    val differ= AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_save,parent,false)
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val save=differ.currentList[position]
        holder.itemView.apply {
            Glide.with(context).load(save.strMealThumb).into(imageView4)
            textView4.text=save.strMeal
            textView7.text=save.strArea
            setOnClickListener {
                onItemClickListener?.let { it(save) }
            }
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
    private var onItemClickListener:((Meal)->Unit)?=null
    fun setOnItemClickListener(listener:(Meal)->Unit){
        onItemClickListener=listener
    }
}