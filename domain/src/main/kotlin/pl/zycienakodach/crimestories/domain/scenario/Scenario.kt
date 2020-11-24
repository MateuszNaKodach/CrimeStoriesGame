package pl.zycienakodach.crimestories.domain.scenario

import pl.zycienakodach.crimestories.domain.shared.HasCommands
import pl.zycienakodach.crimestories.domain.shared.HasCommandsResults
import pl.zycienakodach.crimestories.domain.shared.HasEvents

interface Scenario : HasEvents, HasCommands, HasCommandsResults {

}
