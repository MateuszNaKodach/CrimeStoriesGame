package pl.zycienakodach.crimestories.domain.scenario

import Characters
import pl.zycienakodach.crimestories.domain.character.CharacterCommand
import pl.zycienakodach.crimestories.domain.character.CharacterId
import pl.zycienakodach.crimestories.domain.character.character
import pl.zycienakodach.crimestories.domain.item.Item
import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.DomainEvents
import pl.zycienakodach.crimestories.domain.shared.ICommandResult

val notFoundCharacter = character(CharacterId("NotFound")) { _, _ ->
    CommandResult.onlyMessage("This character not found!")
}

abstract class Scenario(
    private val characters: Characters,
    private val items: List<Item>
) {

    

    fun investigate(command: Command, investigationHistory: DomainEvents): ICommandResult =
        when (command) {
            is CharacterCommand -> characters.getOrDefault(command.characterId, notFoundCharacter)(
                command,
                investigationHistory
            )
            else -> CommandResult.onlyMessage("You cannot do that!")
        }

}
