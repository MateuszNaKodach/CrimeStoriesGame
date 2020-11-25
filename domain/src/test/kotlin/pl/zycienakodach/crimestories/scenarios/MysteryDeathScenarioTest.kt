package pl.zycienakodach.crimestories.scenarios

import assertk.assertThat
import org.junit.jupiter.api.Test
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.policy.investigation.SinglePlayerInvestigation

class MysteryDeathScenarioTest {

    val detectiveThomas = DetectiveId("Thomas")

    @Test
    fun `when investigation started, players is at Police Station`() {
        val investigation = mysteryDeathInvestigation()

        assertThat(investigation)
    }

    private fun mysteryDeathInvestigation() = SinglePlayerInvestigation(
        scenario = MysteryDeathScenario,
        detectiveId = detectiveThomas
    )

}
