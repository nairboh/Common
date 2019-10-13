package ca.brianho.common.extensions

import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ca.brianho.whea.BaseViewModelFactory

inline fun <reified VM : ViewModel> Fragment.viewModel(noinline creator: (() -> VM)? = null) = lazy {
    ViewModelProviders.of(this, creator?.let { BaseViewModelFactory(it) }).get(VM::class.java)
}

fun Fragment.navigateTo(@IdRes resId: Int) = findNavController().navigate(resId)

fun Fragment.showLongToast(text: CharSequence?) = Toast.makeText(activity, text, Toast.LENGTH_LONG).show()

fun Fragment.showLongToast(@StringRes resId: Int) = Toast.makeText(activity, resId, Toast.LENGTH_LONG).show()

fun Fragment.getCurrentFragment() = this.childFragmentManager.primaryNavigationFragment