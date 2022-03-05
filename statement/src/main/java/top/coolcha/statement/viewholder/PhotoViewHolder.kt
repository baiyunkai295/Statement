package top.coolcha.statement.viewholder

import android.view.View
import top.coolcha.statement.databinding.ItemStatementPhotoBinding
import top.coolcha.statement.util.BaseStatementViewHolder

open class PhotoViewHolder(item: View): BaseStatementViewHolder(item) {
    lateinit var binding: ItemStatementPhotoBinding
}