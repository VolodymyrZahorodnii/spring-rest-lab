import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.kpi.its.lab.rest.dto.SatelliteRequest
import ua.kpi.its.lab.rest.dto.SatelliteResponse
import ua.kpi.its.lab.rest.svc.SatelliteService

@RestController
@RequestMapping("/satellites")
class SatelliteController @Autowired constructor(
    private val satelliteService: SatelliteService
) {
    init {
        println("Hello from controller")
    }
    /**
     * Gets the list of all satellites
     *
     * @return: List of SatelliteResponse
     */
    @GetMapping(path = ["", "/"])
    fun satellites(): List<SatelliteResponse> = satelliteService.read()

    /**
     * Reads the satellite by its id
     *
     * @param id: id of the satellite
     * @return: SatelliteResponse for the given id
     */
    @GetMapping("{id}")
    fun readSatellite(@PathVariable("id") id: Long): ResponseEntity<SatelliteResponse> {
        return wrapNotFound { satelliteService.readById(id) }
    }

    /**
     * Creates a new satellite instance
     *
     * @param satellite: SatelliteRequest with set properties
     * @return: SatelliteResponse for the created satellite
     */
    @PostMapping(path = ["", "/"])
    fun createSatellite(@RequestBody satellite: SatelliteRequest): SatelliteResponse {
        return satelliteService.create(satellite)
    }

    /**
     * Updates existing satellite instance
     *
     * @param satellite: SatelliteRequest with properties set
     * @return: SatelliteResponse of the updated satellite
     */
    @PutMapping("{id}")
    fun updateSatellite(
        @PathVariable("id") id: Long,
        @RequestBody satellite: SatelliteRequest
    ): ResponseEntity<SatelliteResponse> {
        return wrapNotFound { satelliteService.updateById(id, satellite)}
    }

    /**
     * Deletes existing satellite instance
     *
     * @param id: id of the satellite
     * @return: SatelliteResponse of the deleted satellite
     */
    @DeleteMapping("{id}")
    fun deleteSatellite(
        @PathVariable("id") id: Long
    ): ResponseEntity<SatelliteResponse> {
        return wrapNotFound { satelliteService.deleteById(id) }
    }

    fun <T>wrapNotFound(call: () -> T): ResponseEntity<T> {
        return try {
            // call function for result
            val result = call()
            // return "ok" response with result body
            ResponseEntity.ok(result)
        }
        catch (e: IllegalArgumentException) {
            // catch not-found exception
            // return "404 not-found" response
            ResponseEntity.notFound().build()
        }
    }
}