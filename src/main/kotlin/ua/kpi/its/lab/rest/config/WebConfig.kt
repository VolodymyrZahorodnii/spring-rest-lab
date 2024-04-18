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
        fun wrapNotFoundError(call: () -> Any): ServerResponse {
            return try {
                val result = call()
                ok().body(result)
            }
            catch (e: IllegalArgumentException) {
                notFound().build()
            }
        }

        "/fn".nest {
            "/satellites".nest {
                GET("") {
                    ok().body(satelliteService.read())
                }
                GET("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { satelliteService.readById(id) }
                }
                POST("",) { req ->
                    val satellite = req.body<SatelliteRequest>()
                    ok().body(satelliteService.create(satellite))
                }
                PUT("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    val satellite = req.body<SatelliteRequest>()
                    wrapNotFoundError { satelliteService.updateById(id, satellite) }
                }
                DELETE("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { satelliteService.deleteById(id)}
                }
            }
        }
    }
}