/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.AbstractAccountant2
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ch0202.SimResRow
import org.joda.time.DateTime

/**
 * Created by pisarenko on 17.05.2016.
 */
class AgriculturalSimulationAccountant(resultsStorage: MutableMap<DateTime,
        SimResRow<AgriculturalSimulationRowField>>,
                                       scenarioName: String) :
        AbstractAccountant2<AgriculturalSimulationRowField>(resultsStorage,
                scenarioName) {
    override fun saveRowData(agents: List<IAgent>,
                         target: MutableMap<AgriculturalSimulationRowField,
                                 Double>) {
        target.put(AgriculturalSimulationRowField.SEEDS_IN_SHACK,
                calculateSeedsInShack(agents))
        target.put(AgriculturalSimulationRowField.FIELD_AREA_WITH_SEEDS,
                calculateFieldAreaWithSeeds(agents))
        target.put(AgriculturalSimulationRowField.EMPTY_FIELD_AREA,
                calculateEmptyFieldArea(agents))
        target.put(AgriculturalSimulationRowField.FIELD_AREA_WITH_CROP,
                calculateFieldAreaWithCrop(agents))
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    private fun calculateFieldAreaWithCrop(agents: List<IAgent>): Double {
// TODO: Implement this
// TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun calculateEmptyFieldArea(agents: List<IAgent>): Double {
// TODO: Implement this
// TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun calculateFieldAreaWithSeeds(agents: List<IAgent>): Double {
// TODO: Implement this
// TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun calculateSeedsInShack(agents: List<IAgent>): Double {
// TODO: Implement this
// TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}