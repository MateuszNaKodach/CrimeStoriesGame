package pl.zycienakodach.crimestories.scenarios

import pl.zycienakodach.crimestories.domain.character.Character
import pl.zycienakodach.crimestories.domain.character.CharacterId
import pl.zycienakodach.crimestories.domain.character.HumanCharacter
import pl.zycienakodach.crimestories.domain.character.character
import pl.zycienakodach.crimestories.domain.shared.CommandResult

val alice: HumanCharacter = character(CharacterId("Alice")) { command, history ->
    CommandResult.onlyMessage("Super!")
}

val characters = listOf<HumanCharacter>(alice)
