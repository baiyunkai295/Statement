package top.coolcha.statement

import android.annotation.SuppressLint
import android.widget.TextView
import top.coolcha.statement.databinding.ItemStatementBinding
import top.coolcha.statement.dialog.DateTimePicker
import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.util.StatementViewHolder
import java.text.SimpleDateFormat

/**
 * 日期选择
 * @author byk
 * @date 2022/2/8
 */
class DateStatement(
    /**
     * 是否显示时间
     */
    var time: Boolean = false,
    /**
     * 是否显示日期
     */
    var date: Boolean = true,
    /**
     * 所选时间戳
     */
    var timeInMillis: Long = System.currentTimeMillis(),


    important: Boolean = false,
    help: String = "",
    /**
     * 默认显示
     */
    hint: String = "",
    /**
     * 显示标题
     */
    title: String = "",

    /**
     * 输入内容
     */
    text: String = "",

    /**
     * json key
     */
    key: String = "",
): BaseStatement(important, help, hint, title, text, key) {

    companion object {
        @SuppressLint("SimpleDateFormat")
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        @SuppressLint("SimpleDateFormat")
        private val timeFormat = SimpleDateFormat("HH:mm")
        @SuppressLint("SimpleDateFormat")
        private val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
    }

    override fun save(): String {
        return "\"${key}\":\"${timeInMillis}\""
    }

    init {
        // 如果time和日期均为false默认只显示时间
        if (!time && !date) {
            date = true
        }
    }

    override var type = 0

    override fun init(holder: Any, position: Int) {
        holder as StatementViewHolder
        holder.binding.tvItemSTitle.text = title
        holder.binding.tvItemS.text = text
        holder.binding.tvItemS.hint = hint

        updateTime(holder.binding, timeInMillis)

        holder.binding.tvItemS.setOnClickListener {
            it as TextView
            DateTimePicker.instance.init(
                title = hint,
                date = date,
                time = time,
                start = timeInMillis
            ) { timeMillis ->

                updateTime(holder.binding, timeMillis)
            }.show(holder.itemView.context)
        }

        help(holder.binding.ivItemSHelp)
        important(holder.binding.ivItemSImportant)
    }

    private fun updateTime(binding: ItemStatementBinding, timeMillis: Long) {

        timeInMillis = timeMillis
        if (time && date) {
            text = format.format(timeInMillis)
        } else if (time) {
            text = timeFormat.format(timeInMillis)
        } else if (date) {
            text = dateFormat.format(timeInMillis)
        }
        binding.tvItemS.text = text

        update(timeInMillis)
    }

}