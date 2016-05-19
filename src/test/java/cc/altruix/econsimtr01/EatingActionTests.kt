/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01

import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class EatingActionTests {

    @Test
    fun timeToRunSunnyDay() {
        val out = EatingAction(
                Farmer(Mockito.mock(IResourceStorage::class.java), LinkedList(), 30, 1.0),
                Mockito.mock(IResourceStorage::class.java), LinkedList(), 1.0
        )
        out.timeToRun(71999L.secondsToSimulationDateTime()).shouldBeFalse()
        out.timeToRun(72000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(72001L.secondsToSimulationDateTime()).shouldBeFalse()

        for (i in 1..7) {
            out.timeToRun((DateTime(0, 1, 0+i, 20, 0, 0).plusSeconds(1))).shouldBeFalse()
            out.timeToRun(DateTime(0, 1, 0+i, 20, 0, 0)).shouldBeTrue()
            out.timeToRun((DateTime(0, 1, 0+i, 20, 0, 0).plusSeconds(1))).shouldBeFalse()
        }
    }
}
