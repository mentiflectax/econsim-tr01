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

import cc.altruix.econsimtr01.TimeSeriesCreator
import cc.altruix.econsimtr01.ch0202.SimResRow
import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class AgriculturalSimulationTimeSeriesCreator(simData: Map<DateTime, SimResRow<AgriculturalSimulationRowField>>,
                                              targetFileName: String,
                                              simNames: List<String>) :
        TimeSeriesCreator<AgriculturalSimulationRowField>(
                simData,
                targetFileName,
                simNames,
                AgriculturalSimulationRowField.values(),
                AgriculturalSimulationRowField.values()) {

}
