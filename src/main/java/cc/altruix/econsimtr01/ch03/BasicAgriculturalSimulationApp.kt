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

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.ITimeProvider
import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.TimeProvider
import cc.altruix.econsimtr01.ch0202.SimResRow
import org.joda.time.DateTime
import java.io.PrintStream
import java.util.*

/**
 * Created by pisarenko on 13.05.2016.
 */
class BasicAgriculturalSimulationApp(
        val cmdLineParamValidator:CmdLineParametersValidator =
            CmdLineParametersValidator(),
        val timeProvider:ITimeProvider = TimeProvider(),
        val targetDir:String = System.getProperty("user.dir")
) {
    fun run(args: Array<String>,
            out: PrintStream,
            err: PrintStream) {
        val cmdLineParamValRes = cmdLineParamValidator.validate(args)
        if (!cmdLineParamValRes.valid) {
            err.println(cmdLineParamValRes.message)
            return
        }
        val validators = createSemanticValidators()
        val scenarios = cmdLineParamValidator.simParamProviders
        val valRes = LinkedList<ValidationResult>()
        scenarios.forEach { scenario ->
            validators.map { it.validate(scenario) }.forEach { valRes.add(it) }
        }
        val error = valRes.find { it.valid == false }
        if (error != null) {
            val allErrors = valRes.filter { it.valid == false }
                    .map { it.message }
                    .joinToString(separator = ", ")
            err.println("One or more scenarios are invalid:")
            err.println(allErrors)
            return
        }
        val simResults = HashMap<DateTime,
                SimResRow<AgriculturalSimulationRowField>>()
        val scenarioResults = scenarios
                .map { it as AgriculturalSimParametersProvider }
                .map {
                    BasicAgriculturalSimulation(
                        logTarget = StringBuilder(),
                        flows = ArrayList<ResourceFlow>(),
                        simParametersProvider = it,
                        resultsStorage = simResults
                    )
                }
        scenarioResults.forEach {
            it.run()
        }
        val targetFileName = composeTargetFileName()
        val simNames = scenarios.map { it.data["SimulationName"].toString() }
                .toList()
        val timeSeriesCreator = AgriculturalSimulationTimeSeriesCreator(
                simResults,
                targetFileName,
                simNames)
        timeSeriesCreator.run()
    }

    internal open fun composeTargetFileName(): String =
            "$targetDir/agriculture-${timeProvider.now().millis}.csv"

    fun createSemanticValidators():List<ISemanticSimulationParametersValidator>
            = listOf(
                    EnoughCapacityForPuttingSeedsIntoGround(),
                    EnoughCapacityForHarvesting(),
                    OneDateBeforeOtherValidator("Process1Start", "Process1End"),
                    OneDateBeforeOtherValidator("Process2End", "Process3End"),
                    EnoughSeedsAtTheStartValidator()
            )
}
fun main(args : Array<String>) {
    BasicAgriculturalSimulationApp().run(args, System.out, System.err)
    println("Basic agriculture simulation")
    println("(C) Copyright 2016 Dmitri Pisarenko")
}
