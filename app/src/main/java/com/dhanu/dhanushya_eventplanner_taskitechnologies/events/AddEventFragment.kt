package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.FragmentAddEventBinding
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddEventFragment : Fragment() {

    private var _binding: FragmentAddEventBinding? = null
    private val binding get()= _binding!!
    private lateinit var viewModel: EventViewModel
    private var eventId: Int = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventId = it.getInt("eventId", -1)
            isEditMode = eventId != -1
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isEditMode) {
            binding.editTextTitle.setText(arguments?.getString("title"))
            binding.editTextDescription.setText(arguments?.getString("description"))
            binding.editTextTime.setText(arguments?.getString("time"))
            binding.buttonSaveEvent.text = "Update"

            val dateInMillis = arguments?.getLong("date") ?: System.currentTimeMillis()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.editTextDate.setText(dateFormat.format(Date(dateInMillis)))
        }

        viewModel = ViewModelProvider(requireActivity())[EventViewModel::class.java]

        binding.editTextDate.setOnClickListener { setupDatePicker() }
        binding.editTextTime.setOnClickListener { setupTimePicker() }

        binding.buttonSaveEvent.setOnClickListener {
            saveEvent()
        }
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        binding.editTextDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    binding.editTextDate.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupTimePicker() {
        val calendar = Calendar.getInstance()
        binding.editTextTime.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    val time = String.format("%02d:%02d", hourOfDay, minute)
                    binding.editTextTime.setText(time)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun saveEvent() {
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val date = binding.editTextDate.text.toString().trim()
        var time = binding.editTextTime.text.toString().trim()

        if (title.isEmpty() || date.isEmpty() || time.isEmpty()){
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDate = dateFormat.parse(date)

        val calendar = Calendar.getInstance()
        calendar.time = selectedDate ?: Date()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val normalizedDateInMillis = calendar.timeInMillis

        Log.d("EventDebug", "Normalized Date millis: $normalizedDateInMillis")

        val event = Event(
            id = if (isEditMode) eventId else 0,
            title = title,
            description = description,
            date = normalizedDateInMillis, // use normalized date
            time = time
        )

        // Insert or update in RoomDB
        if (isEditMode) {
            viewModel.updateEvent(event)
            Toast.makeText(requireContext(), "Event Updated", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.insertEvent(event)
            Toast.makeText(requireContext(), "Event Added", Toast.LENGTH_SHORT).show()
        }

        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}