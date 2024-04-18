package ua.kpi.its.lab.rest.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.function.*
import ua.kpi.its.lab.rest.dto.SatelliteRequest
import ua.kpi.its.lab.rest.svc.SatelliteService
import java.text.SimpleDateFormat

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val builder = Jackson2ObjectMapperBuilder()
            .indentOutput(true)
            .dateFormat(SimpleDateFormat("yyyy-MM-dd"))
            .modulesToInstall(KotlinModule.Builder().build())

        converters
            .add(MappingJackson2HttpMessageConverter(builder.build()))
    }

    @Bean
    fun functionalRoutes(satelliteService: SatelliteService): RouterFunction<*> = router {
        "/fn".nest {
            "/satellites".nest {
                GET("") {
                    try {
                        val satellites = satelliteService.read()
                        ok().body(satellites)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }
                GET("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    try {
                        val satellite = satelliteService.readById(id)
                        ok().body(satellite)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }
                POST("") { req ->
                    val satellite = req.body<SatelliteRequest>()
                    try {
                        val createdSatellite = satelliteService.create(satellite)
                        ok().body(createdSatellite)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }
                PUT("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    val satellite = req.body<SatelliteRequest>()
                    try {
                        val updatedSatellite = satelliteService.updateById(id, satellite)
                        ok().body(updatedSatellite)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }
                DELETE("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    try {
                        val deletedSatellite = satelliteService.deleteById(id)
                        ok().body(deletedSatellite)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }
            }
        }
    }
}