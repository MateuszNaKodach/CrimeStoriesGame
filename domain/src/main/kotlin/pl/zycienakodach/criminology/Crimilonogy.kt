package pl.zycienakodach.criminology

import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import pl.zycienakodach.crimestories.domain.shared.DomainEvents
import pl.zycienakodach.crimestories.scenarios.mysterydeath.Knife


class Character

class Scenario

//todo: Items in context!

interface ScenarioContext<CharactersType, ItemsType> {
    val characters: CharactersType
    val items: ItemsType
    val history: DomainEvents
}

class ScenarioDsl<T : ScenarioContext<*, *>>(val context: T, init: ScenarioDsl<T>.() -> Unit) {

    //val characters = mutableListOf<Character>()

    init {
        init()
    }

    fun build() = Scenario()
}


inline fun <reified T : ScenarioContext<*, *>> scenario(context: T, noinline dsl: ScenarioDsl<T>.() -> Unit) =
        ScenarioDsl<T>(context, dsl).build()


inline fun <reified I, reified C, reified T : ScenarioContext<C, I>> ScenarioDsl<T>.character(block: (C) -> Unit) {
    block(context.characters)
}

val <I, C, T : ScenarioContext<C, I>> ScenarioDsl<T>.history
    get() = this.context.history

typealias CrimeStory = DomainEvents

inline fun <reified I, reified C, reified T : ScenarioContext<C, I>> ScenarioDsl<T>.crime(block: (characters: C, items: I) -> CrimeStory) {
    block(context.characters, context.items) //TODO: Add crime story to scenario
}

data class MysteryScenarioCharacters(val alice: Character)
data class MysteryScenarioItems(val knife: Knife)


class MysteryScenarioContext(
        override val characters: MysteryScenarioCharacters,
        override val items: MysteryScenarioItems,
        override val history: DomainEvents
        ) : ScenarioContext<MysteryScenarioCharacters, MysteryScenarioItems>

val context = MysteryScenarioContext(
        characters = MysteryScenarioCharacters(alice = Character()),
        items = MysteryScenarioItems(knife = Knife),
        history = emptyList()
)


fun story(vararg elements: DomainEvent): CrimeStory = elements.toList()


val mysteryScenario: Scenario = scenario(context) {

    crime { (alice), (knife) ->
        story(

        )
    }
    
    character { (alice) -> }
}
