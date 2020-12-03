package pl.zycienakodach.crimestoriesgamebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CrimeStoriesGameBackendApplication

fun main(args: Array<String>) {
	runApplication<CrimeStoriesGameBackendApplication>(*args)
}
