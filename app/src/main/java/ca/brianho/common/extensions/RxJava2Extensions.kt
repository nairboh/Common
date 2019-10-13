package ca.brianho.common.extensions

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

inline fun <reified T>Flowable<T>.fromIOToMain(
        disposable: CompositeDisposable,
        crossinline body: (T) -> Unit,
        noinline error: (Throwable) -> Unit
) =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .distinctUntilChanged()
        .replay()
        .autoConnect(1) { disposable.add(it) }
        .subscribe ({ body(it) }, error)
        .addTo(disposable)

inline fun <reified T>Flowable<T>.fromIOToMain(
        disposable: CompositeDisposable,
        crossinline body: (T) -> Unit
) = this.fromIOToMain(disposable, body, { log(it) })

inline fun <reified T>Flowable<T>.onIO(
        disposable: CompositeDisposable,
        crossinline body: (T) -> Unit,
        noinline error: (Throwable) -> Unit
) =
    this.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .distinctUntilChanged()
        .replay()
        .autoConnect(1) { disposable.add(it) }
        .subscribe ({ body(it) }, error)
        .addTo(disposable)

inline fun <reified T>Flowable<T>.onIO(
        disposable: CompositeDisposable,
        crossinline body: (T) -> Unit
) = this.fromIOToMain(disposable, body, { log(it) })

fun log(t: Throwable) {
    Log.e("Error", "Exception thrown!", t)
}
