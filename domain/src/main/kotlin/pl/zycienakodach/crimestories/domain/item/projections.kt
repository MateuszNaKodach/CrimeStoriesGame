package pl.zycienakodach.crimestories.domain.item

import pl.zycienakodach.crimestories.domain.detective.DetectiveId

fun detectivePossessItem(itemId: ItemId, detectiveId: DetectiveId, events: ItemEvents): Boolean =
    events
        .filter { it.itemId === itemId }
        .fold(false) { acc, domainEvent ->
        when (domainEvent) {
            is ItemWasFound -> if(domainEvent.detectiveId === detectiveId) true else acc
            is ItemWasLost ->  if(domainEvent.detectiveId === detectiveId) false else acc
            else -> acc
        }
    }
