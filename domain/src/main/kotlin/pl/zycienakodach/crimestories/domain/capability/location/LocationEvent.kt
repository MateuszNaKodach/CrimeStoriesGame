package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.shared.DomainEvent

interface LocationEvent : DomainEvent {
    val locationId: LocationId
}

data class CharacterArrived(val at: LocationId, val who: CharacterId) : LocationEvent {
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


