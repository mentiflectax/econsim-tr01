/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 19.04.2016.
 */
open class Sim4(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim4ParametersProvider) :
    DefaultSimulation(simParametersProvider){
    override fun continueCondition(time: DateTime): Boolean = (time.year <= 1)

    override fun createAgents(): List<IAgent> {
        attachFlowsToAgents(
                simParametersProvider.flows,
                simParametersProvider.agents,
                this.flows)
        attachTransformationsToAgents(
                simParametersProvider.transformations,
                simParametersProvider.agents
        )
        setInitialResourceLevels()
        setInfiniteResourceSupplies()
        return simParametersProvider.agents
    }

    internal open fun attachTransformationsToAgents(
            trs: MutableList<PlTransformation>,
            agents: List<IAgent>) {
        trs.forEach { attachTransformationToAgent(agents, it) }
    }

    internal open fun attachTransformationToAgent(agents: List<IAgent>, tr: PlTransformation) {
        val agent = findAgent(agents, tr.agentId)
        if (agent == null) {
            LOGGER.error("Can't find agent ${tr.agentId}")
            return
        }
        tr.agents = agents
        if (agent is DefaultAgent) {
            agent.addTransformation(tr)
        }
    }

    open internal fun findAgent(agents: List<IAgent>, agentId: String) =
            agents.filter { it.id() == agentId }.firstOrNull()

    override fun createSensors(): List<ISensor> =
            listOf(Sim4Accountant(
                    logTarget,
                    simParametersProvider.agents,
                    (simParametersProvider as Sim4ParametersProvider).resources)
            )
}