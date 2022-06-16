package com.example.quoteday.presentation

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.example.quoteday.databinding.FragmentSettingsBinding
import com.example.quoteday.presentation.viewmodel.ViewModalSettingFragment
import java.util.*


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModal: ViewModalSettingFragment
    private var titleMessage = TITLE_MESSAGE
    private var messageText = MESSAGE_TEXT


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModal = ViewModelProvider(this)[ViewModalSettingFragment::class.java]

        viewModal.getQuoteDay()
        viewModal.responseQuoteDay.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                it.body()?.let {
                    val getQuotesItem = it[0]
                    titleMessage = getQuotesItem.a
                    messageText = getQuotesItem.q
                    binding.switchNotification.setOnCheckedChangeListener{_,isChecked->
                        scheduleNotification(titleMessage, messageText)
                    }
                    binding.buttonSaveSettings.setOnClickListener {

                    }
                }
            }
        }

        createNotificationChannel()
        binding.buttonSaveSettings.setOnClickListener {

        }

    }

    private fun scheduleNotification(titleMessage: String,messageText: String) {

        val intent = Intent(requireContext(), Notification::class.java)
        intent.putExtra(TITLE_EXTRA, titleMessage)
        intent.putExtra(MESSAGE_EXTRA, messageText)
        Log.d("SettingsFragment","message $messageText")

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

//        showAlert(time,titleMessage,messageText)

    }
    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(requireContext())
        val timeFormat = android.text.format.DateFormat.getTimeFormat(requireContext())

        AlertDialog.Builder(requireActivity())
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }



    private fun getTime():Long {

        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        calendar.set(year,month,day,hour,minute)

        return  calendar.timeInMillis

    }

    private fun createNotificationChannel()
    {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL, name, importance)
        channel.description = desc
        val notificationManager =requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val TITLE_MESSAGE = "title message"
        private const val MESSAGE_TEXT = "message text"
    }
}