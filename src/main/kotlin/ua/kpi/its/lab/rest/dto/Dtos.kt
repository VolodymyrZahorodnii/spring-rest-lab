package ua.kpi.its.lab.rest.dto

data class SatelliteRequest(
    var name: String,
    var country: String,
    var launchDate: String,
    var purpose: String,
    var weight: Double,
    var height: Double,
    var isGeostationary: Boolean,
    var processor: ProcessorRequest
)

data class SatelliteResponse(
    var id: Long,
    var name: String,
    var country: String,
    var launchDate: String,
    var purpose: String,
    var weight: Double,
    var height: Double,
    var isGeostationary: Boolean,
    var processor: ProcessorResponse
)

data class ProcessorRequest(
    var name: String,
    var manufacturer: String,
    var cores: Int,
    var frequency: Double,
    var socket: String,
    var productionDate: String,
    var mmxSupport: Boolean
)

data class ProcessorResponse(
    var id: Long,
    var name: String,
    var manufacturer: String,
    var cores: Int,
    var frequency: Double,
    var socket: String,
    var productionDate: String,
    var mmxSupport: Boolean
)