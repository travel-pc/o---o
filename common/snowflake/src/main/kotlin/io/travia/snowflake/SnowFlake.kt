package io.travia.snowflake

import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

fun generateSnowflakeId(): Long {
    val UNUSED_BITS = 1
    val EPOCH_BITS = 41
    val NODE_ID_BITS = 10
    val SEQUENCE_BITS = 12

    val maxNodeId = (1L shl NODE_ID_BITS) - 1
    val maxSequence = (1L shl SEQUENCE_BITS) - 1

    val nodeId = Random.nextLong(0, maxNodeId + 1)  // 랜덤한 노드 ID 생성
    val startTimeMillis = 1704067200000L  // UTC = 2024-01-01T00:00:00Z
    val lastTimeMillis = AtomicLong(startTimeMillis)
    val sequence = AtomicLong(0)

    synchronized(lastTimeMillis) {
        var currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis < lastTimeMillis.get()) {
            throw IllegalStateException("Invalid Time")
        }

        if (currentTimeMillis == lastTimeMillis.get()) {
            val newSequence = (sequence.incrementAndGet() and maxSequence)
            if (newSequence == 0L) {
                while (currentTimeMillis <= lastTimeMillis.get()) {
                    currentTimeMillis = System.currentTimeMillis()
                }
            }
        } else {
            sequence.set(0)
        }

        lastTimeMillis.set(currentTimeMillis)

        return ((currentTimeMillis - startTimeMillis) shl (NODE_ID_BITS + SEQUENCE_BITS)) or
                (nodeId shl SEQUENCE_BITS) or
                sequence.get()
    }
}

// 예제 실행
fun main() {
    repeat(5) {
        println(generateSnowflakeId())
    }
}
