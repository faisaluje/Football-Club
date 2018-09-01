package com.faisaluje.footballclubwithapi.detail

import com.faisaluje.footballclubwithapi.model.Team

interface TeamDetailView{
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}