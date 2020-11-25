package pl.zycienakodach.crimestories.domain.capability.location

interface ILocation {
    val id: LocationId
    val name: String
}

class Location(override val id: LocationId, override val name: String): ILocation
