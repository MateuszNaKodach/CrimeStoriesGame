package pl.zycienakodach.crimestories.domain.shared

import pl.zycienakodach.crimestories.domain.Command
import pl.zycienakodach.crimestories.domain.CommandResult
import pl.zycienakodach.crimestories.domain.DomainEvent

typealias DomainEvents = List<DomainEvent>
typealias Commands = List<Command>
typealias CommandsResults = List<CommandResult>
