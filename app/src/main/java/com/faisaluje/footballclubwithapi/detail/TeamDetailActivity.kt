package com.faisaluje.footballclubwithapi.detail

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.faisaluje.footballclubwithapi.R
import com.faisaluje.footballclubwithapi.R.color.colorAccent
import com.faisaluje.footballclubwithapi.R.color.primary_text_default_material_light
import com.faisaluje.footballclubwithapi.api.ApiRepository
import com.faisaluje.footballclubwithapi.db.Favorite
import com.faisaluje.footballclubwithapi.db.database
import com.faisaluje.footballclubwithapi.model.Team
import com.faisaluje.footballclubwithapi.util.invisible
import com.faisaluje.footballclubwithapi.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamDetailActivity: AppCompatActivity(), TeamDetailView{
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private lateinit var id: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            teamBadge = imageView().lparams(height = dip(75))

                            teamName = textView{
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, colorAccent)
                            }.lparams{
                                topMargin = dip(5)
                            }

                            teamFormedYear = textView{
                                this.gravity = Gravity.CENTER
                            }

                            teamStadium = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, primary_text_default_material_light)
                            }

                            teamDescription = textView().lparams{
                                topMargin = dip(20)
                            }
                        }
                        progressBar = progressBar().lparams{
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        id = intent.getStringExtra("id")
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)

        swipeRefresh.onRefresh {
            presenter.getTeamDetail(id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                addToFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<Team>) {
        teams = data[0]
        swipeRefresh.isRefreshing = false

        Picasso.get().load(teams.teamBadge).into(teamBadge)
        teamName.text = teams.teamName
        teamDescription.text = teams.teamDescription
        teamFormedYear.text = teams.teamFormedYear
        teamStadium.text = teams.teamStadium
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.TEAM_ID to teams.teamId,
                        Favorite.TEAM_NAME to teams.teamName,
                        Favorite.TEAM_BADGE to teams.teamBadge)
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }
}