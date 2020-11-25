package pl.zycienakodach.crimestories.scenarios

import pl.zycienakodach.crimestories.domain.capability.location.*
import pl.zycienakodach.crimestories.domain.capability.time.TimeHasCome
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.operations.scenario.ScenarioId
import pl.zycienakodach.crimestories.domain.operations.scenario.wasKilled
import java.time.LocalTime


private var cityCenter = Location(LocationId("CityCenter"), "Police Station")
private var harryHouse = Location(LocationId("London"), "Harry's House")

object MysteryDeathScenario : Scenario(
    scenarioId = ScenarioId("ScenarioId"),
    characters = mapOf(alice, policeman, harry, labTechnicianJohn),
    items = listOf(Knife, Clothes), //Czy one są potrzebne!?
    locations = listOf(cityCenter, harryHouse),
    detectiveStartLocation = cityCenter,
    history = listOf(
        alice.hasArrived(at = harryHouse),
        harry.wasKilled(by = alice),
        Knife.hasLeft(at = harryHouse),
        alice.hasGone(from = harryHouse),
        policeman.hasArrived(at = harryHouse),
        TimeHasCome(time = LocalTime.NOON)
    )
)

