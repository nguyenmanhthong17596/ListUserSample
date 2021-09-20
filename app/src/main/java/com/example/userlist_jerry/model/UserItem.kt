package com.example.userlist_jerry.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.userlist_jerry.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class UserItem(
    private val userDataModel: UserDataModel,
    private val onItemClickListener: OnItemClickListener
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val userAvaImage: ImageView = viewHolder.itemView.findViewById(R.id.userAva)
        val userName: TextView = viewHolder.itemView.findViewById(R.id.textUserLogin)
        val userUrl: TextView = viewHolder.itemView.findViewById(R.id.textUserHtmlUrl)
        val userView: ConstraintLayout = viewHolder.itemView.findViewById(R.id.userView)

        userName.text = userDataModel.login
        userUrl.text = userDataModel.html_url
        Glide.with(userAvaImage.context)
            .load(userDataModel.avatar_url)
            .centerCrop()
            .into(userAvaImage)
        userView.setOnClickListener {
            onItemClickListener.onItemClick(userDataModel)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_user_item
    }

    interface OnItemClickListener {
        fun onItemClick(user: UserDataModel)
    }
}