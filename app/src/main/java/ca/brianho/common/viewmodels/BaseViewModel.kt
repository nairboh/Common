package ca.brianho.checkex.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private val lazyDisposables = lazy { CompositeDisposable() }
    protected val disposables by lazyDisposables

    override fun onCleared() {
        if (lazyDisposables.isInitialized()) {
            disposables.clear()
        }

        super.onCleared()
    }
}