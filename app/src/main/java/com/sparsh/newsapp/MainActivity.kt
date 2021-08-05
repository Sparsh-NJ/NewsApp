package com.sparsh.newsapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), Adapter.NewsItemClicked {

    private lateinit var recyclerView : RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter : Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "News"

         fetchData()

        recyclerView = findViewById(R.id.RecyclerView)
        layoutManager = LinearLayoutManager(this)

    }
     private fun fetchData() {
         val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
         val jsonObjectRequest = JsonObjectRequest(
             Request.Method.GET,
             url,
             null,
             {
              val newsArray = ArrayList<News>()
                 val newsJsonArray = it.getJSONArray("articles")
                 for(i in 0 until newsJsonArray.length()){
                     val obj = newsJsonArray.getJSONObject(i)
                     val news = News(
                         obj.getString("title"),
                         obj.getString("author"),
                         obj.getString("url"),
                         obj.getString("urlToImage")
                     )
                     newsArray.add(news)
                     adapter = Adapter(this)
                     recyclerView.adapter = adapter
                     recyclerView.layoutManager = layoutManager
                     adapter.updateNews(newsArray)
                 }
             },
             {

             })
         MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
     }

    override fun onItemClicked(item: News) {

       val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }
}