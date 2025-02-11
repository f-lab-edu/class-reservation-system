package reservation.project.application.academy

import org.apache.catalina.connector.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.entity.InstructorRole
import reservation.project.domain.academy.service.AcademyInstructorService
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.customer.entity.Role
import reservation.project.domain.customer.service.CustomerService
import reservation.project.presentation.academy.dto.academy.AcademyRegisterInfoDto
import reservation.project.presentation.academy.dto.academy.AcademyUpdateInfoReqDto
import reservation.project.presentation.academy.dto.academy.InstructorRegisterInfoDto
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDto
import java.time.LocalDateTime

@Component
class AcademyUseCase(
    private val academyService: AcademyService,
    private val customerService: CustomerService,
    private val academyInstructorService: AcademyInstructorService
) {
    val log = LoggerFactory.getLogger(AcademyUseCase::class.java)

    fun registerAcademyInfo(req: AcademyRegisterInfoDto): ResponseDto<Boolean> {
        val result = customerService.findCustomerInfo(req.uid)
        if (result!!.roles != Role.ADMIN.toString()) {
            throw ErrorException(Response.SC_BAD_REQUEST, "Not an Admin")
        }

        val academySaveInfo = academyService.saveAcademy(req.toEntity())
        val now = LocalDateTime.now()

        val instructorInfo = AcademyInstructor(
            0, result.id, academySaveInfo!!.id, enumValueOf<InstructorRole>(req.role), now, now
        )

        academyInstructorService.saveInfo(instructorInfo)

        return ResponseDto(200, true)

    }

    fun updateAcademyInfo(request: AcademyUpdateInfoReqDto): ResponseDto<Boolean> {
        val customInfo = customerService.findCustomerInfo(request.uid)
        val academyInfo = academyService.findAcademyInfo(request.academyId)

        val instructorResult = academyInstructorService.findInfoByAcademyIdAndCustomerId(customInfo!!.id, request.academyId)
        if (instructorResult!!.role != InstructorRole.MASTER) {
            throw ErrorException(Response.SC_BAD_REQUEST, "Not a Master")
        }

        academyService.updateAcademy(request.toUpdateDto(academyInfo))

        return ResponseDto(200, true)
    }

    fun registerAcademyInstructorInfo(req: InstructorRegisterInfoDto): ResponseDto<Boolean> {
        // 1. 학원존재여부
        academyService.findAcademyInfo(req.academyId)
        // 2. 강사존재여부 확인
        val masterInfo = customerService.findCustomerInfo(req.masterUid)
        // 3. 마스터 강사 학원소속여부 확인
        val checkingInstructor = academyInstructorService.checkingValue(masterInfo!!.id, req.academyId)


        // 4. 소속 강사 등록 로직
        if (req.normalUid != null){
            if(checkingInstructor == null || checkingInstructor.role != InstructorRole.MASTER){
                throw ErrorException(Response.SC_BAD_REQUEST, "Instructor is not Master")
            }
            //4-1. 소속 강사가 ADMIN 인지 확인
            val normalCustomerInfo = customerService.findCustomerInfo(req.normalUid)
            if(normalCustomerInfo!!.roles != Role.ADMIN.toString()) {
                throw ErrorException(Response.SC_BAD_REQUEST, "Register Request Instructor is not a Admin")
            }
            //4-2. 소속 강사가 요청한 학원 소속이 아닌지 확인
            val checkingAcademyInstructorInfo = academyInstructorService.checkingValue(normalCustomerInfo.id, req.academyId)
            if(checkingAcademyInstructorInfo != null){
                throw ErrorException(Response.SC_BAD_REQUEST, "Register Request Instructor exists in Academy")
            }
            //4-3. 저장
            academyInstructorService.saveInfo(req.toMasterEntity(normalCustomerInfo.id, "NORMAL"))
            // 5. 마스터 강사 등록 로직
        }else {
            //5-1. 마스터 강사가 학원에 등록이 되어 있는지 확인
            if(checkingInstructor != null){
                throw ErrorException(Response.SC_BAD_REQUEST, "Instructor(master) already exists")
            }
            //5-2 저장
            academyInstructorService.saveInfo(req.toMasterEntity(masterInfo.id, "MASTER"))
        }
        return ResponseDto(200, true)
    }

}
