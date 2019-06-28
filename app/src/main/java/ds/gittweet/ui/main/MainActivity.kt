package ds.gittweet.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ds.gittweet.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setupListener()
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
