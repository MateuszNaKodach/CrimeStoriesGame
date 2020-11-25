package pl.zycienakodach.crimestories.scenarios

import pl.zycienakodach.crimestories.domain.capability.character.AskAboutItem
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.LetsChatWith
import pl.zycienakodach.crimestories.domain.capability.item.wasFoundBy
import pl.zycienakodach.crimestories.domain.operations.scenario.ScenarioCharacter
import pl.zycienakodach.crimestories.domain.operations.scenario.wasKilled
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.inThe

//TODO: Remove ifs to something like assert in event gwt
val alice: ScenarioCharacter = CharacterId("Alice") to { command, history ->
    when (command) {
        is AskAboutItem -> {
            if (command.askAbout === Knife.id) {
                if (Knife.wasFoundBy(command.askedBy).inThe(history)) {
                    CommandResult.onlyMessage("Alice: Oh! This knife belongs to my brother.")
                } else {
                    CommandResult.onlyMessage("Alice: You cannot ask about item which you have not found.")
                }
            }
        }
    }
    CommandResult.onlyMessage("Super!")
}

val policemanId = CharacterId("Policeman")
val policeman: ScenarioCharacter = policemanId to { command, history ->
    val knifeWasFound = Knife.wasFoundBy(command.askedBy)
    when (knifeWasFound.inThe(history)) {
        true -> CommandResult(
            event = knifeWasFound,
            storyMessage = "Policeman: I've found this knife in this apartment."
        )
        false -> CommandResult.onlyMessage("Policeman: Do you know who left the DNA on the knife?")
    }
}

val harryId = CharacterId("Harry")
val harry: ScenarioCharacter = harryId to { _, history ->
    when (harryId.wasKilled.inThe(history)) {
        true -> CommandResult.onlyMessage("It's the body of the victim. His name was Harry.")
        false -> CommandResult.onlyMessage("Harry: Hello! How can I help you?")
    }
}

val labTechnicianJohn: ScenarioCharacter = CharacterId("LabTechnician") to { command, history ->
    when (command) {
        is LetsChatWith -> CommandResult.onlyMessage("John: I can tell you something about everything and everything about something.")
        is AskAboutItem -> when (command.askAbout) {
            Knife.id -> when (Knife.wasFoundBy(command.askedBy).inThe(history)) {
                true -> CommandResult.onlyMessage("John: I need to about 1 hour to investigate this.")
                else -> CommandResult.onlyMessage("John: You must bring it to me.")
            }
            else -> CommandResult.onlyMessage("John: I don't know anything about that.")
        }
    }
    CommandResult.onlyMessage("John: I cannot help you with that.")
}
