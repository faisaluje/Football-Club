package com.faisaluje.footballclubwithapi.teams

import com.faisaluje.footballclubwithapi.model.Team

interface TeamsView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}