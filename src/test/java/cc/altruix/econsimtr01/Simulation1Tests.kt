package cc.altruix.econsimtr01

import org.apache.commons.io.FileUtils
import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1Tests {
    @Test
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()

        val sim = Simulation1(
                log,
                flows,
                SimParametersProvider(
                        File("src/test/resources/Simulation1.params.pl").readText()
                )
        )

        // Run method under test
        sim.run()

        // Verify
        val expectedRawSimResultsFile = File("src/test/resources/Simulation1Tests.test.pl.expected.txt")
        val expectedRawSimResults = expectedRawSimResultsFile.readText()
        Assertions.assertThat(log.toString()).isEqualTo(expectedRawSimResults)

        val actualConvertedSimResults = Simulation1TimeSeriesCreator().prologToCsv(expectedRawSimResultsFile)
        val expectedConvertedSimResults = File("src/test/resources/Simulation1Tests.test.csv.expected.txt").readText()
        Assertions.assertThat(actualConvertedSimResults).isEqualTo(expectedConvertedSimResults)

        val seqDiagramTxt = FlowDiagramTextCreator().createFlowDiagramText(flows)
        seqDiagramTxt.toSequenceDiagramFile(File("src/test/resources/Simulation1Tests.test.flows.actual.png"))
    }
}