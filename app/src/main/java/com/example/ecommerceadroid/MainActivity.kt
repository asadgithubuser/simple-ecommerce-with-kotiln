package com.example.ecommerceadroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.ecommerceadroid.Fragments.AddProduct
import com.example.ecommerceadroid.Fragments.HomeFragment
import com.example.ecommerceadroid.Fragments.SellHistoryFragment
import com.example.ecommerceadroid.Fragments.SellProductFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var seletedFragment: Fragment? = null
    private var drawerToggle: ActionBarDrawerToggle? = null
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ id ->
        when(id.itemId){
            R.id.home -> {
                selectedFregment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.sellProduct -> {
                selectedFregment(SellProductFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.AddProduct -> {
                selectedFregment(AddProduct())
                return@OnNavigationItemSelectedListener true
            }
            R.id.history -> {
                selectedFregment(SellHistoryFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        if(seletedFragment != null){
            supportFragmentManager.beginTransaction().replace(R.id.main_frame_container, seletedFragment!!).commit()
        }
        false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNav : BottomNavigationView = findViewById(R.id.bottom_nav_menu)
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        selectedFregment(HomeFragment())

        drawerToggle = ActionBarDrawerToggle(this, drawer_layout, top_toolbar, R.string.dOpen, R.string.dClose)
        drawer_layout.bringToFront()
        drawer_layout.addDrawerListener(drawerToggle!!)
        drawerToggle!!.syncState()

        var drawer_nav_view: NavigationView = findViewById(R.id.drawer_nav)
        drawer_nav_view.setNavigationItemSelectedListener(this)
        drawer_nav_view.itemIconTintList = null


    }

    private fun selectedFregment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.drhome -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frame_container, HomeFragment()).commit()
            }
            R.id.draddProust -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frame_container, AddProduct()).commit()
            }
            R.id.drhistory -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frame_container, SellHistoryFragment()).commit()
            }
            R.id.drlogout -> {
                FirebaseAuth.getInstance().signOut()

                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }




}