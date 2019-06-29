package ds.gittweet.utility

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Long?.orZero(): Long = this ?: 0

fun Int?.orZero(): Int = this ?: 0

fun Boolean?.orFalse(): Boolean = this ?: false

fun Completable.applyScheduler(): Completable {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.applyScheduler(): Flowable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applyScheduler(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}