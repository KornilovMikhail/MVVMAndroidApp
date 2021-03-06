package com.github.kornilovmikhail.mvvmandroidproject.ui.fragment.newsdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.kornilovmikhail.mvvmandroidproject.App
import com.github.kornilovmikhail.mvvmandroidproject.di.screens.component.DaggerNewsComponent
import com.github.kornilovmikhail.mvvmandroidproject.di.screens.module.NewsModule
import com.github.kornilovmikhail.mvvmandroidproject.di.screens.module.ViewModelModule
import com.github.kornilovmikhail.mvvmandroidproject.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_news_details.*
import javax.inject.Inject
import com.github.kornilovmikhail.mvvmandroidproject.R
import com.github.kornilovmikhail.mvvmandroidproject.ui.fragment.newslist.NewsListFragment.Companion.KEY_NEWS_ID
import com.squareup.picasso.Picasso

class NewsDetailFragment : Fragment() {
    private var newsDetailViewModel: NewsDetailViewModel? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<NewsDetailViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerNewsComponent.builder()
            .appComponent(App.getAppComponents())
            .newsModule(NewsModule())
            .viewModelModule(ViewModelModule())
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_news_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsDetailViewModel::class.java)
        newsDetailViewModel?.getNews(arguments?.get(KEY_NEWS_ID) as Int)
        observeNewsDetailData()
        observeInProgress()
        observeIsSuccess()
    }

    private fun observeNewsDetailData() {
        newsDetailViewModel?.newsLiveData?.observe(this, Observer {
            tv_detail_description.text = it.description
            Picasso.get()
                .load(it.urlToImage)
                .into(iv_details_image)
        })
    }

    private fun observeInProgress() {
        newsDetailViewModel?.inProgressLiveData?.observe(this, Observer {
            it?.let {
                details_progressBar.visibility =
                    if (it) View.VISIBLE else View.GONE
            }
        })
    }

    private fun observeIsSuccess() {
        newsDetailViewModel?.isSuccessLiveData?.observe(this, Observer {
            makeToast(
                if (it) {
                    getString(R.string.server_load_success)
                } else {
                    getString(R.string.server_load_error)
                }
            )
        })
    }

    private fun makeToast(text: String) =
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
