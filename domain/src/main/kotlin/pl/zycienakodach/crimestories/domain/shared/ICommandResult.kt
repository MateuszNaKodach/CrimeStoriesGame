package pl.zycienakodach.crimestories.domain.shared

interface ICommandResult {
    val events: DomainEvents
    val storyMessage: StoryMessage
}

class CommandResult(override val events: DomainEvents, override val storyMessage: StoryMessage) : ICommandResult {

    companion object {
        fun onlyMessage(storyMessage: StoryMessage) = CommandResult(events = listOf(), storyMessage)
    }

}


