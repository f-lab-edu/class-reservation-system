package reservation.project.application.academy

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Component
import reservation.project.application.security.util.JwtUtils
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.admin.service.AdminService
import reservation.project.domain.apply.service.ApplyService
import reservation.project.domain.token.entity.Token
import reservation.project.domain.token.service.TokenService
import reservation.project.domain.token.status.TokenStatus
import reservation.project.presentation.academy.dto.AcademyClassRegisterReqDto
import reservation.project.presentation.academy.dto.AcademyClassReqDto
import reservation.project.presentation.academy.dto.AcademyClassUpdateReqDto
import reservation.project.presentation.academy.dto.ApplyReqDto
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDto

@Component
class AcademyClassUseCase(
    private val academyClassService: AcademyClassService,
    private val academyService: AcademyService,
    private val adminService: AdminService,
    private val applyService: ApplyService,
    private val tokenService: TokenService,
    private val jwtUtils: JwtUtils
) {

    fun enrollInClass(req: ApplyReqDto) {
        // 강의 존재 여부
        val findClassInfo = academyClassService.findByAcademyClassId(req.classId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found AcademyInfo")
        }
        // 강의 상태 여부
        if(findClassInfo.status != ClassStatus.PROGRESS){
           throw ErrorException(Response.SC_CONFLICT, "this is not a class in progress")
        }
        // 인원체크
        val findApplyInfo = applyService.findByClassId(req.classId).size
        if(findClassInfo.isCapacityExceeded(findApplyInfo)) {
            throw ErrorException(Response.SC_CONFLICT, "over capacity")
        }

        // 대기열 체크
        val queueInfo = tokenService.findByAcademyClassIdAndCustomerId(req.classId, req.userId)
        if(queueInfo.isPresent && !queueInfo.get().isQueueCanceled()){
            throw ErrorException(Response.SC_CONFLICT, "this information is already being applied for")
        }
        val queueAccessToken = jwtUtils.accessToken(req.classId, req.userId)
        val tokenInfo = jwtUtils.extractTokenDetails(queueAccessToken)
        val queue = Token(0, queueAccessToken, tokenInfo["expiration"], req.userId, req.classId, TokenStatus.PENDING, tokenInfo["issuedAt"])

        // 대기열 추가
        tokenService.addQueue(queue).orElseThrow {
            ErrorException(Response.SC_BAD_REQUEST, "Add Queue Error")
        }

    }

    fun registerClassInfo(req: AcademyClassRegisterReqDto) {
        val findAcademyResult = academyService.findByAcademyId(req.academyId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found AcademyInfo")
        }
        val findAdminInfo = adminService.findByAdminId(req.adminId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found AdminInfo")
        }

        val toEntity = req.toEntity()
        toEntity.academy = findAcademyResult
        toEntity.changeClassStatusToWAITING()
        toEntity.classInstructor = findAdminInfo.adminName.toString()

        academyClassService.save(toEntity).orElseThrow {
            ErrorException(Response.SC_BAD_REQUEST, "AcademyClass Save Error")
        }
    }

    fun updateClassInfo(req: AcademyClassUpdateReqDto) {
        var classInfo = academyClassService.findByAcademyClassId(req.classId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found AcademyClassInfo")
        }
        academyService.findByAcademyId(req.academyId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found AcademyInfo")
        }

        adminService.findByAdminId(req.adminId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found AdminInfo")
        }

        classInfo.toUpdateAcademyClass(req)

        academyClassService.update(classInfo).orElseThrow {
            ErrorException(Response.SC_BAD_REQUEST, "AcademyClass Update Error")
        }

    }

    fun findByAcademyId(academyId: Long): List<AcademyClass> {
        val result = academyClassService.findByAcademyId(academyId)
        if(result.isEmpty()) throw ErrorException(Response.SC_NOT_FOUND, "not found AcademyClassInfo")

        return result
    }

    fun findByAcademyClassId(academyClassId: Long): AcademyClass {
        val result = academyClassService.findByAcademyClassId(academyClassId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found AcademyClassInfo")
        }
        return result
    }

    fun findByCustomerId(CustomerId: Long): List<AcademyClass> {
        val result = academyClassService.findByCustomerId(CustomerId)
        if(result.isEmpty()) throw ErrorException(Response.SC_NOT_FOUND, "not found AcademyClassInfo")

        return result
    }

    fun findByAdminId(adminId: Long): List<AcademyClass> {
        val result = academyClassService.findByAdminId(adminId)
        if(result.isEmpty()) throw ErrorException(Response.SC_NOT_FOUND, "not found AcademyClassInfo")

        return result
    }

    fun findByAcademyIdAndAdminId(req: AcademyClassReqDto): AcademyClass {
        val result = academyClassService.findByAcademyIdAndAdminId(req.academyId, req.adminId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found AcademyClassInfo")
        }
        return result
    }


}