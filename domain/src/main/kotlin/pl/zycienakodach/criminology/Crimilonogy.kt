package pl.zycienakodach.criminology


class Character

class Scenario

//todo: Items in context!

interface ScenarioContext<CharactersType>{
    val characters: CharactersType
}

class ScenarioDsl<T : ScenarioContext<*>>(init: ScenarioDsl<T>.() -> Unit) {

    val characters = mutableListOf<Character>()

    init {
        init()
    }

    fun build() = Scenario()
}


inline fun <reified T : ScenarioContext<*>> scenario(noinline dsl: ScenarioDsl<T>.() -> Unit) =
        ScenarioDsl<T>(dsl).build()


fun <T : ScenarioContext<*>> ScenarioDsl<T>.character() {

}

data class MysteryScenarioCharacters(val alice: Character)

class MysteryScenarioContext(override val characters: MysteryScenarioCharacters) : ScenarioContext<MysteryScenarioCharacters>



val mysteryScenario: Scenario = scenario<MysteryScenarioContext> {

}
