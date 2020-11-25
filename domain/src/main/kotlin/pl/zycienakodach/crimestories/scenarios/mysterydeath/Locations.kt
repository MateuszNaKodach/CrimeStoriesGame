package pl.zycienakodach.crimestories.scenarios.mysterydeath

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveMoved
import pl.zycienakodach.crimestories.domain.capability.location.Location
import pl.zycienakodach.crimestories.domain.capability.location.LocationId
import pl.zycienakodach.crimestories.domain.capability.location.VisitLocation
import pl.zycienakodach.crimestories.domain.shared.CommandResult

val policeStation = Location(LocationId("CityCenter"), "Police Station")
val harryHouse = Location(LocationId("London"), "Harry's House") { command, history ->
    when (command) {
        is VisitLocation -> CommandResult(
            event = DetectiveMoved(detectiveId = command.detectiveId, to = command.where),
            storyMessage = "You have visited victims house. Police office is waiting for you here."
        )
        else -> CommandResult.onlyMessage("You cannot do it!")
    }
}
