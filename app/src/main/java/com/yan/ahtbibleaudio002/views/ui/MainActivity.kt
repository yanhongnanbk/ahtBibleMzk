package com.yan.ahtbibleaudio002.views.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yan.ahtbibleaudio002.R
import com.yan.ahtbibleaudio002.models.AudioItem
import com.yan.ahtbibleaudio002.models.NaviModel
import com.yan.ahtbibleaudio002.remote.AudioServiceInterface
import com.yan.ahtbibleaudio002.repositories.AudioRepository
import com.yan.ahtbibleaudio002.viewmodels.MainActivityViewModel
import com.yan.ahtbibleaudio002.views.adapters.AudioAdapter
import com.yan.ahtbibleaudio002.views.adapters.ClickListener
import com.yan.ahtbibleaudio002.views.adapters.NavigationAdapter
import com.yan.ahtbibleaudio002.views.adapters.RecyclerTouchListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main_activity.view.*

const val MAIN_ACTIVITY = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationAdapter
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    var navigationItems: ArrayList<NaviModel> = arrayListOf(NaviModel("All Songs"))
    var stringNavigationArray: ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)

        // Set the toolbar
        setSupportActionBar(audio_main_toolbar)
        /**ViewModels*/
        setupViewModels()
        /***/
        mainActivityViewModel.getAllAudios() { result ->

            result.forEach {
                stringNavigationArray.add(it.category.toString())
            }

//            Log.d(MAIN_ACTIVITY, stringNavigationArray.distinct().toString())
            stringNavigationArray.distinct().forEach {
                navigationItems.add(NaviModel(it))
            }

            Log.d(MAIN_ACTIVITY, navigationItems.size.toString())

            val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(this, R.drawable.rv_divider)?.let { itemDecor.setDrawable(it) }
            navigation_rv.addItemDecoration(itemDecor)
            navigation_rv.layoutManager = LinearLayoutManager(this)
            navigation_rv.setHasFixedSize(true)

            // Add Item Touch Listener
            navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
                override fun onClick(view: View, position: Int) {
                    /***/
                    if(position==0){
                        supportActionBar?.title = "All Songs"
                        val bundle = Bundle()
                        bundle.putString("fragmentName", "All Songs")
                        val allSongsFragment = MainActivityFragment()
                        allSongsFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.activity_main_content_id, allSongsFragment).commit()
                    }else{
                        supportActionBar?.title = navigationItems[position].title
                        val bundle = Bundle()
                        bundle.putString("fragmentName", navigationItems[position].title)
                        val categoryFragment = MainActivityFragment()
                        categoryFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.activity_main_content_id, categoryFragment).commit()
                    }

                   /***/
                    /***/
                    drawerLayout.closeDrawer(GravityCompat.START)
//                Handler(Looper.getMainLooper()).postDelayed({
//                    drawerLayout.closeDrawer(GravityCompat.START)
//                }, 200)
                    /***/
                }
            }))
            /**Update Adapter*/
            updateAdapter(0)
            /**Set 'Home' as the default fragment when the app starts*/
            supportActionBar?.title = "All Songs"
            val bundle = Bundle()
            bundle.putString("fragmentName", "All Songs")
            val allSongsFragment = MainActivityFragment()
            allSongsFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_content_id, allSongsFragment).commit()

            /**Close the soft keyboard when you open or close the Drawer*/
            val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                audio_main_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            ) {
                override fun onDrawerClosed(drawerView: View) {
                    // Triggered once the drawer closes
                    super.onDrawerClosed(drawerView)
                    try {
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }

                override fun onDrawerOpened(drawerView: View) {
                    // Triggered once the drawer opens
                    super.onDrawerOpened(drawerView)
                    try {
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }

            }
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            // Set background of Drawer
            navigation_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))

        }
        /***/
//        Log.d(MAIN_ACTIVITY, navigationItems.size.toString())
        // Setup Recyclerview's Layout

        /***/
    }

    /**onBackPressed*/
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Go to the previous fragment
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

    /**Update Adapter*/
    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationAdapter(navigationItems, highlightItemPos)
        navigation_rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    /**SetupViewmodels*/
    private fun setupViewModels() {
        val service = AudioServiceInterface.instance
        mainActivityViewModel.audioRepository = AudioRepository(service)
    }

}
