package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.shared.Command

abstract class LocationCommand(override val detectiveId: DetectiveId) : Command {
}

class SearchCrimeScene(detectiveId: DetectiveId) : LocationCommand(detectiveId)

class SecureTheEvidence(detectiveId: DetectiveId, val itemId: ItemId) : LocationCommand(detectiveId)

class VisitLocation(detectiveId: DetectiveId, val where: LocationId) : LocationCommand(detectiveId)
