package ds.githubclient.ui.main.search.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
    private var isLoadingMore = false
    private var isEndOfData = false
    private var searchQuery = EMPTY_SEARCH_QUERY

    companion object {
        private const val EMPTY_SEARCH_QUERY = ""
        private const val PAGINATION_SIZE = 30
        private const val VISIBLE_THRESHOLD = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        setupSearchView(menu)
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
        if (!isLoadingMore) {
            searchUserRefresh.isRefreshing = refreshState
            isEndOfData = false
        }
    }

    override fun onLoadMore(loadingMoreState: Boolean) {
        if (loadingMoreState) {
            searchUserProgress.visibility = View.VISIBLE
        } else {
            searchUserProgress.visibility = View.GONE
        }
        isLoadingMore = !isLoadingMore
    }

    override fun onReachEndOfData() {
        isEndOfData = true
    }

    private fun setupView() {
        presenter.attachView(this)
        setupRecyclerView()

        presenter.getUsers(searchQuery, PAGINATION_SIZE, isStartingPage = true)
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
                    presenter.getUsers(searchQuery, PAGINATION_SIZE, isStartingPage = false)
                }
            }
        })
    }

    private fun setupRefreshListener() {
        searchUserRefresh.setOnRefreshListener {
            presenter.getUsers(searchQuery, PAGINATION_SIZE, isStartingPage = true)
        }
    }

    private fun isAbleToLoad(layoutManager: LinearLayoutManager): Boolean {
        val childCount = layoutManager.childCount
        val firstVisible = layoutManager.findFirstVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        return !isLoadingMore &&
                childCount + firstVisible + VISIBLE_THRESHOLD >= itemCount
                && firstVisible >= 0
                && !isEndOfData
    }

    private fun setupSearchView(menu: Menu?) {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.let {
            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = "Search ${getString(R.string.app_name)}"
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchQuery = query.orEmpty()
                    presenter.getUsers(searchQuery, PAGINATION_SIZE, isStartingPage = true)
                    return true
                }
            })
            searchView.setOnCloseListener {
                searchQuery = EMPTY_SEARCH_QUERY
                presenter.getUsers(searchQuery, PAGINATION_SIZE, isStartingPage = true)
                searchUserRefresh.isRefreshing = true
                false
            }
        }
    }

    override fun getTotalItem(): Int = searchUserAdapter.itemCount
}
