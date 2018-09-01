package com.faisaluje.footballclubwithapi.teams

import com.faisaluje.footballclubwithapi.model.TeamResponse
import com.faisaluje.footballclubwithapi.api.ApiRepository
import com.faisaluje.footballclubwithapi.api.TheSportDBApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson){

    fun getTeamList(league: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(league)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}