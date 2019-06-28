package ds.githubclient.ui.main.searchuser

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ds.githubclient.R
import ds.githubclient.data.remote.response.UserResponse
import kotlinx.android.synthetic.main.dialog_search_user.*

class SearchUserFragment() : DialogFragment(), SearchUserView {

    companion object {
        const val VISIBLE_THRESHOLD = 5
    }

    private val presenter = SearchUserPresenter()
    private val recentAdapter = SearchUserAdapter(mutableListOf(), true)
    private val searchAdapter = SearchUserAdapter(mutableListOf(), false) {
        presenter.onSearchItemClick(it)
    }

    private var lastSearchQuery = ""
    private var isLoadingMoreSearchResult = false
    private var isSearchReachEndOfPage = false

    lateinit var clearRecentConfirmationDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_search_user, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun initView() {
        setupToolbar()
        setupEditSearchInput()
        setupButtonCloseSearch()
        setupButtonClearRecent()
        setupClearRecentConfirmation()
        setupRecentAdapter()
        setupSearchRecycler()
    }

    override fun showClearSearchButton(isShow: Boolean) {
        buttonClearSearch.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

    override fun clearSearchQuery() {
        editSearchInput.text.clear()
    }

    override fun showClearRecentConfirmation(isShow: Boolean) {
        if (isShow) clearRecentConfirmationDialog.show() else clearRecentConfirmationDialog.dismiss()
    }

    override fun showGroupRecent(isShow: Boolean) {
        groupRecent.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun showGroupSearch(isShow: Boolean) {
        groupSearch.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun showRecentEmpty() {}

    override fun showRecentResult(userResponses: List<UserResponse>) {
        recentAdapter.swapData(userResponses)
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSearchEmpty() {}

    override fun showSearchResult(userResponses: List<UserResponse>, isFirstPage: Boolean) {
        if (isFirstPage) searchAdapter.swapData(userResponses) else searchAdapter.addData(userResponses)
    }

    override fun checkEndOfSearchData(totalCount: Int) {
        isSearchReachEndOfPage = (searchAdapter.itemCount == totalCount)
    }

    override fun showLoadMoreSearchLoading(isShow: Boolean) {
        if (isShow) {
            progressSearchLoadMore.visibility = View.VISIBLE
            isLoadingMoreSearchResult = true
        } else {
            progressSearchLoadMore.visibility = View.GONE
            isLoadingMoreSearchResult = false
        }
    }

    override fun closeView() {
        dismiss()
    }

    private fun setupToolbar() {
        toolbarSearchUser.setNavigationOnClickListener { presenter.onBackNavigationClick() }
    }

    private fun setupEditSearchInput() {
        editSearchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.onSearchQueryChange(s.toString())
                lastSearchQuery = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupButtonCloseSearch() {
        buttonClearSearch.setOnClickListener { presenter.onClearSearch() }
    }

    private fun setupButtonClearRecent() {
        buttonClearRecent.setOnClickListener { presenter.onClearRecent() }
    }

    private fun setupClearRecentConfirmation() {
        context?.let {
            clearRecentConfirmationDialog = AlertDialog.Builder(it)
                .setMessage("Clear recent searches?")
                .setPositiveButton("Clear") { _, _ -> presenter.onAcceptClearRecent() }
                .setNegativeButton("Cancel") { _, _ -> presenter.onCancelClearRecent() }
                .create()
        }
    }

    private fun setupRecentAdapter() {
        recyclerRecent.apply {
            adapter = recentAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupSearchRecycler() {
        setupSearchAdapter()
        setupSearchScrollListener()
    }

    private fun setupSearchAdapter() {
        recyclerSearch.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupSearchScrollListener() {
        recyclerSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerSearch.layoutManager as LinearLayoutManager
                if (isRecyclerSearchAbleToLoad(layoutManager)) {
                    presenter.loadMoreSearch(lastSearchQuery)
                }
            }
        })
    }

    private fun isRecyclerSearchAbleToLoad(layoutManager: LinearLayoutManager): Boolean {
        val childCount = layoutManager.childCount
        val firstVisible = layoutManager.findFirstVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        return !isLoadingMoreSearchResult &&
                childCount + firstVisible + VISIBLE_THRESHOLD >= itemCount &&
                !isSearchReachEndOfPage
    }
}