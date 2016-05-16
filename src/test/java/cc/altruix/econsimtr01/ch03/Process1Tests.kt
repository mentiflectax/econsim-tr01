package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Process1Tests {
    @Test
    fun timeToRunWiring() {
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = false,
                evenHourAndMinute = false,
                fieldNotNull = false,
                expectedResult = false)
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = true,
                evenHourAndMinute = false,
                fieldNotNull = false,
                expectedResult = false)
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = false,
                evenHourAndMinute = true,
                fieldNotNull = false,
                expectedResult = false)
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = false,
                evenHourAndMinute = false,
                fieldNotNull = true,
                expectedResult = false)
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = true,
                evenHourAndMinute = true,
                fieldNotNull = true,
                expectedResult = true)
    }
    fun timeToRunWiringTestLogic(timeBetweenStartAndEnd:Boolean,
                                 evenHourAndMinute:Boolean,
                                 fieldNotNull:Boolean,
                                 expectedResult:Boolean) {
        // Prepare
        val data = Properties()
        data["Process1Start"] = "30.08"
        data["Process1End"] = "30.10"
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(data)
        simParamProv.initAndValidate()
        val field = Field(simParamProv)
        simParamProv.agents.add(field)
        val out = Mockito.spy(Process1(simParamProv))
        val time = 0L.millisToSimulationDateTime()
        Mockito.doReturn(timeBetweenStartAndEnd).`when`(out)
                .timeBetweenStartAndEnd(time)
        Mockito.doReturn(evenHourAndMinute).`when`(out).evenHourAndMinute(time)
        Mockito.doReturn(fieldNotNull).`when`(out).fieldNotFull(field)
        // Run method under test
        val res = out.timeToRun(time)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }
    @Test
    fun initFindsField() {
        val data = Properties()
        data["Process1Start"] = "30.08"
        data["Process1End"] = "30.10"
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(data)
        simParamProv.initAndValidate()
        val field = Field(simParamProv)
        simParamProv.agents.add(field)
        simParamProv.agents.add(Farmers(simParamProv))
        val out = Process1(simParamProv)
        Assertions.assertThat(out.field).isSameAs(field)
    }
}
