package pl.zycienakodach.crimestories.domain.capability.detective

import pl.zycienakodach.crimestories.domain.shared.StringIdentifier

/**
 * Mogą być scenariusze kooperacyjne, np. że jeden detektyw wygra. Dowody tylko jedna instancja
 */
class DetectiveId(id: String) : StringIdentifier(id)
