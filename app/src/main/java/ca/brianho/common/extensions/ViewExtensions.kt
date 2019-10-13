package ca.brianho.common.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.Navigation

fun View.onClickNavigateTo(@IdRes resId: Int) {
    this.setOnClickListener(Navigation.createNavigateOnClickListener(resId))
}