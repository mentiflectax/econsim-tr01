/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.DefaultAgent
import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
class AgriculturalSimParametersProviderTests {
    @Test
    fun ryeSimulationParametersCorrect() {
        simulationParametersCorrectnessTestLogic(
                "BasicAgriculturalSimulationRye.properties"
        )
    }
    @Test
    fun wheatSimulationParametersCorrect() {
        simulationParametersCorrectnessTestLogic(
                "BasicAgriculturalSimulationWheat.properties"
        )
    }
    private fun simulationParametersCorrectnessTestLogic(fileName: String) {
        val out = AgriculturalSimParametersProvider(
                File("src/test/resources/ch03/$fileName")
        )
        out.initAndValidate()
        Assertions.assertThat(out.validity.message).isEmpty()
        Assertions.assertThat(out.validity.valid).isTrue()
        Assertions.assertThat(out.agents.size).isEqualTo(3)
        val farmers = out.agents.find { it.id() == Farmers.ID} as DefaultAgent
        Assertions.assertThat(farmers.actions.size).isEqualTo(2)
        val field = out.agents.find { it.id() == Field.ID } as DefaultAgent
        Assertions.assertThat(field.actions.size).isEqualTo(1)
        Assertions.assertThat(out.agents.find { it.id() == Shack.ID }).isNotNull
    }
}
