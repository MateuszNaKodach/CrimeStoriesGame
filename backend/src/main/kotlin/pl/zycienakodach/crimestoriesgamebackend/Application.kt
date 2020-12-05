package pl.zycienakodach.crimestoriesgamebackend

import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.fu.kofu.reactiveWebApplication
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.json
import pl.zycienakodach.crimestories.domain.policy.investigation.Investigation
import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.ICommandResult
import pl.zycienakodach.kttimetraveler.spring.currentTime

val app = reactiveWebApplication {
    enable(webConfig)
}

fun routes() = coRouter {
    GET("/test") {
        ServerResponse.ok().json().bodyValueAndAwait("Super")
    }
    "/investigations".nest {
        GET("/") {
            ServerResponse.ok().json().bodyValueAndAwait("Super")
        }
    }
}


val webConfig = configuration {
    beans {
        bean(::routes)
    }
    webFlux {
        codecs {
            jackson()
            string()
        }
    }
    currentTime {

    }
}

fun main() {
    app.run()
}

//APPLICATION
interface InvestigationRepository

//DOMAIN
typealias CommandHandler = (investigation: Investigation, command: Command) -> ICommandResult
val investigationCommandHandler: CommandHandler = { investigation, command -> investigation.investigate(command) }
