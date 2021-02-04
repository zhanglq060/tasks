package org.tasks

import org.tasks.time.DateTime
import org.tasks.time.DateTimeUtils

class Freeze {
    fun <T> thawAfter(run: () -> T): T {
        try {
            return run()
        } finally {
            thaw()
        }
    }

    companion object {

        fun freezeClock(run: () -> Unit): Any {
            return freezeAt(DateTimeUtils.currentTimeMillis()).thawAfter(run)
        }

        fun <T> freezeAt(dateTime: DateTime, run: () -> T): T {
            return freezeAt(dateTime.millis, run)
        }

        fun <T> freezeAt(timestamp: Long, run: () -> T): T {
            return freezeAt(timestamp).thawAfter(run)
        }

        fun freezeAt(dateTime: DateTime): Freeze {
            return freezeAt(dateTime.millis)
        }

        fun freezeAt(millis: Long): Freeze {
            DateTimeUtils.setCurrentMillisFixed(millis)
            return Freeze()
        }

        fun thaw() {
            DateTimeUtils.setCurrentMillisSystem()
        }
    }
}