package pl.zycienakodach.crimestories.scenarios.mysterydeath

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveMoved
import pl.zycienakodach.crimestories.domain.capability.detective.InvestigationStarted
import pl.zycienakodach.crimestories.domain.capability.detective.StartInvestigation
import pl.zycienakodach.crimestories.domain.capability.location.VisitLocation
import pl.zycienakodach.crimestories.domain.capability.location.detectiveLocation
import pl.zycienakodach.crimestories.domain.policy.investigation.SinglePlayerInvestigation
import pl.zycienakodach.crimestories.domain.policy.investigation.currentTime
import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import pl.zycienakodach.crimestories.domain.shared.DomainEvents
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val detectiveThomas = DetectiveId("Thomas")

class MysteryDeathScenarioTest {

    val investigation = mysteryDeathInvestigation()

    @Test
    fun `scenario action starts at Police Station`() {
        val investigation = mysteryDeathInvestigation()

        assertThat(investigation.detectiveLocation()).isEqualTo(policeStation.id)
    }

    @Test
    fun `scenario action starts on 2020_11_25 at 12_00`() {
        val investigation = mysteryDeathInvestigation()

        assertThat(investigation.currentTime()).isEqualTo(
            LocalDateTime.of(
                LocalDate.of(2020, 11, 25),
                LocalTime.of(12, 0, 0)
            )
        )
    }

    @Test
    fun `start investigation should say about found human body`() {
        val investigation = mysteryDeathInvestigation()

        assertThat(investigation.investigate(StartInvestigation(detectiveThomas)))
            .isEqualTo(
                CommandResult(
                    event = InvestigationStarted(detectiveThomas),
                    storyMessage = "Police is on the crime scene. Neighbour call to you that they have found Harry death body. His apartment is in city center."
                )
            )
    }

    @Test
    fun `detective can move to city center`() {
        val investigation = mysteryDeathInvestigation(
            StartInvestigation(detectiveThomas)
        )

        assertThat(investigation.investigate(VisitLocation(detectiveThomas, where = harryHouse.id)))
            .isEqualTo(
                CommandResult(
                    event = DetectiveMoved(detectiveThomas, to = harryHouse.id),
                    storyMessage = "You have visited victims house. Police officer is waiting for you here."
                )
            )
        assertThat(investigation.detectiveLocation()).isEqualTo(harryHouse.id)
    }

}

private fun mysteryDeathInvestigation(vararg event: DomainEvent) =
    mysteryDeathInvestigation(listOf(*event), emptyList())

private fun mysteryDeathInvestigation(vararg command: Command) =
    mysteryDeathInvestigation(emptyList(), listOf(*command))

private fun mysteryDeathInvestigation(
    history: DomainEvents = emptyList(),
    commands: List<Command> = emptyList()
) =
    SinglePlayerInvestigation(
        scenario = MysteryDeathScenario,
        detectiveId = detectiveThomas,
        history = history
    ).apply {
        commands.forEach { investigate(it) }
    }
