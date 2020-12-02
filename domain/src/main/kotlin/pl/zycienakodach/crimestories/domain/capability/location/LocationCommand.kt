package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.shared.Command

abstract class LocationCommand(override val detectiveId: DetectiveId) : Command {
    abstract val locationId: LocationId
}

class SearchCrimeScene(detectiveId: DetectiveId, val at:LocationId) : LocationCommand(detectiveId){
    override val locationId: LocationId
        get() = at
}

class SecureTheEvidence(detectiveId: DetectiveId, val at: LocationId, val itemId: ItemId) : LocationCommand(detectiveId){
    override val locationId: LocationId
        get() = at
}

class VisitLocation(detectiveId: DetectiveId, val where: LocationId) : LocationCommand(detectiveId){
    override val locationId: LocationId
        get() = where
}
