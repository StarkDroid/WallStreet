package com.velocity.wallstreet.data.factory

import android.content.Context

actual class ContextFactory(private val context: Context) {
    actual fun getContext(): Any = context
}