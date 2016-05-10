package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 06.05.2016.
 */
class Sim1aTimeSeriesCreatorTests {
    @Test
    fun runWiring() {
        // Prepare
        val simData = HashMap<DateTime, Sim1aResultsRow>()
        val t0 = 0L.millisToSimulationDateTime()
        val row0 = Sim1aResultsRow(t0)
        val t1 = t0.plusMinutes(1)
        val row1 = Sim1aResultsRow(t1)
        simData.put(t0, row0)
        simData.put(t0, row1)
        val targetFileName = "targetFileName"
        val out = Mockito.spy(Sim1aTimeSeriesCreator(simData, targetFileName, emptyList()))
        val times = arrayListOf(t0, t1)
        val builder = StringBuilder()
        Mockito.doReturn(builder).`when`(out).createStringBuilder()
        Mockito.doReturn(times).`when`(out).toMutableList()
        Mockito.doNothing().`when`(out).sort(times)
        val header = "header"
        Mockito.doReturn(header).`when`(out).composeHeader()
        val rowString0 = "rowString0"
        val rowString1 = "rowString1"
        Mockito.doReturn(rowString0).`when`(out).composeRowData(t0)
        Mockito.doReturn(rowString1).`when`(out).composeRowData(t1)
        Mockito.doNothing().`when`(out).writeToFile(builder)
        // Run method under test
        out.run()
        // Verify
        Mockito.verify(out).toMutableList()
        Mockito.verify(out).sort(times)
        Mockito.verify(out).createStringBuilder()
        Mockito.verify(out).composeRowData(t0)
        Mockito.verify(out).composeRowData(t1)
        Mockito.verify(out).writeToFile(builder)
        Assertions.assertThat(builder.toString()).isEqualTo(header + rowString0 + rowString1)
    }
    @Test
    fun composeHeader() {
        // Prepare
        val simData = HashMap<DateTime, Sim1aResultsRow>()
        val targetFileName = "targetFileName"
        val sim1Name = "Scenario 1"
        val sim2Name = "Scenario 2"
        val out = Mockito.spy(
                Sim1aTimeSeriesCreator(
                        simData,
                        targetFileName,
                        listOf(sim1Name, sim2Name)
                )
        )
        // Run method under test
        val header = out.composeHeader()
        // Verify
        Assertions.assertThat(header).isEqualTo(
"""t;"Scenario 1: Number of people willing to meet";"Scenario 1: Number of people willing to recommend my friend";"Scenario 1: Number of people my friend had an offline networking session with";"Scenario 1: Number of people willing to purchase my friend's services, if need arises";"Scenario 2: Number of people willing to meet";"Scenario 2: Number of people willing to recommend my friend";"Scenario 2: Number of people my friend had an offline networking session with";"Scenario 2: Number of people willing to purchase my friend's services, if need arises";
"""
        );
    }
}