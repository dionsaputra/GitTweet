package ds.githubclient.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ds.githubclient.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()
        setupView()
    }

    // TODO: implement later
    private fun injectDependencies() {

    }

    private fun setupView() {
        vpMain.adapter = MainAdapter(supportFragmentManager)
        tlMain.setupWithViewPager(vpMain)
    }
}
