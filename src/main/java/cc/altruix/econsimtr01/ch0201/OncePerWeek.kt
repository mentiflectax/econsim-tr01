package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.toDayOfWeekName
import org.joda.time.DateTime

/**
 * Created by pisarenko on 14.04.2016.
 */
class OncePerWeek(val dayOfWeek:String) : (DateTime) -> Boolean {
    override fun invoke(t: DateTime): Boolean = dayOfWeek.equals(t.toDayOfWeekName())
}