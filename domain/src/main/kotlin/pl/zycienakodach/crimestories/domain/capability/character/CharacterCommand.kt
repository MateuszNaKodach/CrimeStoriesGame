package pl.zycienakodach.crimestories.domain.capability.character

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.shared.Command

interface CharacterCommand : Command {
    val ask: CharacterId;
    val askedBy: DetectiveId
    override val detectiveId: DetectiveId
        get() = askedBy
}


class AskAboutCharacter(override val ask: CharacterId, override val askedBy: DetectiveId, val askAbout: CharacterId) :
    CharacterCommand

class AskAboutItem(override val ask: CharacterId, override val askedBy: DetectiveId, val askAbout: ItemId) :
    CharacterCommand

class LetsChatWith(override val ask: CharacterId, override val askedBy: DetectiveId): CharacterCommand
