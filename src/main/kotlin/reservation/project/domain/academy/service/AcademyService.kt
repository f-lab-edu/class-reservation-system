package reservation.project.domain.academy.service

import org.apache.catalina.connector.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.repository.AcademyRepository
import reservation.project.presentation.academy.dto.academy.AcademyUpdateDto
import reservation.project.presentation.advice.exception.ErrorException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class AcademyService(
    private val academyRepository: AcademyRepository
) {

    private val log = LoggerFactory.getLogger(AcademyService::class.java)

    fun saveAcademy(academy: Academy): Academy? {
        try {
            return academyRepository.saveAcademy(academy) ?: throw ErrorException(Response.SC_CONFLICT, "Academy Save Error")
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Academy Save Error")
        }
    }

    fun updateAcademy(updateDto: AcademyUpdateDto): Academy? {
        try {
            val academyInfo = updateDto.info
            updateDto.academyName?.let { academyInfo.academyName = it }
            updateDto.category?.let { academyInfo.category = it }
            updateDto.openTime?.let { academyInfo.openTime = convertDateTime(it) }
            updateDto.closeTime?.let { academyInfo.closeTime = convertDateTime(it) }
            updateDto.location?.let { academyInfo.location = it }
            updateDto.socialNetworkAddress?.let { academyInfo.socialNetworkAddress = it }
            updateDto.contactInfo?.let { academyInfo.contactInfo = it }

            return academyRepository.saveAcademy(academyInfo) ?: throw ErrorException(Response.SC_CONFLICT, "Academy Update Error")
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Academy Save Error")
        }
    }

    fun findAcademyInfo(id: Long): Academy {
        try{
            return academyRepository.findAcademyById(id).orElseThrow {
                ErrorException(Response.SC_NOT_FOUND, "Academy Not Found")
            }
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Server Error : " + e.message)
        }
    }

    fun findAcademyInfoByName(name: String): List<Academy> {
        try{
            val result = academyRepository.findByAcademyName(name)
            if (result.isEmpty()) {
                throw ErrorException(Response.SC_NOT_FOUND, "Academy Not Found")
            }
            return result
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Academy Save Error")
        }
    }

    private fun convertDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
    }
}
