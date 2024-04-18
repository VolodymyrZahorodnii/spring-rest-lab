package ua.kpi.its.lab.rest.controller
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
        return try {
            val satellite = satelliteService.readById(id)
            ResponseEntity.ok(satellite)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
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
        return try {
            val updatedSatellite = satelliteService.updateById(id, satellite)
            ResponseEntity.ok(updatedSatellite)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
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
        return try {
            val deletedSatellite = satelliteService.deleteById(id)
            ResponseEntity.ok(deletedSatellite)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
}