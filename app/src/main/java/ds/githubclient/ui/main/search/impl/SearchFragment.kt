package ds.githubclient.ui.main.search.impl

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ds.githubclient.R
import ds.githubclient.data.network.model.User
import ds.githubclient.ui.main.search.adapter.SearchUserAdapter
import ds.githubclient.ui.main.search.contract.SearchMvpPresenter
import ds.githubclient.ui.main.search.contract.SearchMvpView
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment(), SearchMvpView {

    private val presenter: SearchMvpPresenter = SearchPresenter()
    private val searchUserAdapter = SearchUserAdapter(emptyList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    private fun setupView(view: View) {
        presenter.attachView(this)
        view.rvSearchUser.apply {
            adapter = searchUserAdapter
            layoutManager = LinearLayoutManager(context)
        }

        presenter.getAllUsers()
    }

    override fun showAllUser(users: List<User>) {
        searchUserAdapter.swapData(users)
    }

    override fun showErrorMessage(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showEmptyUser() {
        Log.d("TAG", "Empty")
    }
}
