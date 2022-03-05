package top.coolcha.statement.util

import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.android.material.appbar.CollapsingToolbarLayout
import top.coolcha.statement.dialog.HelpDialog

/**
 * 基础框架
 * @author byk
 * @date 2022年2月7日
 */
open class BaseStatement(
    /**
     * 是否必填
     */
    var important: Boolean = false,
    /**
     * 说明文本
     */
    var help: String = "",
    /**
     * 下方文本
     */
    var hint: String = "",
    /**
     * 显示标题
     */
    var title: String = "",

    /**
     * 输入内容
     */
    var text: String = "",

    /**
     * json key
     */
    var key: String = "",
    /**
     * 该列表key
     */
    var KEY: String = "",
    /**
     * viewType
     */
    open var type: Int = 0
): IStatement {

    override fun save(): String {
        return "\"${key}\":\"${text}\""
    }

    override fun init(holder: Any, position: Int) {

    }

    override fun update(data: Any?) {
        StatementViewHelper.dataMap[KEY]?.let {
            it[key] = data
            StatementViewHelper.listenMap[KEY]?.invoke(it)
        }
    }

    override fun help(view: View) {
        if (TextUtils.isEmpty(help)) {
            view.visibility = View.GONE
        }
        view.setOnClickListener {
            HelpDialog.instance.init(
                title = title,
                help = help
            ).show(view.context)
        }
    }

    override fun important(view: View) {
        if (!important) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }
}