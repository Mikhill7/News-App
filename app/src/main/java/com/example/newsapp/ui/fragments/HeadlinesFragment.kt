package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Resource

class HeadlinesFragment : Fragment(R.layout.fragment_headline) {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    private var recyclerHeadlines: RecyclerView? = null
    private var paginationProgressBar: ProgressBar? = null
    private var itemHeadlinesError: CardView? = null
    private var errorText: TextView? = null
    private var retryButton: Button? = null

    private var isError = false
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            // Initialize views
            recyclerHeadlines = view.findViewById(R.id.recyclerHeadlines)
            paginationProgressBar = view.findViewById(R.id.paginationProgressBar)
            itemHeadlinesError = view.findViewById(R.id.itemHeadlinesError)
            errorText = itemHeadlinesError?.findViewById(R.id.errorText)
            retryButton = itemHeadlinesError?.findViewById(R.id.retryButton)

            newsViewModel = (activity as NewsActivity).newsViewModel
            setupHeadlinesRecycler()

            newsAdapter.setOnItemClickListener { article ->
                val bundle = Bundle().apply {
                    putParcelable("article", article)
                }
                findNavController().navigate(
                    R.id.action_headlinesFragment_to_articleFragment,
                    bundle
                )
            }

            newsViewModel.headlines.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success<*> -> {
                        hideProgressBar()
                        hideErrorMessage()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles.toList())
                            val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
                            isLastPage = newsViewModel.headlinesPage == totalPages
                            if (isLastPage) {
                                recyclerHeadlines?.setPadding(0, 0, 0, 0)
                            }
                        }
                    }
                    is Resource.Error<*> -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG).show()
                            showErrorMessage(message)
                        }
                    }
                    is Resource.Loading<*> -> {
                        showProgressBar()
                    }
                }
            }

            retryButton?.setOnClickListener {
                newsViewModel.getHeadlines("us")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error initializing fragment: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerHeadlines?.removeOnScrollListener(scrollListener)
        recyclerHeadlines = null
        paginationProgressBar = null
        itemHeadlinesError = null
        errorText = null
        retryButton = null
    }

    private fun hideProgressBar() {
        paginationProgressBar?.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar?.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        itemHeadlinesError?.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemHeadlinesError?.visibility = View.VISIBLE
        errorText?.text = message
        isError = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoError = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNoError && isNotLoadingAndNotLastPage && isAtLastItem &&
                    isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                newsViewModel.getHeadlines("us")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupHeadlinesRecycler() {
        newsAdapter = NewsAdapter()
        recyclerHeadlines?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HeadlinesFragment.scrollListener)
        }
    }
}