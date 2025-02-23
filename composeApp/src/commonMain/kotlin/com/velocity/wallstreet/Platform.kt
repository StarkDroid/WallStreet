package com.velocity.wallstreet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform