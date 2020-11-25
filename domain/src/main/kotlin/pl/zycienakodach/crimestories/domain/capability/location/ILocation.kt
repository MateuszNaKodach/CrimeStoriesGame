package pl.zycienakodach.crimestories.domain.capability.location

interface ILocation {
    val id: LocationId
    val name: String
}

data class Location(override val id: LocationId, override val name: String) : ILocation{
    override fun toString(): String = id.raw
}

object Unknown : ILocation {
    override val id: LocationId
        get() = LocationId("Unknown")

    override val name: String
        get() = "Unknown"

    override fun toString(): String = "Unknown"
}
