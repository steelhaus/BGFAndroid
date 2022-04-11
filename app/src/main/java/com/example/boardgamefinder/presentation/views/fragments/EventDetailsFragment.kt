package com.example.boardgamefinder.presentation.views.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.boardgamefinder.R
import com.example.boardgamefinder.databinding.FragmentEventDetailsBinding
import com.example.boardgamefinder.domain.models.Event
import com.example.boardgamefinder.presentation.views.activities.MainActivity
import com.google.android.material.chip.Chip


class EventDetailsFragment(val event: Event) : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = event.title
        binding.location.text = event.locationShort
        binding.date.text = event.date
        //ToDo binding.description.text = event

        // set image
        Glide.with(this)
            .load(event.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.color.dark_gray)
            .transform(CenterCrop(), RoundedCorners(50))
            .into(binding.imageView)

        // set subscription button state
        if(event.subscriptionStatus != Event.SubscriptionStatus.NOT_SUBMITTED){
            //ToDo to resources
            binding.joinButton.text = "LEAVE"

            //ToDo binding.joinButton.style(R.style.Widget_BoardGameFinder_Button_SmallOutlinedButtonOnPrimary)
            //Paris.style(binding.joinButton).apply(R.style.Widget_BoardGameFinder_Button_SmallOutlinedButtonOnPrimary);
        }

        // set description scrollable
        binding.description.movementMethod = ScrollingMovementMethod()

        event.tags?.let { tags ->
            val uniqueTags = tags.distinctBy { it.title }
            // setting tags
            for (i in uniqueTags) {
                val chip = Chip(context)
                chip.text = i.title
                binding.tags.addView(chip)
            }
        }

        binding.membersButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(EventMembersFragment(event))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}