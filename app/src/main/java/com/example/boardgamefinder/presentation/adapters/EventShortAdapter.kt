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
import com.example.boardgamefinder.domain.models.Event
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class EventShortAdapter(private val items: List<Event>) : RecyclerView.Adapter<EventShortAdapter.EventViewHolder>(){
    private var context: Context? = null

    override fun getItemViewType(position: Int): Int {
        return R.layout.event_short_raw_object
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
            //holder.date.text = date
            holder.eventName.text = title
            holder.place.text = locationShort
        }
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.time)
        val eventName: TextView = itemView.findViewById(R.id.event_name)
        val place: TextView = itemView.findViewById(R.id.place)

        val avatar: ImageView = itemView.findViewById(R.id.avatar)
        val eventImage: ImageView = itemView.findViewById(R.id.event_image)
    }
}