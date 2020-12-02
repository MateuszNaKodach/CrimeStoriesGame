package pl.zycienakodach.crimestories.domain.capability.detective

import pl.zycienakodach.crimestories.domain.shared.Command

typealias Answer = Any
typealias Question = String

interface DetectiveCommand : Command

data class StartInvestigation(override val detectiveId: DetectiveId) : DetectiveCommand
data class CloseInvestigation(override val detectiveId: DetectiveId, val answers: Map<Question, Any> = mapOf()) : DetectiveCommand


