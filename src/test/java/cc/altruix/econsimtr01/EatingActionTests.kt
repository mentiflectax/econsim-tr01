package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
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
                Farmer(Mockito.mock(IResourceStorage::class.java), LinkedList()),
                Mockito.mock(IResourceStorage::class.java), LinkedList()
        )
        Assertions.assertThat(out.timeToRun(71999L)).isFalse()
        Assertions.assertThat(out.timeToRun(72000L)).isTrue()
        Assertions.assertThat(out.timeToRun(72001L)).isFalse()
        Assertions.assertThat(out.timeToRun(144000L)).isTrue()
        Assertions.assertThat(out.timeToRun(216000L)).isTrue()
        Assertions.assertThat(out.timeToRun(288000L)).isTrue()
        Assertions.assertThat(out.timeToRun(360000L)).isTrue()
        Assertions.assertThat(out.timeToRun(432000L)).isTrue()
        Assertions.assertThat(out.timeToRun(504000L)).isTrue()
        Assertions.assertThat(out.timeToRun(576000L)).isTrue()
        Assertions.assertThat(out.timeToRun(647999L)).isFalse()
        Assertions.assertThat(out.timeToRun(648000L)).isTrue()
        Assertions.assertThat(out.timeToRun(648001L)).isFalse()
    }
}
