package ds.githubclient.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ds.githubclient.R
import ds.githubclient.ui.main.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // TODO: inject later
    lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()
        setupTabAndViewPager()
    }

    // TODO: implement later
    private fun injectDependencies() {

    }

    private fun setupTabAndViewPager() {
        vpMain.adapter = MainAdapter(supportFragmentManager)
        tlMain.setupWithViewPager(vpMain)
    }
}
