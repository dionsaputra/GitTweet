package ds.gittweet.ui.main.search.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ds.gittweet.R
import ds.gittweet.data.remote.response.UserResponse
import ds.gittweet.ui.main.search.presenter.SearchPresenter
import ds.gittweet.ui.main.user.search.view.UserSearchFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), SearchMvpView {

    private val presenter = SearchPresenter()

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
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_setting -> Toast.makeText(context, "Setting in construction", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun showErrorMessage(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showEmptyUser() {
        Log.d("TAG", "Empty")
    }

    override fun showInitialPageUsers(userResponses: List<UserResponse>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNextPageUsers(userResponses: List<UserResponse>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRefresh(refreshState: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadMore(loadingMoreState: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReachEndOfData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTotalItem(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setupView() {
        presenter.attachView(this)
        setupToolbar()
    }

    private fun setupListener() {
        setupNavigationSearch()
    }

    private fun setupNavigationSearch() {
        searchNavigationToolbar.setOnClickListener {
            val searchUserFragment = UserSearchFragment()
            searchUserFragment.show(fragmentManager, UserSearchFragment::class.java.simpleName)
        }
    }
    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbarSearchUser)
    }

}
