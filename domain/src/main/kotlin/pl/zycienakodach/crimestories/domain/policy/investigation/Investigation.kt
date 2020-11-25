package pl.zycienakodach.crimestories.domain.policy.investigation

import pl.zycienakodach.crimestories.domain.capability.character.CharacterCommand
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.operations.scenario.notFoundCharacter
import pl.zycienakodach.crimestories.domain.shared.*

/**
 * Investigation is played scenario.
 */
abstract class Investigation(private val scenario: Scenario) : HasEvents, HasCommands, HasCommandsResults {

    var history: DomainEvents = listOf()

    fun investigate(command: Command): ICommandResult{
        val result = this.scenario.investigate(command, events)
        history = history.plus(result.events)
        return result
    }

    private fun Scenario.investigate(command: Command, investigationHistory: DomainEvents): ICommandResult =
        when (command) {
            is CharacterCommand -> this.characters.getOrDefault(command.characterId, notFoundCharacter)(
                command,
                investigationHistory
            )
            else -> CommandResult.onlyMessage("You cannot do that!")
        }

    override val events: DomainEvents
        get() = history
}

