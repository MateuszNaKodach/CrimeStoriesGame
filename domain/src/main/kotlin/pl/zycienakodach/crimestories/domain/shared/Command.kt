package pl.zycienakodach.crimestories.domain.shared

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId

interface Command {
    val detectiveId: DetectiveId
}
