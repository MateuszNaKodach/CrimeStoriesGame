package pl.zycienakodach.crimestories.scenarios

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.policy.investigation.SinglePlayerInvestigation
import pl.zycienakodach.crimestories.domain.policy.investigation.currentTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MysteryDeathScenarioTest {

    val detectiveThomas = DetectiveId("Thomas")

    @Test
    fun `detective starts investigation at Police Station`() {
        val investigation = mysteryDeathInvestigation()
    }

    @Test
    fun `detective starts investigation on 2020_11_25 at 12_00`() {
        val investigation = mysteryDeathInvestigation()

        assertThat(investigation.currentTime()).isEqualTo(
            LocalDateTime.of(
                LocalDate.of(2020, 11, 25),
                LocalTime.of(12, 0,0)
            )
        )
    }

    private fun mysteryDeathInvestigation() = SinglePlayerInvestigation(
        scenario = MysteryDeathScenario,
        detectiveId = detectiveThomas
    )

}
