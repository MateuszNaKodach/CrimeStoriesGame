package pl.zycienakodach.crimestories.domain.capability.character

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.ItemId

interface CharacterCommand {
    val ask: CharacterId;
    val askedBy: DetectiveId
}


class AskAboutCharacter(override val ask: CharacterId, override val askedBy: DetectiveId, val askAbout: CharacterId) :
    CharacterCommand

class AskAboutItem(override val ask: CharacterId, override val askedBy: DetectiveId, val askAbout: ItemId) :
    CharacterCommand

class LetsChatWith(override val ask: CharacterId, override val askedBy: DetectiveId): CharacterCommand
