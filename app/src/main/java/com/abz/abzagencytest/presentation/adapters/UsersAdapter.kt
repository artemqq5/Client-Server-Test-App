package com.abz.abzagencytest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abz.abzagencytest.databinding.ItemUserBinding
import com.abz.abzagencytest.domain.api.model.users.User
import com.squareup.picasso.Picasso

class UsersAdapter(
    private val userList: MutableList<User?> = mutableListOf(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VH && userList[position] != null) {
            val user = userList[position]!!
            holder.binding.userName.text = user.name
            holder.binding.userEmail.text = user.email
            holder.binding.userPhone.text = user.phone
            holder.binding.userPosition.text = user.position
            Picasso.get().load(user.photo!!).into(holder.binding.profileImage)
        }
    }

    fun addUsers(newUsers: List<User?>?) {
        val startPosition = userList.size
        userList.addAll(newUsers!!)
        notifyItemRangeInserted(startPosition, newUsers.size)
    }

    class VH(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}