package me.ayushig.viewmodel.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.ayushig.viewmodel.R
import me.ayushig.viewmodel.base.BaseActivity
import me.ayushig.viewmodel.base.BaseApplication
import me.ayushig.viewmodel.databinding.ActivityMainBinding
import me.ayushig.viewmodel.ui.detail.DetailsFragment
import me.ayushig.viewmodel.ui.list.MovieListFragment
import me.ayushig.viewmodel.util.ViewModelFactory
import javax.inject.Inject

/**
 * Launcher activity
 */
class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private lateinit var sharedViewModel: MainActivitySharedViewModel

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    private lateinit var mainActivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.getApplicationComponent().inject(this)

        createViewModel()

        initUI(savedInstanceState)
    }

    private fun initUI(savedInstanceState: Bundle?) {
        mainActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setToolbar(getString(R.string.app_name), false)
        if (savedInstanceState == null)
            initMovieListFragment()
    }

    private fun initMovieListFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.sliding_in_left,
            R.anim.sliding_out_right,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
            .add(R.id.screenContainer, MovieListFragment())
            .addToBackStack(MOVIE_FRAGMENT)
            .commit()
    }

    private fun createViewModel() {
        sharedViewModel =
            ViewModelProvider(this, viewmodelFactory)
                .get(MainActivitySharedViewModel::class.java)

        implementObservables()
    }

    private fun implementObservables() {
        sharedViewModel.openDetailsFragment.observe(this, Observer { openDetailsFragment() })
    }

    private fun openDetailsFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.sliding_in_left,
                R.anim.sliding_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            ).replace(
                R.id.screenContainer,
                DetailsFragment()
            ).addToBackStack(DETAILS_FRAGMENT)
            .commit()
    }

    fun setToolbar(title: String, showHome: Boolean) {
        setSupportActionBar(mainActivityMainBinding.toolbar)
        mainActivityMainBinding.toolbarTitle.text = title
        supportActionBar?.setDisplayHomeAsUpEnabled(showHome)
        supportActionBar?.setDisplayShowHomeEnabled(showHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mainActivityMainBinding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }

    companion object {
        const val MOVIE_FRAGMENT = "Movies Fragment"
        const val DETAILS_FRAGMENT = "Details Fragment"

    }
}
