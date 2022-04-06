package com.example.boardgamefinder.presentation.views.fragments

import android.app.TimePickerDialog
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.boardgamefinder.R
import com.example.boardgamefinder.databinding.FragmentAddBinding
import com.example.boardgamefinder.presentation.viewModels.NewEventViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment for creating a new event tab
 */
internal class NewEventFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val newEventViewModel: NewEventViewModel by viewModels()

    private val hour = 0
    private val minute = 0

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDatePicker()
        setTimePicker()
        setMap()
    }

    private fun setTimePicker(){
        val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            binding.time.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute))
        }

        val timePicker = TimePickerDialog(context, onTimeSetListener, hour, minute, true)

        binding.time.inputType = InputType.TYPE_NULL
        binding.time.keyListener = null
        binding.time.setOnClickListener{
            timePicker.show()
        }
    }

    private fun setDatePicker(){
        // calendar for date picker
        val constraintBuilder = CalendarConstraints.Builder()
        // only dates in the future
        constraintBuilder.setValidator(DateValidatorPointForward.now())

        val builder = MaterialDatePicker.Builder.datePicker()
        // without input inside a picker
        builder.setTheme(R.style.Widget_BoardGameFinder_MaterialDatePicker)
        // ToDo move to resources
        builder.setTitleText("Event date")
        builder.setCalendarConstraints(constraintBuilder.build())
        val datePicker = builder.build()

        datePicker.addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener { selection: Long ->
            // Get the offset from our timezone and UTC.
            val timeZoneUTC = TimeZone.getDefault()
            // It will be negative, so that's the -1
            val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
            // Create a date format, then a date object with our offset
            val simpleFormat =
                SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = Date(selection + offsetFromUTC)
            binding.date.setText(simpleFormat.format(date))
        } as MaterialPickerOnPositiveButtonClickListener<Long>)

        binding.date.inputType = InputType.TYPE_NULL
        binding.date.keyListener = null
        binding.date.setOnClickListener{
            activity?.let {
                datePicker.show(
                    it.supportFragmentManager,
                    "DATE_PICKER"
                )
            }
        }
    }

    private fun setMap(){
        // initialize map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        // async map
        mapFragment!!.getMapAsync { googleMap ->
            map = googleMap

            // remove all POIs
            map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_json)
            )

            // For scrollview not to move
            map.setOnCameraMoveStartedListener{
                binding.scrollView.requestDisallowInterceptTouchEvent(true)
            }

            // For scrollview to move again
            map.setOnCameraIdleListener{
                binding.scrollView.requestDisallowInterceptTouchEvent(false)
            }

            // set coordinates in textview
            map.setOnMapClickListener{ latLng ->
                val cooText = latLng.latitude.toString() + ", " + latLng.longitude
                binding.location.setText(cooText)
            }
        }

        binding.location.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                // check if coordinates are valid
                if (!newEventViewModel.validateCoordinates(binding.location.text.toString()))
                    return

                val latLng = with(binding.location.text.toString().split(", ").toTypedArray()){
                    LatLng(this[0].toDouble(), this[1].toDouble())
                }

                // set marker
                val mo = MarkerOptions()
                val geocoder = Geocoder(activity)
                try {
                    val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                    if (addressList.size > 0) {
                        val adr = addressList[0]
                        mo.title(adr.getAddressLine(0))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                mo.position(latLng)
                map.clear()
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                map.addMarker(mo)
            }
        })

    }
}