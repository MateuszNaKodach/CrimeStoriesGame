package pl.zycienakodach.crimestories.domain.capability.character

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId

interface CharacterCommand{
    val characterId: CharacterId;
    val askedBy: DetectiveId
}

interface AskAboutCharacter : CharacterCommand

interface AskAboutItem : CharacterCommand
