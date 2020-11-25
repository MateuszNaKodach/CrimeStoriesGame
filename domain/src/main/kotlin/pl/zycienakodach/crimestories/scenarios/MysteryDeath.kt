package pl.zycienakodach.crimestories.scenarios

import pl.zycienakodach.crimestories.domain.capability.character.AskAboutItem
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.CharacterBehaviour
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.*
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.operations.scenario.ScenarioId
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.inThe

typealias ScenarioCharacter = Pair<CharacterId, CharacterBehaviour>

//TODO: Remove ifs to something like assert in event gwt
private val alice: ScenarioCharacter = CharacterId("Alice") to { command, history ->
    when (command) {
        is AskAboutItem -> {
            if (command.askAbout === knife.id) {
                if (knife.wasFoundBy(command.askedBy).inThe(history)) {
                    CommandResult.onlyMessage("Oh! This knife belongs to my brother.")
                } else {
                    CommandResult.onlyMessage("You cannot ask about item which you have not found.")
                }
            }
        }
    }
    CommandResult.onlyMessage("Super!")
}

private val policeman: ScenarioCharacter = CharacterId("Policeman") to { command, history ->
    val knifeWasFound = knife.wasFoundBy(command.askedBy)
    when (knifeWasFound.inThe(history)) {
        true -> CommandResult(event = knifeWasFound, storyMessage = "I've found this knife in this apartment.")
        false -> CommandResult.onlyMessage("Do you know who left the DNA on the knife?")
    }
}

private val harry: ScenarioCharacter = CharacterId("Harry") to { _, _ ->
    CommandResult.onlyMessage("It's the body of the victim.")
}


private val labTechnician: ScenarioCharacter = CharacterId("LabTechnician") to { command, history ->
    when(command){
        is AskAboutItem -> {

        }
    }
    CommandResult.onlyMessage("I cannot help you with that.")
}


private val knife = Knife()
private val clothes = Clothes()


class MysteryDeathScenario : Scenario(
    scenarioId = ScenarioId("ScenarioId"),
    characters = mapOf(alice),
    items = listOf(knife)
)

val mysteryDeathScenario = MysteryDeathScenario()
