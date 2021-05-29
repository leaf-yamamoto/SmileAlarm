package app.yamamoto.leaf.smilealarm.util

import java.util.concurrent.atomic.AtomicInteger

object RandomintUtil {
    private val seed = AtomicInteger()

    fun getRandomInt() = seed.getAndIncrement() + System.currentTimeMillis().toInt()
}