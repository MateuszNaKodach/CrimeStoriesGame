package pl.zycienakodach.crimestories.domain.investigation

import pl.zycienakodach.crimestories.domain.character.Character
import pl.zycienakodach.crimestories.domain.location.Location
import pl.zycienakodach.crimestories.domain.shared.HasCommands
import pl.zycienakodach.crimestories.domain.shared.HasCommandsResults
import pl.zycienakodach.crimestories.domain.shared.HasEvents

/**
 * Investigation is played scenario.
 */
interface Investigation : HasEvents, HasCommands, HasCommandsResults {
    val charactersByLocation: Map<Location, List<Character>>
}
