package com.ali.ashraf.mvvm.ui.fragment.articlesList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali.ashraf.mvvm.R
import com.ali.ashraf.mvvm.data.model.article.GetArticlesResponse
import com.ali.ashraf.mvvm.data.model.article.ResultsItem
import com.ali.ashraf.mvvm.ui.activity.MainActivity
import com.ali.ashraf.mvvm.utils.Resource
import com.ali.ashraf.mvvm.utils.Status
import com.ali.ashraf.mvvm.viewmodel.MainViewModel
import com.builbee.app.util.Internet
import com.builbee.app.util.Loading
import com.example.codechallenge_plentina.util.Alert


class ArticlesListFragment : Fragment() {

    private val TAG = "ArticlesListFragment"

    private lateinit var rvArticles:RecyclerView
    private lateinit var tvPlaceholder: TextView

    private lateinit var mainViewModel: MainViewModel

    private var observer : Observer<Resource<GetArticlesResponse>>? = null
    private lateinit var articlesListAdapter: ArticlesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
        setUpRecycler()
        initViewModel()

        if(MainActivity.mListArticles.isEmpty()){
            getArticlesFromServer()
        }
    }

    private fun setupUI(view: View) {
        rvArticles = view.findViewById(R.id.rvArticles)
        tvPlaceholder = view.findViewById(R.id.tvPlaceholder)
    }

    private fun setUpRecycler() {
        rvArticles.setHasFixedSize(true)
        rvArticles.layoutManager = LinearLayoutManager(requireActivity())
        articlesListAdapter = ArticlesListAdapter(requireActivity())
        rvArticles.adapter = articlesListAdapter
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    private fun getArticlesFromServer() {
        if(!Internet.isAvailable(requireActivity())){
            Alert.show(requireActivity(), "Error", requireActivity().resources.getString(R.string.no_internet))
            return
        }

        setupObserver()
    }

    private fun setupObserver() {
        Log.e(TAG, "setupObserver: ")
        observer = Observer<Resource<GetArticlesResponse>> {
            when(it.status){
                Status.LOADING -> {
                    Loading.showLoading(requireActivity(), false)
                }
                Status.SUCCESS -> {
                    Loading.hideLoading()
                    tvPlaceholder.visibility = View.GONE
                    it.data?.let { articles -> renderList(articles.results) }
                }
                Status.NO_DATA -> {
                    Loading.hideLoading()
                    tvPlaceholder.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Loading.hideLoading()
                    Alert.show(requireActivity(), "Error", it.message)
                }
            }
        }
        mainViewModel.getArticles()?.observe(requireActivity(), observer!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(articles: List<ResultsItem?>?) {
        Log.e(TAG, "renderList: ")
        MainActivity.mListArticles.clear()
        MainActivity.mListArticles.addAll(articles!!)
        articlesListAdapter.notifyDataSetChanged()
    }

    private fun removeObserver() {
        Log.e(TAG, "removeObserver: ")
        if(observer!=null){
            mainViewModel.getArticles()?.removeObserver(observer!!)
        }
    }


}