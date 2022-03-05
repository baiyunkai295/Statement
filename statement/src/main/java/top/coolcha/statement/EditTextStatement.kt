package top.coolcha.statement

import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.util.StatementViewHolder
import top.coolcha.statement.viewholder.EditTextViewHolder

/**
 * 输入框
 * @author byk
 * @date 2022年2月7日
 */
class EditTextStatement(


    important: Boolean = false,
    help: String = "",
    /**
     * 隐藏文字
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

    override var type = 1

    override fun init(holder: Any, position: Int) {
        holder as EditTextViewHolder
        holder.binding.let {
            it.etItemSE.hint = hint
            it.etItemSE.setText(text)
            it.tvItemSETitle.text = title

            it.etItemSE.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s == null) {
                        this@EditTextStatement.text = ""
                    } else {
                        this@EditTextStatement.text = s.toString()
                    }

                    update(s)
                }
            })
        }

        help(holder.binding.ivItemSHelp)
        important(holder.binding.ivItemSImportant)
    }
}