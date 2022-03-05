package top.coolcha.statement.util

import android.view.View

interface IStatement {

    /**
     * 将改内容转换为json字符串
     */
    fun save(): String

    /**
     * 加载试图
     */
    fun init(holder: Any, position: Int)

    /**
     * 更新数据
     * @param data Any
     */
    fun update(data: Any?)

    /**
     * 帮助按钮
     */
    fun help(view: View)

    /**
     * 是否是必填信息
     */
    fun important(view: View)
}