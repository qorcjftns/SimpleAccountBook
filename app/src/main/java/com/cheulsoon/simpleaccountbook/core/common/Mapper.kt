package com.cheulsoon.simpleaccountbook.core.common

import java.util.Calendar

fun Calendar.toLocalTimeMillis(): Long {
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
    return this.timeInMillis
}

