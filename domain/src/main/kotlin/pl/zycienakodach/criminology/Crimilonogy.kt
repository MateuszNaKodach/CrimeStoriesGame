package pl.zycienakodach.criminology

import pl.zycienakodach.crimestories.domain.capability.character.AskAboutItem
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.Item
import pl.zycienakodach.crimestories.domain.shared.*
import pl.zycienakodach.crimestories.scenarios.mysterydeath.Knife


class Character(val id: CharacterId) {

    fun whenAsk(command: Command, askDsl: CharacterAskDsl.() -> Unit = {}) =
            CharacterAskDsl(command)


    fun whenAskAbout(item: Item, askDsl: CharacterAskDsl.() -> Unit = {}) =
            CharacterAskDsl(AskAboutItem(id, ))


}

typealias Condition = (history: DomainEvents) -> Boolean

fun any(vararg events: DomainEvent): Condition = AnyEventCondition(*events)
fun all(vararg events: DomainEvent): Condition = AllEventsCondition(*events)

object NoCondition : Condition {
    override fun invoke(history: DomainEvents): Boolean = true
}

class AllEventsCondition(private vararg val events: DomainEvent) : Condition {

    override fun invoke(history: DomainEvents) = events.all { it.inThe(history) }
}

class AnyEventCondition(private vararg val events: DomainEvent) : Condition {

    override fun invoke(history: DomainEvents) = events.any { it.inThe(history) }
}

class CharacterAskDsl(val command: Command, val condition: Condition = NoCondition) {
    infix fun then(commandResult: CommandResult) {

    }

    infix fun and(condition: Condition) = CharacterAskDsl(this.command, condition)

    infix fun and(event: DomainEvent) = CharacterAskDsl(this.command, all(event))
}


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

val <I, C, T : ScenarioContext<C, I>> ScenarioDsl<T>.characters
    get() = this.context.characters

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


val mysteryScenario: (detectiveId: DetectiveId) -> Scenario = { detectiveId ->
    scenario(context) {

        crime { (alice), (knife) ->
            story(

            )
        }

        characters.alice
                .whenAskAbout(Knife) and
    }
}


