package ds.githubclient.util

fun Long?.orZero(): Long = this ?: 0

fun Boolean?.orFalse(): Boolean = this ?: false