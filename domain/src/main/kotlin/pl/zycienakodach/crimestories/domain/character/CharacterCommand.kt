package pl.zycienakodach.crimestories.domain.character

import pl.zycienakodach.crimestories.domain.detective.DetectiveId

interface CharacterCommand{
    val characterId: CharacterId;
    val askedBy: DetectiveId
}

interface AskAboutCharacter : CharacterCommand

interface AskAboutItem : CharacterCommand
