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

package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.getResults

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class CopiesOfSoftwareAtTargetAudienceColFunction : ITimeSeriesFieldFillerFunction {
    override fun invoke(prolog: Prolog, time: Long): String =
            prolog.getResults(
                    "resourceLevel($time, list, r5, Amount).",
                    "Amount"
            ).first()
}
