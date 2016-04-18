package cc.altruix.econsimtr01

import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 04.04.2016.
 */
abstract class DefaultSimulation(val timing : ITiming,
                                 val simParametersProvider: ISimParametersProvider) : ISimulation {
    val LOGGER = LoggerFactory.getLogger(DefaultSimulation::class.java)
    override fun run():SimResults {
        val results = SimResults()
        val agents = createAgents()
        val sensors = createSensors()
        var time = 0L.millisToSimulationDateTime()
        //var lastTick = 0L
        // TODO: Verify that we tick every 1 second
        while (timing.gotFuture() && continueCondition(time)) {
            //lastTick = timing.tick()
            //val time = t0().plus(lastTick*1000L)
            time = time.plusSeconds(1)
            agents.forEach { x -> x.act(time) }
            sensors.forEach { x -> x.measure(time) }
        }
        return results

    }

    internal abstract fun continueCondition(tick: DateTime): Boolean
    protected abstract fun createAgents(): List<IAgent>
    protected abstract fun createSensors(): List<ISensor>
    fun findAgent(agentId: String) =
            simParametersProvider.agents.filter { x -> x.id().equals(agentId) }.first()

    protected fun attachFlowToAgent(agents: List<IAgent>, flow: PlFlow, flows:MutableList<ResourceFlow>) {
        val agent = simParametersProvider.agents
                .filter { x -> x.id().equals(flow.src) }
                .first()
        if (agent == null) {
            LOGGER.error("Can't find process ${flow.src}")
        } else {
            flow.agents = agents
            flow.flows = flows

            if (agent is DefaultAgent) {
                agent.addAction(flow)
            }
        }
    }

    protected fun attachFlowsToAgents(plFlows: MutableList<PlFlow>,
                                      agents: MutableList<IAgent>,
                                      flows: MutableList<ResourceFlow>) {
        plFlows.forEach { flow ->
            attachFlowToAgent(
                    agents,
                    flow,
                    flows
            )
        }
    }

    protected fun setInfiniteResourceSupplies() {
        simParametersProvider.infiniteResourceSupplies.forEach { infiniteResourceSupply ->
            val agent = findAgent(infiniteResourceSupply.agent)
            if (agent is DefaultAgent) {
                agent.setInfinite(infiniteResourceSupply.res)
            }
        }
    }

    open protected fun setInitialResourceLevels() {
        simParametersProvider.initialResourceLevels
                .forEach { initialResourceLevel ->
            val agent = findAgent(initialResourceLevel.agent)
            if ((agent != null) && (agent is DefaultAgent)) {
                agent.put(initialResourceLevel.resource, initialResourceLevel.amt)
            } else {
                LOGGER.error("Can't find agent '${initialResourceLevel.agent}'")
            }
        }
    }
}
