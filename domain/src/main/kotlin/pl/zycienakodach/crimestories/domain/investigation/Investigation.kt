package pl.zycienakodach.crimestories.domain.investigation

import pl.zycienakodach.crimestories.domain.shared.HasCommands
import pl.zycienakodach.crimestories.domain.shared.HasCommandsResults
import pl.zycienakodach.crimestories.domain.shared.HasEvents

/**
 * Investigation is played scenario.
 */
interface Investigation : HasEvents, HasCommands, HasCommandsResults {
}
