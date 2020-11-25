package pl.zycienakodach.crimestories.domain.capability.item

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId

interface Item {
    val id: ItemId
}

//TODO: Change to objects?
data class Knife(override val id: ItemId = ItemId("Knife")): Item
data class Clothes(override val id: ItemId = ItemId("Clothes")): Item


fun Item.wasFoundBy(detective: DetectiveId) = ItemWasFound(itemId = this.id, detectiveId = detective)
