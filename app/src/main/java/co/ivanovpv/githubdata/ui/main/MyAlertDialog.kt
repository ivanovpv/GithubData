package co.ivanovpv.githubdata.ui.main

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog
import androidx.core.view.isVisible
import co.ivanovpv.githubdata.databinding.CustomAlertDialogBinding

class MyAlertDialog(
    context: Context,
) : AppCompatDialog(context) {

    companion object {
    }

    private val binding: CustomAlertDialogBinding = CustomAlertDialogBinding.inflate(
        layoutInflater,
        findViewById(android.R.id.content),
        false
    )

    init {
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun withTitle(@StringRes title: Int) = withTitle(context.getString(title))

    fun withTitle(title: String) = apply {
        binding.tvTitle.text = title
    }

    fun withDescription(@StringRes description: Int) =
        withDescription(context.getString(description))

    fun withDescription(description: String) = apply {
        binding.tvDescription.isVisible = description.isNotEmpty()
        binding.tvDescription.text = description
    }

    fun withIcon(@DrawableRes drawableRes: Int) = apply {
        binding.ivIcon.setImageResource(drawableRes)
    }

    fun withNegativeButton(@StringRes title: Int?, action: (() -> Unit)? = null) = apply {
        title?.let {
            binding.btnLeft.text = context.getString(title)
            binding.btnLeft.setOnClickListener {
                dismiss()
                action?.invoke()
            }
            binding.btnRight.visibility = View.VISIBLE
        }
    }

    fun withPositiveButton(@StringRes title: Int, action: (() -> Unit)? = null) = apply {
        binding.btnRight.text = context.getString(title)
        binding.btnRight.setOnClickListener {
            dismiss()
            action?.invoke()
        }
        binding.btnRight.visibility = View.VISIBLE
    }

    fun withCancelListener(action: (() -> Unit)?) = apply {
        setOnCancelListener {
            action?.invoke()
        }
    }

    fun withDismissListener(action: (() -> Unit)?) = apply {
        setOnDismissListener {
            action?.invoke()
        }
    }

    fun withCloseButton(action: (() -> Unit)?) = apply {
        binding.ivClose.isVisible = true
        binding.ivClose.setOnClickListener {
            dismiss()
            action?.invoke()
        }
    }

}