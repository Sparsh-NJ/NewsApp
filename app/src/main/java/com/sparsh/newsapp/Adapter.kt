package com.sparsh.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class Adapter(private val listener : NewsItemClicked) : RecyclerView.Adapter<Adapter.NewsViewHolder>(){
    private val items : ArrayList<News> = ArrayList()

    class NewsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        val titleView : TextView = itemView.findViewById(R.id.Title)
        val imageView : ImageView = itemView.findViewById(R.id.newsImage)
        val author : TextView = itemView.findViewById(R.id.author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_layout,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])

        }
        return  viewHolder

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Picasso.get().load(currentItem.imageUrl).error(R.drawable.ic_launcher_background).into(holder.imageView)
    }
    override fun getItemCount(): Int {
       return items.size
    }
    fun updateNews( updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

    interface NewsItemClicked{
        fun onItemClicked(item : News)
    }
}