package top.coolcha.statement

import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.util.StatementViewHolder

class TextViewStatement(

    important: Boolean = false,
    help: String = "",
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

    override var type = 0

    override fun init(holder: Any, position: Int) {
        holder as StatementViewHolder
        holder.binding.tvItemSTitle.text = title
        holder.binding.tvItemS.text = text
        holder.binding.tvItemS.hint = hint

        help(holder.binding.ivItemSHelp)
        important(holder.binding.ivItemSImportant)
    }
}