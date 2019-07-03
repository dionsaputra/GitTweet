package ds.gittweet.ui.main.user.detail.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import ds.gittweet.GitTweet
import ds.gittweet.R
import ds.gittweet.data.remote.response.UserResponse
import ds.gittweet.ui.main.injection.DaggerMainComponent
import ds.gittweet.ui.main.user.detail.presenter.UserDetailPresenter
import ds.gittweet.utility.dpToPx
import kotlinx.android.synthetic.main.activity_user_detail.*
import javax.inject.Inject
import kotlin.math.abs

class UserDetailActivity : AppCompatActivity(), UserDetailView {

    @Inject
    lateinit var presenter: UserDetailPresenter

    private val TAG = UserDetailActivity::class.java.simpleName

    companion object {
        private const val USER_AVATAR_MAX_SIZE = 96F
        private const val USER_AVATAR_MIN_SIZE = 12F
        private const val TRANSPARENT_HOLDER_MAX_SIZE = 56F
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        DaggerMainComponent.builder()
            .appComponent(GitTweet.appComponent)
            .build()
            .inject(this)

        presenter.attachView(this)
    }

    override fun initView() {
        setupToolbar()
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setupToolbar() {
        setupToolbarListener()
    }

    private fun setupToolbarListener() {
        userDetailAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            setAvatarSize(offset)
            setToolbarTitleVisibility(offset)
        })
    }

    private fun setAvatarSize(collapsingOffset: Int) {
        val diffUserAvatar = dpToPx(USER_AVATAR_MAX_SIZE) - dpToPx(USER_AVATAR_MIN_SIZE)
        val maxCollapsingOffset = dpToPx(userDetailAppBar.height.toFloat() - userDetailToolbar.height.toFloat())
        val avatarSize = dpToPx(USER_AVATAR_MAX_SIZE) + (diffUserAvatar * collapsingOffset) / maxCollapsingOffset

        userDetailAvatar.layoutParams.height = avatarSize.toInt()
        userDetailAvatar.layoutParams.width = avatarSize.toInt()
        userDetailAvatar.requestLayout()
    }

    private fun isFullyCollapsed(collapsingOffset: Int): Boolean {
        val maxCollapsingOffset = userDetailAppBar.height.toFloat() - userDetailToolbar.height.toFloat()
        return abs(collapsingOffset) >= maxCollapsingOffset.toInt()
    }

    private fun setToolbarTitleVisibility(collapsingOffset: Int) {
        if (isFullyCollapsed(collapsingOffset)) {
            userDetailToolbar.title = "Dion Saputra"
            userDetailToolbar.subtitle = "58 Tweets"
        } else {
            userDetailToolbar.title = ""
            userDetailToolbar.subtitle = ""
        }
    }
}