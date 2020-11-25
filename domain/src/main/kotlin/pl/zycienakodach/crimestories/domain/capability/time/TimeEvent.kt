package pl.zycienakodach.crimestories.domain.capability.time

import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import java.time.LocalDateTime

interface TimeEvent : DomainEvent


typealias Minutes = Int

class MinutesHasPassed(val minutes: Minutes) : TimeEvent
class TimeHasCome(val time: LocalDateTime) : TimeEvent
