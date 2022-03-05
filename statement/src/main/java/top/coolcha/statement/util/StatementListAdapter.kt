package top.coolcha.statement.util

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class StatementListAdapter: ListAdapter<BaseStatement, BaseStatementViewHolder>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<BaseStatement>() {
            override fun areItemsTheSame(oldItem: BaseStatement, newItem: BaseStatement): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: BaseStatement, newItem: BaseStatement): Boolean {
                return oldItem.key == newItem.key
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseStatementViewHolder {
        return StatementViewHelper.get(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseStatementViewHolder, position: Int) {
        getItem(position).init(holder, position)
    }
}