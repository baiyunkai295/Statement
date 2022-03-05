package top.coolcha.statement

import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.util.StatementViewHolder
import top.coolcha.widget.colorpicker.CColorPicker

class ColorStatement(
    /**
     * 默认颜色
     */
    var color: String = "#ffffffff",

    important: Boolean = false,
    help: String = "",
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
): BaseStatement(important, help, "", title, text, key) {

    override fun save(): String {
        return "\"${key}\":\"${color}\""
    }

    override fun init(holder: Any, position: Int) {
        holder as StatementViewHolder

        holder.binding.tvItemSTitle.text = title
        holder.binding.tvItemS.text = color

        holder.binding.tvItemS.setOnClickListener {
            CColorPicker.instance
                .init(
                    colorStr = color
                ) { _, colorStr ->
                    this.color = colorStr
                    holder.binding.tvItemS.text = colorStr

                    update(colorStr)
                }
                .show(holder.itemView.context)
        }

        help(holder.binding.ivItemSHelp)
        important(holder.binding.ivItemSImportant)
    }
}