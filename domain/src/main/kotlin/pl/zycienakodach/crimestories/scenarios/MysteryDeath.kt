package pl.zycienakodach.crimestories.scenarios

import pl.zycienakodach.crimestories.domain.capability.character.AskAboutItem
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.CharacterBehaviour
import pl.zycienakodach.crimestories.domain.capability.character.LetsChatWith
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
                    CommandResult.onlyMessage("Alice: Oh! This knife belongs to my brother.")
                } else {
                    CommandResult.onlyMessage("Alice: You cannot ask about item which you have not found.")
                }
            }
        }
    }
    CommandResult.onlyMessage("Super!")
}

private val policeman: ScenarioCharacter = CharacterId("Policeman") to { command, history ->
    val knifeWasFound = knife.wasFoundBy(command.askedBy)
    when (knifeWasFound.inThe(history)) {
        true -> CommandResult(
            event = knifeWasFound,
            storyMessage = "Policeman: I've found this knife in this apartment."
        )
        false -> CommandResult.onlyMessage("Policeman: Do you know who left the DNA on the knife?")
    }
}

private val harry: ScenarioCharacter = CharacterId("Harry") to { _, _ ->
    CommandResult.onlyMessage("It's the body of the victim. His name was Harry.")
}


private val labTechnicianJohn: ScenarioCharacter = CharacterId("LabTechnician") to { command, history ->
    when (command) {
        is LetsChatWith -> CommandResult.onlyMessage("John: I can tell you something about everything and everything about something.")
        is AskAboutItem -> when (val itemId = command.askAbout) {
            knife.id -> when (knife.wasFoundBy(command.askedBy).inThe(history)) {
                true -> CommandResult.onlyMessage("John: I need to about 1 hour to investigate this.")
                else -> CommandResult.onlyMessage("John: You must bring it to me.")
            }
            else -> CommandResult.onlyMessage("John: I don't know anything about that.")
        }
    }
    CommandResult.onlyMessage("John: I cannot help you with that.")
}


private val knife = Knife()
private val clothes = Clothes()


class MysteryDeathScenario : Scenario(
    scenarioId = ScenarioId("ScenarioId"),
    characters = mapOf(alice),
    items = listOf(knife),
    history = listOf(
        
    )
)

val mysteryDeathScenario = MysteryDeathScenario()
