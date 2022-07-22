package com.example.quoteday.presentation

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.quoteday.databinding.FragmentSettingsBinding
import java.util.*


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireActivity().getSharedPreferences(PREFERENCES_APP, MODE_PRIVATE)

        defaultValues()

        binding.buttonSaveSettings.setOnClickListener {
            val hourSave = binding.timePicker.hour
            val minuteSave = binding.timePicker.minute
            preferences.edit().putInt(TIME_HOUR, hourSave).apply()
            preferences.edit().putInt(TIME_MINUTE, minuteSave).apply()

            scheduleNotification()
        }

        createNotificationChannel()

        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                preferences.edit().putBoolean(STATE_SWITCH, true).apply()
                binding.buttonSaveSettings.isEnabled = true
            } else {
                preferences.edit().putBoolean(STATE_SWITCH, false).apply()
                binding.buttonSaveSettings.isEnabled = false
                scheduleNotificationCancel()
                Toast.makeText(
                    requireActivity(), "Notifications disabled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun scheduleNotification() {

        val intent = Intent(requireContext(), DailyNotification::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager =
            requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        showAlert(time)

    }

    private fun scheduleNotificationCancel() {

        val intent = Intent(requireContext(), DailyNotification::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager =
            requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(
            pendingIntent
        )

    }

    private fun showAlert(time: Long) {
        val date = Date(time)
        val timeFormat =
            android.text.format.DateFormat.getTimeFormat(requireContext())

        AlertDialog.Builder(requireActivity())
            .setTitle("Notification Scheduled")
            .setMessage(
                "\nMessage: Daily notifications  " +
                        "\nscheduled at:" + timeFormat.format(date)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }

    private fun getTime(): Long {

        val currentMinute = preferences.getInt(TIME_MINUTE, DEFAULT_TIME)
        val currentHour = preferences.getInt(TIME_HOUR, DEFAULT_TIME)

        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        calendar.set(year, month, day, currentHour, currentMinute)

        return calendar.timeInMillis
    }

    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL, name, importance)
        channel.description = desc
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun defaultValues() {
        val calendar = Calendar.getInstance()
        val hourTime = calendar.get(Calendar.HOUR)
        val minuteTime = calendar.get(Calendar.MINUTE)
        val hourNow = preferences.getInt(TIME_HOUR, hourTime)
        val minuteNow = preferences.getInt(TIME_MINUTE, minuteTime)
        binding.timePicker.hour = hourNow
        binding.timePicker.minute = minuteNow

        val stateSwitch = preferences.getBoolean(STATE_SWITCH, false)
        if (stateSwitch) {
            binding.switchNotification.isChecked = true
            binding.buttonSaveSettings.isEnabled = true

        } else {
            binding.buttonSaveSettings.isEnabled = false

        }
    }

    companion object {
        private const val DEFAULT_TIME = 1
        private const val STATE_SWITCH = "state switch"
        private const val PREFERENCES_APP = "preferences"
        private const val TIME_HOUR = "time hour"
        private const val TIME_MINUTE = "time minute"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}