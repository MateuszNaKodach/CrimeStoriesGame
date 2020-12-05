package pl.zycienakodach.kttimetraveler.spring

import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsBuilderCustomizer
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.support.GenericApplicationContext
import org.springframework.fu.kofu.AbstractDsl
import org.springframework.fu.kofu.ConfigurationDsl
import org.springframework.fu.kofu.r2dbc.DataR2dbcDsl
import org.springframework.fu.kofu.r2dbc.R2dbcDsl
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.LinkedHashMap

class CurrentTimeDsl(private val init: CurrentTimeDsl.() -> Unit) : AbstractDsl() {

    var fixed: Boolean = false

    var zone: ZoneId = ZoneId.systemDefault()

    var date: LocalDate = LocalDate.now(zone)

    var time: LocalTime = LocalTime.now(zone)

    override fun initialize(context: GenericApplicationContext) {
        super.initialize(context)
        init()

        val properties = currentTimeProperties()

        CurrentTimeInitializer(properties).initialize(context)
    }

    private fun currentTimeProperties(): CurrentTimeProperties =
            let { self ->
                CurrentTimeProperties().apply {
                    fixed = self.fixed
                    zone = self.zone
                    date = self.date
                    time = self.time
                }
            }
}

/**
 * Configure application current time provider.
 * @see CurrentTimeDsl
 */
fun ConfigurationDsl.currentTime(dsl: CurrentTimeDsl.() -> Unit = {}) {
    enable(CurrentTimeDsl(dsl))
}
