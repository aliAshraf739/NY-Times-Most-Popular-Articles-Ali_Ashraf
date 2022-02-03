package com.ali.ashraf.mvvm.ui.fragment.articleDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ali.ashraf.mvvm.R
import com.ali.ashraf.mvvm.data.model.article.ResultsItem
import com.bumptech.glide.Glide


class ArticleDetailFragment : Fragment() {

    private lateinit var ivArticle: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvAbstract: TextView 
    private lateinit var tvSource: TextView 
    private lateinit var tvType: TextView
    private lateinit var tvSection: TextView 
    private lateinit var tvSubSection: TextView 
    private lateinit var tvPublishDate: TextView 

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews(view)
        setDetails()
    }
    
    private fun setUpViews(view: View) {
        ivArticle = view.findViewById(R.id.ivArticle)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvAbstract = view.findViewById(R.id.tvAbstract)
        tvSource = view.findViewById(R.id.tvSource)
        tvType = view.findViewById(R.id.tvType)
        tvSection = view.findViewById(R.id.tvSection)
        tvSubSection = view.findViewById(R.id.tvSubSection)
        tvPublishDate = view.findViewById(R.id.tvPublishDate)
    }

    private fun setDetails() {
        if (obj != null) {

            if(obj?.media?.get(0)?.mediaMetadata?.get(0)?.url?.isNotEmpty() == true){
                if(obj?.media?.isNotEmpty() == true){
                    if(obj?.media?.get(0)?.mediaMetadata?.isNotEmpty() == true){
                        val url = obj?.media?.get(0)?.mediaMetadata?.get(0)?.url+""
                        Glide.with(requireActivity()).load(url).placeholder(R.drawable.img_placeholder).into(ivArticle)
                    }
                }
            }

            if(obj?.title?.isNotEmpty() == true){
                tvTitle.text = obj?.title
            }

            if(obj?.articleAbstract?.isNotEmpty() == true){
                tvAbstract.text = obj?.articleAbstract
            }

            if(obj?.source?.isNotEmpty() == true){
                tvSource.text = obj?.source
            }

            if(obj?.type?.isNotEmpty() == true){
                tvType.text = obj?.type
            }

            if(obj?.section?.isNotEmpty() == true){
                tvSection.text = obj?.section
            }

            if(obj?.subsection?.isNotEmpty() == true){
                tvSubSection.text = obj?.subsection
            }

            if(obj?.publishedDate?.isNotEmpty() == true){
                tvPublishDate.text = obj?.publishedDate
            }

        }
    }

    companion object {
        var obj:ResultsItem? = null
    }

}