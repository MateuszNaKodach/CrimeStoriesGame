package pl.zycienakodach.crimestories.domain.policy.investigation

import pl.zycienakodach.crimestories.domain.capability.character.CharacterCommand
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.operations.scenario.notFoundCharacter
import pl.zycienakodach.crimestories.domain.shared.*

/**
 * Investigation is played scenario.
 */
abstract class Investigation(private val scenario: Scenario, var history: DomainEvents = listOf()) {

    init {
        history = scenario.history
    }

    open fun investigate(command: Command): ICommandResult {
        val result = this.scenario.investigate(command, history)
        history = history.plus(result.events)
        return result
    }

    private fun Scenario.investigate(command: Command, investigationHistory: DomainEvents): ICommandResult =
        when (command) {
            is CharacterCommand -> this.characters.getOrDefault(command.ask, notFoundCharacter)(
                command,
                investigationHistory
            )
            else -> CommandResult.onlyMessage("You cannot do that!")
        }

}

