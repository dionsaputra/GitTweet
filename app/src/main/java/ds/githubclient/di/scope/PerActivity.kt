package ds.mvpkotlin.di.scope

import java.lang.annotation.RetentionPolicy
import javax.inject.Scope
import kotlin.annotation.Retention

@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity
