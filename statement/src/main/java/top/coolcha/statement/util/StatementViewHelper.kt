package top.coolcha.statement.util

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.fastjson.JSONObject
import top.coolcha.statement.databinding.*
import top.coolcha.statement.viewholder.*

/**
 * 找到对应页面
 * @author byk
 * @date 2022年2月7日
 */
class StatementViewHelper {

    companion object {

        val listenMap = mutableMapOf<String, (data: JSONObject) -> Unit>()
        val updateMap = mutableMapOf<String, (data: Array<BaseStatement>, position: Int, old: Int) -> Unit>()

        val dataMap = mutableMapOf<String, JSONObject>()

        /**
         * 获取对应的数据
         * @param key String
         * @param list List<BaseStatement>
         */
        @JvmStatic
        fun updateData(key: String, list: List<BaseStatement>) {
            val data = StringBuilder()
            data.append("{")
            for (baseStatement in list) {
                baseStatement.KEY = key
                data.append(baseStatement.save()).append(",")
            }
            data.append("}")
            dataMap[key] = JSONObject.parseObject(data.toString())
        }

        @JvmStatic
        fun get(parent: ViewGroup, viewType: Int): BaseStatementViewHolder {
            return when(viewType) {
                1 -> {
                    val binding = ItemStatementEdittextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    val holder = EditTextViewHolder(binding.root)
                    holder.binding = binding
                    holder
                }
                2 -> {
                    val binding = ItemStatementSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    val holder = SwitchViewHolder(binding.root)
                    holder.binding= binding
                    holder
                }
                3 -> {
                    val binding = ItemStatementRadioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    val holder = RadioViewHolder(binding.root)
                    holder.binding= binding
                    holder
                }
                4 -> {
                    val binding = ItemStatementNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    val holder = NumberViewHolder(binding.root)
                    holder.binding= binding
                    holder
                }
                5 -> {
                    val binding = ItemStatementPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    val holder = PhotoViewHolder(binding.root)
                    holder.binding = binding
                    holder
                }
                else -> {
                    val binding = ItemStatementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    val holder = StatementViewHolder(binding.root)
                    holder.binding = binding
                    holder
                }
            }
        }

        /**
         * 监听数据变化
         */
        @JvmStatic
        fun listen(key: String, listener: (data: JSONObject) -> Unit) {
            listenMap[key] = listener
        }

        /**
         * 监听列表数据变化
         */
        @JvmStatic
        fun update(key: String, listener:  (data: Array<BaseStatement>, position: Int, old: Int) -> Unit) {
            updateMap[key] = listener
        }
    }
}