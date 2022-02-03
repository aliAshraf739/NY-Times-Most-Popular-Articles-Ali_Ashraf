package com.ali.ashraf.mvvm.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ali.ashraf.mvvm.R
import com.ali.ashraf.mvvm.data.model.article.ResultsItem

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object{
        var mListArticles:ArrayList<ResultsItem?> = ArrayList()
    }

}
