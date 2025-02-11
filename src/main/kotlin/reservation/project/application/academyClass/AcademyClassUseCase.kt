package reservation.project.application.academyClass

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.service.AcademyInstructorService
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.academy.entity.Apply
import reservation.project.domain.academy.service.ApplyService
import reservation.project.domain.customer.entity.Role
import reservation.project.domain.customer.service.CustomerService
import reservation.project.presentation.academy.dto.academyClass.AcademyClassRequest
import reservation.project.presentation.academy.dto.academyClass.AcademyClassUpdateDto
import reservation.project.presentation.academy.dto.academyClass.AppliedInfoRequest
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDto

@Component
class AcademyClassUseCase(
    private val customerService: CustomerService,
    private val academyService: AcademyService,
    private val academyInstructorService: AcademyInstructorService,
    private val academyClassService: AcademyClassService,
    private val applyService: ApplyService
) {

    fun saveClassInfo(req: AcademyClassRequest): ResponseDto<Boolean> {
        val customerInfo = customerService.findCustomerInfo(req.customerUid)
        if(customerInfo!!.roles != Role.ADMIN.toString()){
            throw ErrorException(Response.SC_BAD_REQUEST, "Not Admin")
        }

        val academyInfo = academyService.findAcademyInfo(customerInfo.id)
        academyInstructorService.findInfoByAcademyIdAndCustomerId(customerInfo.id, academyInfo.id)

        academyClassService.save(req.toEntity(ClassStatus.WAITING))

        return ResponseDto(200, true)
    }

    @Transactional
    fun applyStudent(req: AppliedInfoRequest): ResponseDto<Boolean>{
        val academyClassInfo = academyClassService.findById(req.classId)
        if(academyClassInfo.classStatus != ClassStatus.OPEN){
            throw ErrorException(Response.SC_NOT_FOUND, "Class is Not Opened")
        }

        val customerInfo = customerService.findCustomerInfo(req.userUid)

        if(customerInfo!!.roles != Role.USER.toString()){
            throw ErrorException(Response.SC_BAD_REQUEST, "Not User")
        }

        val applyInfoWithNullChecking = applyService.findByAcademyClassIdAndCustomerId(academyClassInfo.id!!, customerInfo.id)
        if(applyInfoWithNullChecking != null){
            throw ErrorException(Response.SC_BAD_REQUEST, "Class Applied")
        }

        if (!academyClassInfo.canApply()){
            throw ErrorException(Response.SC_BAD_REQUEST, "The Class is full")
        }
        val applyInfo = Apply(academyClass = academyClassInfo, customerId = customerInfo.id)
        academyClassInfo.applications.add(applyInfo)

        applyService.saveInfo(applyInfo)
        academyClassService.save(academyClassInfo)

        return ResponseDto(200, true)
    }

    fun updateClassInfo(req: AcademyClassUpdateDto): ResponseDto<Boolean>{
        val classId = req.academyClassId ?:throw ErrorException(Response.SC_NOT_FOUND, "Invalid Class ID")
        val existInfo = academyClassService.findById(classId)

        val getExistInfo = existInfo
        if (getExistInfo.classStatus == ClassStatus.OPEN){
            throw ErrorException(Response.SC_BAD_REQUEST, "AcademyClass is Open Status")
        }
        getExistInfo.updateFromDto(req)

        academyClassService.save(getExistInfo)


        return ResponseDto(200, true)
    }

    fun findClassInfo() {}

    fun cancelApply(){}


}
