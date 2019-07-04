package ds.gittweet.ui.main.user.detail.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import ds.gittweet.GitTweet
import ds.gittweet.R
import ds.gittweet.data.remote.response.UserResponse
import ds.gittweet.helper.*
import ds.gittweet.ui.main.injection.DaggerMainComponent
import ds.gittweet.ui.main.user.UserConstant
import ds.gittweet.ui.main.user.detail.presenter.UserDetailPresenter
import ds.gittweet.ui.main.user.detail.view.adapter.UserDetailPagerAdapter
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.lang.Math.abs
import java.text.SimpleDateFormat
import javax.inject.Inject

class UserDetailActivity : AppCompatActivity(), UserDetailView {

    @Inject
    lateinit var presenter: UserDetailPresenter

    @Inject
    lateinit var viewState: UserDetailViewState

    private val TAG = UserDetailActivity::class.java.simpleName

    companion object {
        private const val USER_AVATAR_MAX_SIZE = 96F
        private const val USER_AVATAR_MIN_SIZE = 24F
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        DaggerMainComponent.builder()
            .appComponent(GitTweet.appComponent)
            .build()
            .inject(this)

        viewState.userLogin = intent.getStringExtra(UserConstant.USER_LOGIN_ARG)
        presenter.attachView(this)
    }

    override fun initView() {
        setupToolbar()
    }

    override fun onResume() {
        super.onResume()
        setupTabLayoutAndViewPager()
    }

    override fun closeView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserEdit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserAvatar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserDetail(user: UserResponse) {
        Glide.with(this).load(user.avatarUrl).placeholder(R.drawable.user_placeholder).into(userDetailAvatar)
        userDetailName.text = user.name
        userDetailLogin.text = user.login
        userDetailBio.text = user.bio
        userDetailAddress.text = user.location
        userDetailJoined.text = SimpleDateFormat("MMM dd, yyyy").format(user.createdAt)
        userDetailFollowingCounter.text = user.following.toString()
        userDetailFollowersCounter.text = user.followers.toString()
    }

    private fun setupToolbar() {
        setupToolbarListener()
    }

    override fun showLoading(isLoading: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorResponse(it: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getState(): UserDetailViewState {
        return viewState
    }

    private fun setupToolbarListener() {
        userDetailAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            setAvatarSize(offset)
            setToolbarTitleVisibility(offset)
        })
    }

    private fun setAvatarSize(collapsingOffset: Int) {
        val diffUserAvatar = dpToPx(USER_AVATAR_MAX_SIZE) - dpToPx(USER_AVATAR_MIN_SIZE)
        val maxCollapsingOffset = userDetailAppBar.height.toFloat() - userDetailToolbar.height.toFloat()
        val avatarSize = dpToPx(USER_AVATAR_MAX_SIZE) + (diffUserAvatar * collapsingOffset) / maxCollapsingOffset

        userDetailAvatar.layoutParams.height = avatarSize.toInt()
        userDetailAvatar.layoutParams.width = avatarSize.toInt()
        userDetailAvatar.requestLayout()
    }

    private fun isAvatarReachMinHeight(collapsingOffset: Int): Boolean {
        return userDetailAvatar.layoutParams.height <= USER_AVATAR_MIN_SIZE
    }

    private fun setToolbarTitleVisibility(collapsingOffset: Int) {
        if (abs(collapsingOffset) > pxToDp(userDetailAppBar.height.toFloat() - userDetailToolbar.height.toFloat())){
            userDetailToolbar.title = "Dion Saputra"
            userDetailToolbar.subtitle = "58 Tweets"
        } else {
            userDetailToolbar.title = ""
            userDetailToolbar.subtitle = ""
        }
    }

    private fun setupTabLayoutAndViewPager() {
        val userDetailToolbarHeight = userDetailToolbar.layoutParams.height.toFloat()
        val userDetailTabHeight = userDetailTab.layoutParams.height.toFloat()
        Log.d(TAG, getStatusBarHeight().toString())
        val userDetailPagerHeight =
            getScreenHeight() - dpToPx(userDetailToolbarHeight + userDetailTabHeight - getStatusBarHeight())

        userDetailPager.adapter = UserDetailPagerAdapter(this, supportFragmentManager)
        userDetailPager.layoutParams.height = userDetailPagerHeight.toInt()
        userDetailTab.setupWithViewPager(userDetailPager)
    }
}