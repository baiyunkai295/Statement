package top.coolcha.statement.dialog

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import top.coolcha.statement.databinding.DateTimePickerBinding
import java.util.*

/**
 * 日期时间选择器
 * @author byk
 * @date 2022/2/8
 */
class DateTimePicker {

    var titleStr: String = ""
    var dateShow: Boolean = true
    var timeShow: Boolean = false
    var startTime: Long = System.currentTimeMillis()

    lateinit var resultListener: (time: Long) -> Unit

    companion object {

        val instance = DateTimePicker()
    }

    fun init(
        title: String = titleStr,
        date: Boolean = dateShow,
        time: Boolean = timeShow,
        start: Long = startTime,
        listener: (time: Long) -> Unit = resultListener
    ): DateTimePicker {
        titleStr = title
        dateShow = date
        timeShow = time
        startTime = start
        resultListener = listener

        return instance
    }

    fun show(context: Context) {
        val dialog = DateTimePickerDialog(context)
        dialog.show()
    }

    inner class DateTimePickerDialog(context: Context): BottomSheetDialog(context) {

        lateinit var binding: DateTimePickerBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DateTimePickerBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.tvDateTimePickerTitle.text = titleStr

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = startTime

            binding.dateTimePickerDate.let {
                if (!dateShow) {
                    it.visibility = View.GONE
                    return@let
                }

                it.init(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    null
                )
            }

            binding.dateTimePickerTime.let {
                if (!timeShow) {
                    it.visibility = View.GONE
                    return@let
                }
                it.setIs24HourView(true)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.hour = calendar.get(Calendar.HOUR_OF_DAY)
                    it.minute = calendar.get(Calendar.MINUTE)
                } else {
                    it.currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                    it.currentMinute = calendar.get(Calendar.MINUTE)
                }
            }


            // 点击确认后，计算所选时间并返回时间戳
            binding.tvDateTimePickerCommit.setOnClickListener {
                calendar.timeInMillis = 0L
                var year = 1970
                var month = 1
                var day = 1
                var hour = 0
                var minute = 0
                if (dateShow) {
                    binding.dateTimePickerDate.let {
                        year = it.year
                        month = it.month
                        day = it.dayOfMonth
                    }
                }
                if (timeShow) {
                    binding.dateTimePickerTime.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            hour = it.hour
                            minute = it.minute
                        } else {
                            hour = it.currentHour
                            minute = it.currentMinute
                        }
                    }
                }
                calendar.set(
                    year, month, day, hour, minute
                )

                instance.resultListener.invoke(calendar.timeInMillis)
                dismiss()
            }
            binding.tvDateTimePickerCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}