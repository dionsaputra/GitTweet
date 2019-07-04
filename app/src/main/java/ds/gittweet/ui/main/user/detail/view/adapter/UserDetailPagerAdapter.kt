package ds.gittweet.ui.main.user.detail.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ds.gittweet.R
import ds.gittweet.ui.main.user.detail.view.fragment.EventsFragment
import ds.gittweet.ui.main.user.detail.view.fragment.GistsFragment
import ds.gittweet.ui.main.user.detail.view.fragment.RepoFragment
import ds.gittweet.ui.main.user.detail.view.fragment.StarsFagment

class UserDetailPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val userDetailFragments = listOf<Fragment>(
        RepoFragment(), StarsFagment(), EventsFragment(), GistsFragment()
    )

    override fun getItem(position: Int): Fragment {
        return userDetailFragments[position]
    }

    override fun getCount(): Int {
        return userDetailFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? = with(context) {
        return when (position) {
            0 -> getString(R.string.repo)
            1 -> getString(R.string.stars)
            2 -> getString(R.string.events)
            3 -> getString(R.string.gists)
            else -> ""
        }
    }
}