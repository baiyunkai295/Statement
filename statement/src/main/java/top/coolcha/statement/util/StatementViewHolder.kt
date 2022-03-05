package top.coolcha.statement.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import top.coolcha.statement.databinding.ItemStatementBinding

open class StatementViewHolder(item: View): BaseStatementViewHolder(item) {
    lateinit var binding: ItemStatementBinding
}