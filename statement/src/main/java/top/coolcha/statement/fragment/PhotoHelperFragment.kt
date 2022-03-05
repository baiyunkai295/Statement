package top.coolcha.statement.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import top.coolcha.photo.view.album.PhotoAlbumMainActivity
import top.coolcha.statement.R
import top.coolcha.utils.BaseFragment

/**
 * 图片帮助
 * @author byk
 * @date 2022年2月17日
 */
class PhotoHelperFragment : BaseFragment() {

    companion object {
        val instance = PhotoHelperFragment()
    }

    lateinit var callback : (photos: String) -> Unit

    fun init(
        callback: (photos: String) -> Unit = this.callback
    ) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_helper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent(context, PhotoAlbumMainActivity::class.java)
        intent.putExtra("max", 9)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) {
            callback.invoke("cancel")
            return
        }
        if (resultCode != -1) {
            return
        }
        if (requestCode != 1) {
            return
        }

        data?.let {
            val photo = it.getStringExtra("result") as String
            callback.invoke(photo)
        }
    }
}