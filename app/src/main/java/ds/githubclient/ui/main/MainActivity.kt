package ds.githubclient.ui.main

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import de.hdodenhof.circleimageview.CircleImageView
import ds.githubclient.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()
        setupView()
        setupListener()
    }

    // TODO: implement later
    private fun injectDependencies() {

    }

    private fun setupView() {
        setupViewPager()
//        setupToolbar()
    }

    private fun setupViewPager() {
        mainViewPager.adapter = MainAdapter(supportFragmentManager)
    }

    private fun setupToolbar() {
//        setSupportActionBar(mainToolbar)
    }

    private fun setupListener() {
        mainBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_search -> mainViewPager.currentItem = 0
            }

            false
        }
    }
}
