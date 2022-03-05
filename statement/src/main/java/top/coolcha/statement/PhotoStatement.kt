package top.coolcha.statement

import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import top.coolcha.photo.entity.Photo
import top.coolcha.photo.repo.PhotoRepo
import top.coolcha.photo.view.album.PhotoAlbumPreviewActivity
import top.coolcha.statement.adapter.PhotoSelectedListAdapter
import top.coolcha.statement.fragment.PhotoHelperFragment
import top.coolcha.statement.util.BaseStatement
import top.coolcha.statement.viewholder.PhotoViewHolder

class PhotoStatement (

    var manager: FragmentManager,

    var photos: Array<String> = arrayOf(),

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
    key: String = "",
): BaseStatement(important, help, hint, title, text, key) {

    override var type = 5

    private val photosList = mutableListOf<Photo>()

    init {
        for (photo in photos) {
            photosList.add(Photo(path = photo, edit = false, cache = ""))
        }
        photosList.add(Photo(uuid = "add", path = "", edit = false, cache = ""))
    }

    override fun save(): String {
        return "\"${key}\":\"${formatArray(photos)}\""
    }

    override fun init(holder: Any, position: Int) {
        holder as PhotoViewHolder
        holder.binding.tvItemSTitle.text = title

        help(holder.binding.ivItemSHelp)
        important(holder.binding.ivItemSImportant)

        // 加载图片选择列表
        val adapter = PhotoSelectedListAdapter()
        holder.binding.recyclerView.adapter = adapter
        adapter.submitList(photosList.toList())
        adapter.onPhotoClickListener = object : PhotoSelectedListAdapter.OnPhotoClickListener {
            override fun click(position: Int, photo: Photo) {
                if (photo.uuid == "add") {
                    manager.beginTransaction().add(R.id.fcv_item_s_p, PhotoHelperFragment.instance).commit()
                    return
                }

                PhotoRepo.repo.selectPhoto.value = photosList.subList(0, photosList.size - 1)
                val context = holder.itemView.context
                context.startActivity(Intent(context, PhotoAlbumPreviewActivity::class.java))
            }

            override fun delete(position: Int, photo: Photo) {
                photosList.removeAt(position)
                val toMutableList = photos.toMutableList()
                toMutableList.removeAt(position)
                photos = toMutableList.toTypedArray()

                update(formatArray(photos))
                adapter.submitList(photosList.toList())
            }
        }

        PhotoHelperFragment.instance.init {
            Log.d("图片选择", it)
            if (it != "cancel") {
                val new = photos.toMutableList()
                for (any in JSONArray.parseArray(it)) {
                    any as JSONObject
                    val path = any.getString("path")
                    if (!new.contains(path)) {
                        new.add(path)
                        photosList.add(photosList.size - 1, Photo(path = path, edit = false, cache = ""))
                    }
                }
                photos = new.toTypedArray()
                adapter.submitList(photosList.toList())
                update(formatArray(photos))
            }
            manager.beginTransaction().remove(PhotoHelperFragment.instance).commit()
        }
    }

    fun formatArray(photos: Array<String>): String {
        if (photos.isEmpty()) {
            return ""
        }

        val string = StringBuilder()
        for (photo1 in photos) {
            string.append(photo1).append(";")
        }

        return string.substring(0, string.length - 1)
    }
}