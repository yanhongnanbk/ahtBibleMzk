package com.yan.ahtbibleaudio002.views.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yan.ahtbibleaudio002.R
import com.yan.ahtbibleaudio002.models.NaviModel
import com.yan.ahtbibleaudio002.views.adapters.ClickListener
import com.yan.ahtbibleaudio002.views.adapters.NavigationAdapter
import com.yan.ahtbibleaudio002.views.adapters.RecyclerTouchListener
import kotlinx.android.synthetic.main.activity_main.*

const val MAIN_ACTIVITY = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationAdapter

    private var items = arrayListOf(
        NaviModel("All Songs"),
        NaviModel("Cat 1"),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)

        // Set the toolbar
        setSupportActionBar(audio_main_toolbar)

        // Setup Recyclerview's Layout
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(this,R.drawable.rv_divider)?.let { itemDecor.setDrawable(it) }
        navigation_rv.addItemDecoration(itemDecor)
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)

        // Add Item Touch Listener
        navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        // # Home Fragment
                        supportActionBar?.setTitle("All Songs")
                        val bundle = Bundle()
                        bundle.putString("fragmentName", "All Songs")
                        val allSongsFragment = MainActivityFragment()
                        allSongsFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.activity_main_content_id, allSongsFragment).commit()
                    }
                    1 -> {
                        // # Music Fragment
                        supportActionBar?.setTitle("Cat 1")
                        val bundle = Bundle()
                        bundle.putString("fragmentName", "Cat 1")
                        val categoryFragment = MainActivityFragment()
                        categoryFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.activity_main_content_id, categoryFragment).commit()
                    }
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    drawerLayout.closeDrawer(GravityCompat.START)
                }, 200)
            }
        }))
        /**Update Adapter*/
        updateAdapter(0)
        /**Set 'Home' as the default fragment when the app starts*/
        supportActionBar?.setTitle("All Songs")
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
        adapter = NavigationAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
