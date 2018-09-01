package com.faisaluje.footballclubwithapi.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.faisaluje.footballclubwithapi.R
import com.faisaluje.footballclubwithapi.R.id.favorites
import com.faisaluje.footballclubwithapi.R.id.teams
import com.faisaluje.footballclubwithapi.favorites.FavoriteTeamsFragment
import com.faisaluje.footballclubwithapi.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                teams -> {
                    loadTeamsFragment(savedInstanceState)
                    return@setOnNavigationItemSelectedListener true
                }
                favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true

        }
        bottom_navigation.selectedItemId = teams
    }

    private fun loadTeamsFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamsFragment(), TeamsFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteTeamsFragment(), FavoriteTeamsFragment::class.java.simpleName)
                    .commit()
        }
    }
}
