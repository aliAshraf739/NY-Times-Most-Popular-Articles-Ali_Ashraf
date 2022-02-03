package com.ali.ashraf.mvvm.ui.fragment.articlesList

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ali.ashraf.mvvm.R
import com.ali.ashraf.mvvm.ui.activity.MainActivity
import com.ali.ashraf.mvvm.ui.fragment.articleDetail.ArticleDetailFragment
import com.ali.ashraf.mvvm.utils.Util


class ArticlesListAdapter(private val activity: Activity): RecyclerView.Adapter<ArticlesListAdapter.ArticlesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_article, parent, false)
        return ArticlesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val obj = MainActivity.mListArticles[position]
        if (obj != null) {

            if(obj.media?.isNotEmpty() == true){
               if(obj.media[0]?.mediaMetadata?.isNotEmpty()==true){
                   val url = obj.media[0]?.mediaMetadata?.get(0)?.url+""
                   Glide.with(activity).load(url).placeholder(R.drawable.img_placeholder).into(holder.ivArticle)
               }
            }

            if(obj.source?.isNotEmpty() == true){
                holder.tvSource.text = obj.source
            }

            if(obj.type?.isNotEmpty() == true){
                holder.tvType.text = obj.type
            }

            if(obj.section?.isNotEmpty() == true){
                holder.tvSection.text = obj.section
            }

            if(obj.title?.isNotEmpty() == true){
                holder.tvTitle.text = obj.title
            }
        }

        holder.itemView.setOnClickListener{
            ArticleDetailFragment.obj = obj
            Util.performMainNavigation(activity, R.id.action_articlesListFragment_to_articleDetailFragment)
        }

    }

    override fun getItemCount(): Int {
        return MainActivity.mListArticles.size
    }

    inner class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSource: TextView = itemView.findViewById(R.id.tvSource)
        val tvType: TextView = itemView.findViewById(R.id.tvType)
        val tvSection: TextView = itemView.findViewById(R.id.tvSection)
        val ivArticle: ImageView = itemView.findViewById(R.id.ivArticle)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }
}