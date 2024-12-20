package reservation.project.application.academy

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Component
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.service.AcademyService
import reservation.project.presentation.academy.dto.AcademyClassRegisterReqDto
import reservation.project.presentation.academy.dto.AcademyClassReqDto
import reservation.project.presentation.academy.dto.AcademyClassUpdateReqDto
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDto

@Component
class AcademyClassUseCase(
    private val academyClassService: AcademyClassService,
    private val academyService: AcademyService
) {

    fun registerClassInfo(req: AcademyClassRegisterReqDto): String {
        val findAcademyResult = academyService.findByAcademyId(req.academyId).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found AcademyInfo"))
        }
        val toEntity = req.toEntity()
        toEntity.academy = findAcademyResult
        toEntity.status = "reserve"

        academyClassService.save(toEntity).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "AcademyClass Save Error"))
        }

        return "Success Register"
    }

    fun updateClassInfo(req: AcademyClassUpdateReqDto): String {
        val findAcademyResult = academyClassService.findByAcademyClassId(req.classId).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found AcademyClassInfo"))
        }
        val academyInfo = academyService.findByAcademyId(req.academyId).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found AcademyInfo"))
        }

        val toEntity = req.toEntity()
        toEntity.academy = academyInfo

        academyClassService.update(toEntity).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "AcademyClass Update Error"))
        }

        return "Success Update"
    }

    fun findByAcademyId(academyId: Long): List<AcademyClass> {
        val result = academyClassService.findByAcademyId(academyId)
        if(result.isEmpty()) throw ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found AcademyClassInfo"))

        return result
    }

    fun findByAcademyClassId(academyClassId: Long): AcademyClass {
        val result = academyClassService.findByAcademyClassId(academyClassId).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found AcademyClassInfo"))
        }
        return result
    }

    fun findByCustomerId(CustomerId: Long): List<AcademyClass> {
        val result = academyClassService.findByCustomerId(CustomerId)
        if(result.isEmpty()) throw ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found AcademyClassInfo"))

        return result
    }

    fun findByAdminId(adminId: Long): List<AcademyClass> {
        val result = academyClassService.findByAdminId(adminId)
        if(result.isEmpty()) throw ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found AcademyClassInfo"))

        return result
    }

    fun findByAcademyIdAndAdminId(req: AcademyClassReqDto): AcademyClass {
        val result = academyClassService.findByAcademyIdAndAdminId(req.academyId, req.adminId).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found AcademyClassInfo"))
        }
        return result
    }




}