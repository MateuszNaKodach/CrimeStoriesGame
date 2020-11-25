package pl.zycienakodach.crimestories.domain.capability.item

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.shared.DomainEvent

typealias ItemEvents = List<ItemEvent>

fun List<DomainEvent>.itemEvents(itemId: ItemId? = null): ItemEvents =
    this.filterIsInstance<ItemEvent>()
        .filter { itemId?.equals(it.itemId) ?: true }

interface ItemEvent : DomainEvent{
    val itemId: ItemId
}

data class ItemWasFound(override val itemId: ItemId, val detectiveId: DetectiveId): ItemEvent
data class ItemWasLost(override val itemId: ItemId, val detectiveId: DetectiveId): ItemEvent
