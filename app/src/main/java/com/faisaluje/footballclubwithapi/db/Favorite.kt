package com.faisaluje.footballclubwithapi.db

data class Favorite(val id: Long?, val teamId: String?, val teamName: String?, val teamBadge: String?){

    companion object {
        const val TABLE_FAVORITE = "TABLE_FAVORITE"
        const val ID = "ID_"
        const val TEAM_ID = "TEAM_ID"
        const val TEAM_NAME = "TEAM_NAME"
        const val TEAM_BADGE = "TEAM_BADGE"
    }
}