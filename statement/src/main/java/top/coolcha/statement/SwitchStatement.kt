package top.coolcha.statement

import top.coolcha.statement.databinding.ItemStatementSwitchBinding
import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.util.StatementViewHelper
import top.coolcha.statement.viewholder.SwitchViewHolder

/**
 * 开关模块
 * @author byk
 * @date 2022/2/8
 */
class SwitchStatement(
    /**
     * 开关开启文本
     */
    var on: String = "",
    /**
     * 开关关闭文本
     */
    var off: String = "",

    /**
     * 是否选中
     */
    var check: Boolean = false,
    /**
     * 所包含的子集
     */
    var children: Array<Array<BaseStatement>> = arrayOf(),
    /**
     * 子集对应标识
     */
    var childrenType: Array<String> = arrayOf(),

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

    override var type = 2

    /**
     * 历史选择大小
     */
    private var old: Int = -1

    override fun save(): String {
        return "\"${key}\":\"${check}\""
    }

    override fun init(holder: Any, position: Int) {
        holder as SwitchViewHolder
        holder.binding.tvItemSTitle.text = title
        holder.binding.tvItemSSType.text = if (check) on else off
        holder.binding.scItemSS.let {
            it.isChecked = check
            it.setOnCheckedChangeListener { _, isChecked ->
                check = isChecked
                select(holder.binding, position)
            }

            select(holder.binding, position)
        }


        help(holder.binding.ivItemSHelp)
        important(holder.binding.ivItemSImportant)
    }

    /**
     * 当发生改变时触发
     */
    private fun select(binding: ItemStatementSwitchBinding, position: Int) {
        val select = if (check) on else off
        binding.tvItemSSType.text = select

        // 判断所选内容
        for (i in childrenType.indices) {
            if (childrenType[i] == select) {
                val selectStatements = children[i]

                StatementViewHelper.updateMap[KEY]?.invoke(selectStatements, position, old)
                old = selectStatements.size
            }
        }

        update(check)
    }
}