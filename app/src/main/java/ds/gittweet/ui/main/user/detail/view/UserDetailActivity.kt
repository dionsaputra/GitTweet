package ds.gittweet.ui.main.user.detail.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import ds.gittweet.R
import ds.gittweet.utility.dpToPx
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.content_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    private val TAG = UserDetailActivity::class.java.simpleName

    companion object {
        private const val USER_AVATAR_MAX_SIZE = 96F
        private const val USER_AVATAR_MIN_SIZE = 12F
        private const val TRANSPARENT_HOLDER_MAX_SIZE = 56F
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        userDetailAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->
            runOnUiThread {
                userDetailAvatar.layoutParams.height = avatarScale(p1).toInt()
                userDetailAvatar.layoutParams.width = avatarScale(p1).toInt()
                userDetailAvatar.requestLayout()
            }
        })
    }

    private fun avatarScale(collapsingOffset: Int): Float {
        val diffUserAvatar = dpToPx(USER_AVATAR_MAX_SIZE) - dpToPx(USER_AVATAR_MIN_SIZE)
        val maxCollapsingOffset = dpToPx(userDetailAppBar.height.toFloat() - userDetailToolbar.height.toFloat())
        return dpToPx(USER_AVATAR_MAX_SIZE) + (diffUserAvatar*collapsingOffset)/maxCollapsingOffset
    }
}

/*

f(0) = 56
f(156) = 0


f(0) = userAvatarMaxSize
f(userAppBar) = userAvatarMinSize

f(x) = userAvatarMaxSize - (userAvatarMaxSize-user
 */