package com.faisaluje.footballclubwithapi.favorites

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.faisaluje.footballclubwithapi.R.id.team_badge
import com.faisaluje.footballclubwithapi.R.id.team_name
import com.faisaluje.footballclubwithapi.db.Favorite
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamsAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit): RecyclerView.Adapter<FavoriteTeamsAdapter.FavoriteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount() = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val teamBadge: ImageView = view.find(team_badge)
        private val teamName: TextView = view.find(team_name)

        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit){
            Picasso.get().load(favorite.teamBadge).into(teamBadge)
            teamName.text = favorite.teamName

            itemView.onClick { listener(favorite) }
        }

    }

    class TeamUI: AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView{
                    id = team_badge
                }.lparams(width = dip(50), height = dip(50))

                textView {
                    id = team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }
            }
        }

    }
}