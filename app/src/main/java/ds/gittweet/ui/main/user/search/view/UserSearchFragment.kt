package ds.gittweet.ui.main.user.search.view

import android.content.Intent
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
import ds.gittweet.GitTweet
import ds.gittweet.R
import ds.gittweet.data.local.entity.UserEntity
import ds.gittweet.data.remote.response.UserResponse
import ds.gittweet.ui.main.injection.DaggerMainComponent
import ds.gittweet.ui.main.user.detail.view.UserDetailActivity
import ds.gittweet.ui.main.user.search.presenter.UserSearchPresenter
import ds.gittweet.ui.main.user.search.adapter.UserLocalAdapter
import ds.gittweet.ui.main.user.search.adapter.UserRemoteAdapter
import ds.gittweet.utility.AppConstant
import kotlinx.android.synthetic.main.dialog_search_user.*
import javax.inject.Inject

class UserSearchFragment : DialogFragment(), UserSearchView {

    @Inject
    lateinit var presenter: UserSearchPresenter

    @Inject
    lateinit var viewState: UserSearchViewState

    private lateinit var userLocalAdapter: UserLocalAdapter
    private lateinit var userRemoteAdapter: UserRemoteAdapter
    private lateinit var clearRecentConfirmDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.AppTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DaggerMainComponent.builder()
            .appComponent(GitTweet.appComponent)
            .build()
            .inject(this)

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
        clearRecentConfirmDialog()
        setupRecentRecycler()
        setupSearchRecycler()
    }

    override fun showClearSearchButton(isShow: Boolean) {
        buttonClearSearch.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

    override fun clearSearchQuery() {
        editSearchInput.text.clear()
    }

    override fun showClearRecentConfirmation(isShow: Boolean) {
        if (isShow) clearRecentConfirmDialog.show() else clearRecentConfirmDialog.dismiss()
    }

    override fun showGroupRecent(isShow: Boolean) {
        groupRecent.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun showGroupSearch(isShow: Boolean) {
        groupSearch.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun showRecentEmpty() {}

    override fun showRecentResult(users: List<UserEntity>) {
        userLocalAdapter.swapData(users)
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSearchEmpty() {}

    override fun showSearchResult(userResponses: List<UserResponse>) {
        if (viewState.isFirstRemoteSearch) {
            userRemoteAdapter.swapData(userResponses)
        } else {
            userRemoteAdapter.addData(userResponses)
        }
    }

    override fun checkEndOfSearchData(totalCount: Int) {
        viewState.isSearchReachEndOfPage = (userRemoteAdapter.itemCount == totalCount)
    }

    override fun showLoadMoreSearchLoading(isShow: Boolean) {
        if (isShow) {
            progressSearchLoadMore.visibility = View.VISIBLE
            viewState.isLoadingMoreSearchResult = true
        } else {
            progressSearchLoadMore.visibility = View.GONE
            viewState.isLoadingMoreSearchResult = false
        }
    }

    override fun closeView() {
        presenter.detachView()
        dismiss()
    }

    override fun getState(): UserSearchViewState {
        return viewState
    }

    override fun showUserDetail(login: String) {
        val intent = Intent(context, UserDetailActivity::class.java)
        intent.putExtra(AppConstant.USER_LOGIN_ARG, login)
        startActivity(intent)
    }

    private fun setupToolbar() {
        toolbarSearchUser.setNavigationOnClickListener { presenter.onBackNavigationClick() }
    }

    private fun setupEditSearchInput() {
        editSearchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.onSearchQueryChange(s.toString())
                viewState.lastSearchQuery = s.toString()
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

    private fun clearRecentConfirmDialog() {
        context?.let {
            clearRecentConfirmDialog = AlertDialog.Builder(it)
                .setMessage("Clear recent searches?")
                .setPositiveButton("Clear") { _, _ -> presenter.onAcceptClearRecent() }
                .setNegativeButton("Cancel") { _, _ -> presenter.onCancelClearRecent() }
                .create()
        }
    }

    private fun setupRecentRecycler() {
        userLocalAdapter = UserLocalAdapter(mutableListOf())

        recyclerRecent.apply {
            adapter = userLocalAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupSearchRecycler() {
        setupSearchAdapter()
        setupSearchScrollListener()
    }

    private fun setupSearchAdapter() {
        userRemoteAdapter = UserRemoteAdapter(mutableListOf()) { presenter.onRemoteUserClick(it) }

        recyclerSearch.apply {
            adapter = userRemoteAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupSearchScrollListener() {
        recyclerSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isSearchRecyclerReachBound() && viewState.isSearchRecyclerAbleToLoad()) {
                    presenter.loadMoreSearch()
                }
            }
        })
    }

    private fun isSearchRecyclerReachBound(): Boolean {
        val layoutManager = recyclerSearch.layoutManager as LinearLayoutManager
        val childCount = layoutManager.childCount
        val firstVisible = layoutManager.findFirstVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        return childCount + firstVisible + UserSearchViewState.REMOTE_SEARCH_VISIBLE_THRESHOLD >= itemCount
    }
}