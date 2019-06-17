package ds.githubclient.ui.main.search.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ds.githubclient.R
import ds.githubclient.data.network.model.User
import ds.githubclient.ui.main.search.adapter.SearchUserAdapter
import ds.githubclient.ui.main.search.presenter.SearchMvpPresenter
import ds.githubclient.ui.main.search.presenter.SearchPresenter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), SearchMvpView {

    private val presenter: SearchMvpPresenter = SearchPresenter()
    private val searchUserAdapter = SearchUserAdapter(mutableListOf())
    private val VISIBLE_THRESHOLD = 20
    private var isLoadingMore = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
    }

    override fun showInitialPageUsers(users: List<User>) {
        searchUserAdapter.swapData(users)
    }

    override fun showNextPageUsers(users: List<User>) {
        searchUserAdapter.addData(users)
    }

    override fun showErrorMessage(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showEmptyUser() {
        Log.d("TAG", "Empty")
    }

    override fun onRefresh(refreshState: Boolean) {
        searchUserRefresh.isRefreshing = refreshState
    }

    override fun onLoadMore(loadingMoreState: Boolean) {
        if (loadingMoreState) {
            searchUserProgress.visibility = View.VISIBLE
        } else {
            searchUserProgress.visibility = View.GONE
        }
        isLoadingMore = !isLoadingMore
    }

    private fun setupView() {
        presenter.attachView(this)
        setupRecyclerView()

        presenter.loadInitialUsers()
    }

    private fun setupListener() {
        setupScrollListener()
        setupRefreshListener()
    }

    private fun setupRecyclerView() {
        searchUserRecycler.apply {
            adapter = searchUserAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupScrollListener() {
        searchUserRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = searchUserRecycler.layoutManager as LinearLayoutManager

                if (isAbleToLoad(layoutManager)) {
                    presenter.loadMoreUsers()
                }
            }
        })
    }

    private fun setupRefreshListener() {
        searchUserRefresh.setOnRefreshListener {
            presenter.loadInitialUsers()
        }
    }

    private fun isAbleToLoad(layoutManager: LinearLayoutManager): Boolean {
        val childCount = layoutManager.childCount
        val firstVisible = layoutManager.findFirstVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        return !isLoadingMore &&
                childCount + firstVisible + VISIBLE_THRESHOLD >= itemCount
                && firstVisible >= 0
    }
}
