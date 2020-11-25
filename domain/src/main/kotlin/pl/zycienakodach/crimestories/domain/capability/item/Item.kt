package pl.zycienakodach.crimestories.domain.capability.item

interface Item {
    val id: ItemId
}

class Knife(override val id: ItemId = ItemId("Knife")): Item
