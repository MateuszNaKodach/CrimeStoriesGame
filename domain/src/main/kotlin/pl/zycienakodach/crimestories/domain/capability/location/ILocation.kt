package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.capability.item.Item

interface ILocation {
    val name: String
    val items: List<Item>
}

abstract class Location(override val name: String, override val items: List<Item>): ILocation
