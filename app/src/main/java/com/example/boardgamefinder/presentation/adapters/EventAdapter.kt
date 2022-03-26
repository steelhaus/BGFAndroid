package com.example.boardgamefinder.presentation.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.boardgamefinder.R
import com.example.boardgamefinder.core.MySettings
import com.example.boardgamefinder.domain.models.Event
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class EventAdapter(private val items: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>(){
    private var context: Context? = null

    override fun getItemViewType(position: Int): Int {
        return R.layout.event_raw_object
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(viewType, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EventViewHolder,
        position: Int
    ) {
        with(items[position]){
            holder.username.text = creator.username
            holder.date.text = date
            holder.eventName.text = title
            holder.likes.text = likes.toString()
            holder.place.text = locationShort
            val members = "$visitorsCount/$visitorsLimit"
            holder.members.text = members
        }

        // setting like button into correct state
        holder.likeButton.apply {
            if (items[position].liked) {
                setImageResource(R.drawable.ic_like)
                setColorFilter(ContextCompat.getColor(context!!, R.color.heart))
            } else {
                setImageResource(R.drawable.ic_like_outlined)
                setColorFilter(ContextCompat.getColor(context!!, R.color.dark_gray))
            }
        }

        // setting join button into correct state
        holder.joinButton.apply {
            when (items[position].subscriptionStatus) {
                Event.SubscriptionStatus.NOT_SUBMITTED -> {
                    setText(R.string.join)
                    setTextColor(MySettings.getSecondaryColor(context))
                }
                Event.SubscriptionStatus.REQUESTED -> {
                    setText(R.string.requested)
                    setTextColor(ContextCompat.getColor(context!!, R.color.dark_gray))
                }
                else -> {
                    setText(R.string.leave)
                    setTextColor(ContextCompat.getColor(context!!, R.color.dark_gray))
                }
            }
        }

        val uniqueTags = items[position].tags.distinctBy { it.title }
        // setting tags
        for(i in uniqueTags){
            val chip = Chip(context)
            chip.text = i.title
            holder.tags.addView(chip)
        }
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.time)
        val eventName: TextView = itemView.findViewById(R.id.event_name)
        val likes: TextView = itemView.findViewById(R.id.likes)
        val place: TextView = itemView.findViewById(R.id.place)
        val members: TextView = itemView.findViewById(R.id.members)

        val avatar: ImageView = itemView.findViewById(R.id.avatar)
        val eventImage: ImageView = itemView.findViewById(R.id.event_image)

        val likeButton: ImageButton = itemView.findViewById(R.id.likeButton)
        val joinButton: TextView = itemView.findViewById(R.id.join_button)

        val tags: ChipGroup = itemView.findViewById(R.id.tags)
    }
}