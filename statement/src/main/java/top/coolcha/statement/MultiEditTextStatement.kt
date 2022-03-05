package top.coolcha.statement

import android.text.Editable
import android.text.TextWatcher
import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.viewholder.EditTextViewHolder

class MultiEditTextStatement(


    important: Boolean = false,
    help: String = "",
    /**
     * 隐藏文字
     */
    hint: String = "",
    /**
     * 最大行数
     */
    var max: Int = 3,
    /**
     * 显示标题
     */
    title: String,

    /**
     * 输入内容
     */
    text: String = "",

    /**
     * json key
     */
    key: String = "",
): BaseStatement(important, help, hint, title, text, key) {

    override var type = 1

    override fun init(holder: Any, position: Int) {
        holder as EditTextViewHolder
        holder.binding.let {
            it.etItemSE.hint = hint
            it.etItemSE.isSingleLine = false
            it.etItemSE.maxLines = max
            it.etItemSE.setText(text)
            it.tvItemSETitle.text = title

            it.etItemSE.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s == null) {
                        this@MultiEditTextStatement.text = ""
                    } else {
                        this@MultiEditTextStatement.text = s.toString()
                    }

                    update(s)
                }
            })
        }

        help(holder.binding.ivItemSHelp)
        important(holder.binding.ivItemSImportant)
    }
}