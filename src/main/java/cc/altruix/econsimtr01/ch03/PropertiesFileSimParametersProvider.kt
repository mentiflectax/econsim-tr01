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

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISimParametersProvider
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.PlTransformation
import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
abstract open class PropertiesFileSimParametersProvider(val file: File) :
        ISimParametersProvider {
    override val agents:MutableList<IAgent> = LinkedList()
        get
    override val flows:MutableList<PlFlow> = LinkedList()
        get

    override val initialResourceLevels:MutableList<InitialResourceLevel> =
            LinkedList()
        get
    override val infiniteResourceSupplies:MutableList<InfiniteResourceSupply> =
            LinkedList()
        get
    override val transformations:MutableList<PlTransformation> = LinkedList()
        get
    lateinit var validity:ValidationResult
        get
    lateinit var data:Properties

    open fun initAndValidate() {
        val validators = createValidators()
        data = loadData()
        val valResults = createValResults()
        validators.entries.forEach { entry ->
            applyValidators(data, valResults, entry.key, entry.value)
        }
        val valid = calculateValidity(valResults)
        var message = createMessage(valResults, valid)
        validity = ValidationResult(valid, message)
    }

    open internal fun createMessage(valResults: List<ValidationResult>,
                                    valid: Boolean): String {
        var message = ""
        if (!valid) {
            message = valResults.filter { it.valid == false }
                    .map { it.message }.joinToString(separator = ", ")
        }
        return message
    }

    open internal fun calculateValidity(valResults: List<ValidationResult>) =
            valResults.filter { it.valid == false }.count() < 1

    open internal fun applyValidators(data: Properties,
                                      valResults: MutableList<ValidationResult>,
                                      parameter: String,
                                      parameterValidators:
                                      List<IPropertiesFileValueValidator>) {
        for (validator in parameterValidators) {
            val vres = validator.validate(data, parameter)
            if (!vres.valid) {
                valResults.add(vres)
                break;
            }
        }
    }

    open internal fun createValResults():MutableList<ValidationResult> =
            LinkedList<ValidationResult>()

    open internal fun loadData(): Properties {
        val data = Properties()
        data.load(file.reader())
        return data
    }

    abstract fun createValidators():
            Map<String,List<IPropertiesFileValueValidator>>
}