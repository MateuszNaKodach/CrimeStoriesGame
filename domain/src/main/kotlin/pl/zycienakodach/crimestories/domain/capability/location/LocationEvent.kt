package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveMoved
import pl.zycienakodach.crimestories.domain.capability.item.Item
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.capability.time.MinutesHasPassed
import pl.zycienakodach.crimestories.domain.capability.time.TimeHasCome
import pl.zycienakodach.crimestories.domain.operations.scenario.ScenarioCharacter
import pl.zycienakodach.crimestories.domain.policy.investigation.Investigation
import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import java.time.LocalDateTime

interface LocationEvent : DomainEvent {
    val locationId: LocationId
}

data class CharacterHasArrived(val at: LocationId, val who: CharacterId) : LocationEvent {
    override val locationId: LocationId
        get() = at
}

data class CharacterHasGone(val from: LocationId, val who: CharacterId) : LocationEvent {
    override val locationId: LocationId
        get() = from
}

data class ItemHasLeft(val at: LocationId, val item: ItemId) : LocationEvent {
    override val locationId: LocationId
        get() = at
}

fun Item.hasLeft(at: Location) = ItemHasLeft(at = at.id, item = this.id)

fun ItemId.hasLeft(at: LocationId) = ItemHasLeft(at = at, item = this)

fun ScenarioCharacter.hasArrived(at: Location) = CharacterHasArrived(at.id, this.first)

fun ScenarioCharacter.hasGone(from: Location) = CharacterHasGone(from = from.id, this.first)

fun CharacterId.hasArrived(at: LocationId) = CharacterHasArrived(at, this)


fun Investigation.detectiveLocation(): LocationId =
    this.history
        .fold(Unknown.id) { acc, domainEvent ->
            when (domainEvent) {
                is DetectiveMoved -> domainEvent.to
                else -> acc
            }
        }
