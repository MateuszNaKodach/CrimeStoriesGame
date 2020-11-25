package pl.zycienakodach.crimestories.domain.capability.detective

import pl.zycienakodach.crimestories.domain.capability.location.LocationId
import pl.zycienakodach.crimestories.domain.shared.DomainEvent

interface DetectiveEvent : DomainEvent{
    val detectiveId: DetectiveId
}

data class InvestigationStarted(override val detectiveId: DetectiveId) : DetectiveEvent
data class DetectiveMoved(override val detectiveId: DetectiveId, val to: LocationId): DetectiveEvent
