/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.calculateBusinessDays
import cc.altruix.econsimtr01.createCorrectValidationResult
import cc.altruix.econsimtr01.createIncorrectValidationResult
import cc.altruix.econsimtr01.parseDayMonthString

/**
 * Created by pisarenko on 15.05.2016.
 */
open class EnoughCapacityForHarvesting :
        ISemanticSimulationParametersValidator {
    open override fun validate(scenario: PropertiesFileSimParametersProvider)
            : ValidationResult {
        val requiredEffort = calculateRequiredEffort(scenario)
        val availableTime = calculateAvailableTime(scenario)
        if (requiredEffort > availableTime) {
            return createIncorrectValidationResult(
                    "Process 3: It requires $requiredEffort hours of " +
                            "effort, but only $availableTime is available")
        }
        return createCorrectValidationResult()
    }

    open internal fun calculateAvailableTime(
            scenario: PropertiesFileSimParametersProvider): Double {
        val start = scenario.data["Process2End"].toString().parseDayMonthString()
        val end = scenario.data["Process3End"].toString().parseDayMonthString()
        val businessDays = calculateBusinessDays(start, end)
        val numberOfWorkers =
                scenario.data["NumberOfWorkers"].toString().toDouble()
        val workingTimePerBusinessDay =
                scenario.data["LaborPerBusinessDay"].toString().toDouble()
        // TODO: Test this
        return businessDays * numberOfWorkers * workingTimePerBusinessDay
    }

    open internal fun calculateRequiredEffort(scenario:
                                  PropertiesFileSimParametersProvider): Double
    {
        val fieldSize = scenario.data["SizeOfField"].toString().toDouble()
        val effortPerSquareMeter = scenario
                .data["Process3EffortPerSquareMeter"].toString().toDouble()
        return fieldSize * effortPerSquareMeter
    }
}