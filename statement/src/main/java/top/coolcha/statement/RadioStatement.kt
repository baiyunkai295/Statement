package top.coolcha.statement

import android.widget.RadioButton
import android.widget.RadioGroup
import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.viewholder.RadioViewHolder

class RadioStatement(
    /**
     * 单选框文本
     */
    var radio: Array<String> = arrayOf(),
    /**
     * 单选框文本标识
     */
    var radioId: Array<String> = arrayOf(),
    /**
     * 选中下标
     */
    var select: Int = 0,

    important: Boolean = false,

    help: String = "",
    /**
     * 显示标题
     */
    title: String = "",

    /**
     * json key
     */
    key: String = "",
): BaseStatement(important, help, "", title, "", key) {

    override var type = 3

    override fun save(): String {
        return "\"${key}\":\"${radioId[select]}\""
    }

    init {
        if (radio.size != radioId.size) {
            throw RuntimeException("选项和标识数量应保持一致")
        }

        if (select > radio.size) {
            select = radio.size - 1
        }
    }

    override fun init(holder: Any, position: Int) {
        holder as RadioViewHolder
        holder.binding.tvItemSTitle.text = title
        holder.binding.rgItemRadio.let {
            if (radio.size <= 3) {
                // 横向
                it.orientation = RadioGroup.HORIZONTAL
            } else {
                it.orientation = RadioGroup.VERTICAL
            }

            for (index in radio.indices) {
                val button = RadioButton(holder.itemView.context)
                button.text = radio[index]
                button.id = -1 * (index + 100)
                button.hint = radioId[index]
                button.isChecked = select == index
                it.addView(button)
            }

            it.setOnCheckedChangeListener { _, checkedId ->
                select = -(checkedId + 100)

                update(radioId[select])
            }
        }

        help(holder.binding.ivItemSHelp)
        important(holder.binding.ivItemSImportant)
    }
}