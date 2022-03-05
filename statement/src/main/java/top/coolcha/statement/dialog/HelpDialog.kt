package top.coolcha.statement.dialog

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import top.coolcha.statement.databinding.HelpDialogBinding
import java.net.ContentHandler

/**
 * 帮助信息提示
 * @author byk
 * @date 2022年2月12日
 */
class HelpDialog {

    var title: String = ""
    var help: String = ""

    companion object {
        val instance = HelpDialog()
    }

    fun init(
        title: String = this.title,
        help: String = this.help
    ): HelpDialog {

        this.help = help
        this.title = title

        return instance
    }

    fun show(context: Context) {
        HelpDialogMain(context).show()
    }

    class HelpDialogMain(context: Context): BottomSheetDialog(context) {
        lateinit var binding: HelpDialogBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = HelpDialogBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.tvHelpDialogHelp.text = instance.help
            binding.tvHelpDialogTitle.text = instance.title

            binding.tvHelpDialogDismiss.setOnClickListener {
                dismiss()
            }
        }
    }
}