package pl.zycienakodach.crimestories.domain.operations.scenario

import Characters
import pl.zycienakodach.crimestories.domain.capability.character.CharacterCommand
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.character
import pl.zycienakodach.crimestories.domain.capability.item.Item
import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.DomainEvents
import pl.zycienakodach.crimestories.domain.shared.ICommandResult

val notFoundCharacter = character(CharacterId("NotFound")) { _, _ ->
    CommandResult.onlyMessage("This character not found!")
}

abstract class Scenario(
    val characters: Characters,
    val items: List<Item>
) {


}
