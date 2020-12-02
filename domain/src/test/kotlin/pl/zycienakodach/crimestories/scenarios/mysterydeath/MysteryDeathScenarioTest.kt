package pl.zycienakodach.crimestories.scenarios.mysterydeath

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import pl.zycienakodach.crimestories.domain.capability.character.AskAboutCharacter
import pl.zycienakodach.crimestories.domain.capability.character.AskAboutItem
import pl.zycienakodach.crimestories.domain.capability.character.LetsChatWith
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveMoved
import pl.zycienakodach.crimestories.domain.capability.detective.InvestigationStarted
import pl.zycienakodach.crimestories.domain.capability.detective.StartInvestigation
import pl.zycienakodach.crimestories.domain.capability.item.ItemWasFound
import pl.zycienakodach.crimestories.domain.capability.location.*
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
    fun `detective can move to victim house`() {
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

    @Test
    fun `detective can search crime scene at victim house`() {
        val investigation = mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id)
        )

        assertThat(
            investigation.investigate(
                SearchCrimeScene(detectiveThomas, at = harryHouseId)
            )
        ).isEqualTo(
            CommandResult(
                event = CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
                storyMessage = "You have searched crime scene. Try to secure items."
            )
        )
    }

    @Test
    fun `at victim house detective can talk with victim daughter`(){
        val investigation = mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id)
        )
        assertThat(
            investigation.investigate(
                LetsChatWith(ask = alice.first, askedBy = detectiveThomas)
            )
        ).isEqualTo(
            CommandResult.onlyMessage("Alice: I'm really scared! My dad was killed by someone...")
        )
    }

    @Test
    fun `after search crime scene, detective can secure knife`() {
        val investigation = mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas)
        )

        assertThat(
            investigation.investigate(
                SecureTheEvidence(detectiveThomas, at = harryHouseId, itemId = Knife.id)
            )
        ).isEqualTo(
            CommandResult(
                event = ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas),
                storyMessage = "Item was secured!"
            )
        )
    }

    @Test
    fun `when detective found knife, victim daughter tell that knife belongs to her brother`(){
        val investigation = mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
            ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas)
        )

        assertThat(
            investigation.investigate(
                AskAboutItem(ask = alice.first, askedBy = detectiveThomas, askAbout = Knife.id)
            )
        ).isEqualTo(
            CommandResult.onlyMessage("Alice: Oh! This knife belongs to my brother.")
        )
    }

    @Test
    fun `when detective found knife, lab technician tell that knife has fingerprint of victim daughter`(){
        val investigation = mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
            ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas)
        )

        assertThat(
            investigation.investigate(
                AskAboutItem(ask = labTechnicianJohn.first, askedBy = detectiveThomas, askAbout = Knife.id)
            )
        ).isEqualTo(
            CommandResult.onlyMessage("John: On the Knife, I've found fingerprints of Alice - Harry's daughter.")
        )
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
