package com.example.quoteday.presentation.settings

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
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quoteday.R
import com.example.quoteday.databinding.FragmentSettingsBinding
import com.example.quoteday.presentation.CHANNEL
import com.example.quoteday.presentation.DailyNotification
import com.example.quoteday.presentation.NOTIFICATION_ID
import com.example.core.extention.BaseFragment
import java.util.*


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireActivity().getSharedPreferences(PREFERENCES_APP, MODE_PRIVATE)
        defaultValuesTimePicker()
        saveSettings()
        createNotificationChannel()
        behaviourSwitch()
    }

    private fun saveSettings() {
        binding.buttonSaveSettings.setOnClickListener {
            val hourSave = binding.timePicker.hour
            val minuteSave = binding.timePicker.minute
            preferences.edit().putInt(TIME_HOUR, hourSave).apply()
            preferences.edit().putInt(TIME_MINUTE, minuteSave).apply()
            scheduleNotification()
        }
    }

    private fun behaviourSwitch() {
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                preferences.edit().putBoolean(STATE_SWITCH, true).apply()
                binding.buttonSaveSettings.isEnabled = true
            } else {
                preferences.edit().putBoolean(STATE_SWITCH, false).apply()
                binding.buttonSaveSettings.isEnabled = false
                scheduleNotificationCancel()
                Toast.makeText(
                    requireActivity(), getString(R.string.notification_disabled),
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
            .setTitle(getString(R.string.notification_scheduled))
            .setMessage(
                getString(R.string.message) +
                        "\n" + getString(R.string.scheduled) + timeFormat.format(date)
            )
            .setPositiveButton(getString(R.string.okay)) { _, _ -> }
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
        val name = getString(R.string.notification_channel)
        val desc = getString(R.string.description_channel)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL, name, importance)
        channel.description = desc
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun defaultValuesTimePicker() {
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
}