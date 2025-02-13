package reservation.project.domain.academy.service


import org.apache.catalina.connector.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.repository.AcademyInstructorRepository
import reservation.project.presentation.advice.exception.ErrorException

@Service
class AcademyInstructorService(
    private val academyInstructorRepository: AcademyInstructorRepository
) {
    private val logger = LoggerFactory.getLogger(AcademyInstructorService::class.java)

    fun saveInfo(academyInstructor: AcademyInstructor): AcademyInstructor {
        return academyInstructorRepository.saveInfo(academyInstructor) ?: throw ErrorException(Response.SC_CONFLICT, "Instructor Save Error")
    }

    fun findInfoById(id: Long): AcademyInstructor {

        return academyInstructorRepository.findAcademyInstructorInfoById(id).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "AcademyInstructor Not Found")}

    }

    fun findInfoByCustomerId(customerId: Long): List<AcademyInstructor> {
        val result = academyInstructorRepository.findAcademyInstructorInfoByCustomerId(customerId)
        if(result.isEmpty()){
            throw ErrorException(Response.SC_NOT_FOUND, "Academy Instructor Not Found")
        }
        return result
    }

    fun findInfoByAcademyId(academyId: Long): List<AcademyInstructor> {
        val result = academyInstructorRepository.findAcademyInstructorInfoByAcademyId(academyId)
        if(result.isEmpty()){
            throw ErrorException(Response.SC_NOT_FOUND, "Academy Instructor Not Found")
        }
        return result
    }

    fun findInfoByAcademyIdAndCustomerId(customerId: Long, academyId: Long): AcademyInstructor? {
        return academyInstructorRepository.findAcademyInstructorInfoAcademyIdAndCustomerId(customerId, academyId)
            ?: throw ErrorException(Response.SC_NOT_FOUND, "Academy Instructor Not Found")
    }

    fun checkingValue(customerId: Long, academyId: Long): AcademyInstructor? {
        return academyInstructorRepository.findAcademyInstructorInfoAcademyIdAndCustomerId(customerId, academyId)
            ?: throw ErrorException(Response.SC_NOT_FOUND, "Academy Instructor Not Found")
    }
}
