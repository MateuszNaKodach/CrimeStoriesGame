package pl.zycienakodach.crimestories.scenarios

import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.HumanCharacter
import pl.zycienakodach.crimestories.domain.capability.character.character
import pl.zycienakodach.crimestories.domain.shared.CommandResult

val alice: HumanCharacter = character(CharacterId("Alice")) { command, history ->
    CommandResult.onlyMessage("Super!")
}

val characters = listOf<HumanCharacter>(alice)
