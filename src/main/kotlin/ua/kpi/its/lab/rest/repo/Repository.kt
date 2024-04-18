package ua.kpi.its.lab.rest.repo

import org.springframework.data.jpa.repository.JpaRepository
import ua.kpi.its.lab.rest.entity.Satellite
import ua.kpi.its.lab.rest.entity.Processor

interface SatelliteRepository : JpaRepository<Satellite, Long> {

}

interface ProcessorRepository : JpaRepository<Processor, Long> {

}