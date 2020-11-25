package pl.zycienakodach.crimestories.domain.capability.detective

import pl.zycienakodach.crimestories.domain.shared.Command

interface DetectiveCommand : Command

data class StartInvestigation(override val detectiveId: DetectiveId) : DetectiveCommand
