package pl.zycienakodach.crimestories.domain.capability.time

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.shared.Command

interface TimeCommand : Command

class GoToSleepFor(override val detectiveId: DetectiveId, val hours: Int) : TimeCommand
