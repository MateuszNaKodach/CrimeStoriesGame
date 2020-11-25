package pl.zycienakodach.crimestories.domain.operations.scenario

import Characters
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.character
import pl.zycienakodach.crimestories.domain.capability.item.Item
import pl.zycienakodach.crimestories.domain.shared.*

val notFoundCharacter = character(CharacterId("NotFound")) { _, _ ->
    CommandResult.onlyMessage("This character not found!")
}

typealias ChainReactions = Map<DomainEvent, DomainEvent>

abstract class Scenario(
    val scenarioId: ScenarioId,
    val characters: Characters,
    val items: List<Item>,
    val chainReactions: ChainReactions
) {


}
