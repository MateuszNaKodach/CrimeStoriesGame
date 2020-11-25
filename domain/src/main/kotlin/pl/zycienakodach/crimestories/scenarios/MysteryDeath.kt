package pl.zycienakodach.crimestories.scenarios

import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.CharacterBehaviour
import pl.zycienakodach.crimestories.domain.capability.item.ItemWasFound
import pl.zycienakodach.crimestories.domain.capability.item.Knife
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.operations.scenario.ScenarioId
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.occurred

typealias ScenarioCharacter = Pair<CharacterId, CharacterBehaviour>

private val alice: ScenarioCharacter = CharacterId("Alice") to { command, history ->
    CommandResult.onlyMessage("Super!")
}

private val policeman: ScenarioCharacter = CharacterId("Policeman") to { command, history ->
    val knifeWasFound = ItemWasFound(itemId = knife.id, detectiveId = command.askedBy)
    when (history.occurred(knifeWasFound)) {
        true -> CommandResult(event = knifeWasFound, storyMessage = "I've found this knife in this apartment.")
        false -> CommandResult.onlyMessage("Do you know who left the DNA on the knife?")
    }
}

private val harry: ScenarioCharacter = CharacterId("Harry") to { _, _ ->
    CommandResult.onlyMessage("It's the body of the victim.")
}


private val knife: Knife = Knife()


class MysteryDeathScenario : Scenario(
    scenarioId = ScenarioId("ScenarioId"),
    characters = mapOf(alice),
    items = listOf(knife)
)

val mysteryDeathScenario = MysteryDeathScenario()
