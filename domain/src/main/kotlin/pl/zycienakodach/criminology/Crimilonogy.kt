package pl.zycienakodach.criminology

import pl.zycienakodach.crimestories.domain.capability.character.AskAboutItem
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.detective.AnyDetectiveId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.Item
import pl.zycienakodach.crimestories.domain.operations.scenario.wasFound
import pl.zycienakodach.crimestories.domain.shared.*
import pl.zycienakodach.crimestories.scenarios.mysterydeath.Knife


class Character(val id: CharacterId = CharacterId()) {

    fun whenAsk(command: Command, askDsl: CharacterAskDsl.() -> Unit = {}) =
            CharacterAskDsl(this, command)


    infix fun whenAskedAbout(item: Item) =
            CharacterAskDsl(this, AskAboutItem(id, askAbout = item.id))


}

typealias Condition = (history: DomainEvents) -> Boolean

fun any(vararg events: DomainEvent): Condition = AnyEventCondition(*events)
fun all(vararg events: DomainEvent): Condition = AllEventsCondition(*events)
fun no(vararg events: DomainEvent): Condition = NoEventsCondition(*events)


object NoCondition : Condition {
    override fun invoke(history: DomainEvents): Boolean = true
}

class AllEventsCondition(private vararg val events: DomainEvent) : Condition {
    override fun invoke(history: DomainEvents) = events.all { it.inThe(history) }
}

class NoEventsCondition(private vararg val events: DomainEvent) : Condition {
    override fun invoke(history: DomainEvents) = events.none { it.inThe(history) }
}

class AnyEventCondition(private vararg val events: DomainEvent) : Condition {
    override fun invoke(history: DomainEvents) = events.any { it.inThe(history) }
}

data class CharacterAskDsl(
        val character: Character,
        val command: Command,
        val condition: Condition = NoCondition,
        val result: CommandResult? = null
) {
    infix fun then(commandResult: CommandResult): CharacterAskDsl {
        return this.copy(result = commandResult)
    }

    infix fun then(storyMessage: StoryMessage): CharacterAskDsl {
        return this.copy(result = CommandResult.onlyMessage(storyMessage))
    }

    fun then(storyMessage: StoryMessage, event: DomainEvent): CharacterAskDsl {
        return this.copy(result = CommandResult(event, storyMessage))
    }

    infix fun and(condition: Condition) = this.copy(condition = condition)

    infix fun and(event: DomainEvent) = this.copy(condition = all(event))
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

        characters.alice whenAskedAbout (Knife) and no(Knife.wasFound) then "Test1"

    }
}


