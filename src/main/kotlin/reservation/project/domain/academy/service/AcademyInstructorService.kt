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
        try{
            return academyInstructorRepository.saveInfo(academyInstructor) ?: throw ErrorException(Response.SC_CONFLICT, "Instructor Save Error")
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }
        catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Instructor Save Error")
        }
    }

    fun findInfoById(id: Long): AcademyInstructor {
        try{
            return academyInstructorRepository.findAcademyInstructorInfoById(id).orElseThrow {
                ErrorException(Response.SC_NOT_FOUND, "AcademyInstructor Not Found")
            }
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }
        catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Instructor Save Error")
        }
    }

    fun findInfoByCustomerId(customerId: Long): List<AcademyInstructor> {
        try{
            val result = academyInstructorRepository.findAcademyInstructorInfoByCustomerId(customerId)
            if(result.isEmpty()){
                throw ErrorException(Response.SC_NOT_FOUND, "Academy Instructor Not Found")
            }
            return result
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }
        catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Instructor Save Error")
        }
    }

    fun findInfoByAcademyId(academyId: Long): List<AcademyInstructor> {
        try{
            val result = academyInstructorRepository.findAcademyInstructorInfoByAcademyId(academyId)
            logger.info(result.toString())
            if(result.isEmpty()){
                throw ErrorException(Response.SC_NOT_FOUND, "Academy Instructor Not Found")
            }
            return result

        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }
        catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Instructor Save Error")
        }
    }

    fun findInfoByAcademyIdAndCustomerId(customerId: Long, academyId: Long): AcademyInstructor? {
        try{
            return academyInstructorRepository.findAcademyInstructorInfoAcademyIdAndCustomerId(customerId, academyId)
                ?: throw ErrorException(Response.SC_NOT_FOUND, "Academy Instructor Not Found")

        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }
        catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Instructor Save Error")
        }
    }

    fun checkingValue(customerId: Long, academyId: Long): AcademyInstructor? {
        try{
            return academyInstructorRepository.findAcademyInstructorInfoAcademyIdAndCustomerId(customerId, academyId)

        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }
        catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Instructor Save Error")
        }
    }
}