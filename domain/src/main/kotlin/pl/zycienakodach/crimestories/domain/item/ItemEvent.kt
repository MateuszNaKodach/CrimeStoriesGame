package pl.zycienakodach.crimestories.domain.item

import pl.zycienakodach.crimestories.domain.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.shared.DomainEvent

typealias ItemEvents = List<ItemEvent>

fun List<DomainEvent>.itemEvents(itemId: ItemId? = null): ItemEvents =
    this.filterIsInstance<ItemEvent>()
        .filter { itemId?.equals(it.itemId) ?: true }

interface ItemEvent : DomainEvent{
    val itemId: ItemId
}

class ItemWasFound(override val itemId: ItemId, val detectiveId: DetectiveId): ItemEvent
class ItemWasLost(override val itemId: ItemId, val detectiveId: DetectiveId): ItemEvent
