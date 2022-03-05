package top.coolcha.statement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import top.coolcha.photo.entity.Photo
import top.coolcha.statement.databinding.ItemStatementPhotoSelectedBinding

class PhotoSelectedListAdapter: ListAdapter<Photo, PhotoSelectedListAdapter.AlbumItemViewHolder>(DIFF) {

    lateinit var onPhotoClickListener : OnPhotoClickListener

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.cache == newItem.cache && oldItem.edit == newItem.edit && oldItem.path == newItem.path
            }
        }
    }

    class AlbumItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemStatementPhotoSelectedBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val binding: ItemStatementPhotoSelectedBinding =
            ItemStatementPhotoSelectedBinding.inflate(LayoutInflater.from(parent.context))
        val holder = AlbumItemViewHolder(binding.root)
        holder.binding = binding
        return holder
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        val photo: Photo = getItem(position)
        val binding = holder.binding

        if (photo.uuid == "add") {
            binding.ivItemStatementPSDelete.visibility = View.GONE
            binding.sivItemStatementPS.visibility = View.GONE
            binding.imageView.visibility = View.VISIBLE
        } else {
            binding.imageView.visibility = View.GONE
            Glide.with(holder.itemView.context)
                .load(photo.path)
                .into(binding.sivItemStatementPS)
            binding.ivItemStatementPSDelete.visibility = View.VISIBLE
        }


        binding.cvItemStatementPS.setOnClickListener {
            onPhotoClickListener.click(position, photo)
        }

        binding.ivItemStatementPSDelete.setOnClickListener {
            onPhotoClickListener.delete(holder.adapterPosition, photo)
        }
    }

    interface OnPhotoClickListener {
        fun click(position: Int, photo: Photo)

        fun delete(position: Int, photo: Photo)
    }
}