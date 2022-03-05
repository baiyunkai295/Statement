package top.coolcha.statement

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import top.coolcha.statement.databinding.ItemStatementNumberBinding
import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.viewholder.NumberViewHolder


/**
 * 数字选择
 * @author byk
 * @date 2022年2月13日
 */
class NumberStatement(
    /**
     * 选择内容
     */
    var select: Float = 0f,
    /**
     * 最小
     */
    var min: Float = 0f,
    /**
     * 最大
     */
    var max: Float = Float.MAX_VALUE,
    /**
     * 间隔
     */
    var space: Float = 1f,
    /**
     * 整数还是小数
     * 默认整数切换
     */
    var integer: Boolean = true,

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
    key: String = ""
): BaseStatement(important, help, hint, title, text, key) {

    override var type = 4

    @SuppressLint("ClickableViewAccessibility")
    override fun init(holder: Any, position: Int) {
        holder as NumberViewHolder

        holder.binding.let { binding ->
            updateNumber(binding)

            binding.tvItemSTitle.text = title

            // 减少按钮
            binding.ivItemSNSub.setOnClickListener {
                if (select - space < min) {
                    return@setOnClickListener
                }
                select -= space
                updateNumber(binding)
                binding.etItemSNNumber.isEnabled = false
                binding.etItemSNNumber.isEnabled = true
                binding.etItemSNNumber.clearFocus()
            }

            // 增加按钮
            binding.ivItemSNAdd.setOnClickListener {
                if (select + space > max) {
                    return@setOnClickListener
                }
                select += space
                updateNumber(binding)
                binding.etItemSNNumber.isEnabled = false
                binding.etItemSNNumber.isEnabled = true
                binding.etItemSNNumber.clearFocus()
            }

            binding.etItemSNNumber.addTextChangedListener(object : TextWatcher {
                private var deleteLastChar = false
                private var startWithDot = false
                private var startWithDotAnd = false
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let {
                        if (s.toString().contains(".")) {
                            val length: Int =
                                s.length - s.toString()
                                    .lastIndexOf(".")
                            // 小数位
                            deleteLastChar = length >= 4
                            // 小数点开头
                            startWithDot =
                                s.toString().startsWith(".")
                            // -.开头
                            startWithDotAnd =
                                s.toString().startsWith("-.")
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    if (!TextUtils.isEmpty(s)) {
                        var str = s.toString()
                        if (deleteLastChar) {
                            str = s.toString().substring(0, s.toString().length - 1)
                        }
                        if (startWithDot) {
                            str = "0."
                        }
                        if (startWithDotAnd) {
                            str = "-0."
                        }
                        Log.i("lskjdf", str)
                        Log.i("ldjflj", str.toFloat().toString())
                        val toFloat = str.toFloat()
                        if (select == toFloat) {
                            return
                        }
                        select = toFloat
                        if (select < min) {
                            select = min
                        }
                        if (select > max) {
                            select = max
                        }

                        updateNumber(binding)
                    }
                }

            })

            help(binding.ivItemSHelp)
            important(binding.ivItemSImportant)
        }
    }

    /**
     * 更新所选文本
     */
    private fun updateNumber(binding: ItemStatementNumberBinding) {
        var value = select.toString()
        if (integer) {
            value = value.split(".")[0]
        }
        binding.etItemSNNumber.setText(value)

        if (binding.etItemSNNumber.hasFocus()) {
            binding.etItemSNNumber.setSelection(value.length)
        }

        update(value)
    }


}