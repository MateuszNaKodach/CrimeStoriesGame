package pl.zycienakodach.crimestories.domain.capability.item

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId

interface Item {
    val id: ItemId
}

abstract class AbstractItem(override val id: ItemId): Item


fun Item.wasFoundBy(detective: DetectiveId) = ItemWasFound(itemId = this.id, detectiveId = detective)
