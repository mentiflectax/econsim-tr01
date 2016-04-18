package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1(val logTarget:StringBuilder,
            val flows:MutableList<ResourceFlow>,
            simParametersProvider: Sim1ParametersProvider) :
        DefaultSimulation(Timing(),
                simParametersProvider) {
    override fun continueCondition(time: DateTime): Boolean = (time.monthOfYear <= 3)

    override fun createAgents(): MutableList<IAgent> {
        attachFlowsToAgents(
                (simParametersProvider as Sim1ParametersProvider).flows,
                simParametersProvider.agents,
                this.flows)
        setInitialResourceLevels()
        setInfiniteResourceSupplies()

        return simParametersProvider.agents
    }

    override fun createSensors(): List<ISensor> =
            listOf(
                    Sim1Accountant(
                            logTarget,
                            simParametersProvider.agents,
                            (simParametersProvider as Sim1ParametersProvider).resources
                    )
            )
}
