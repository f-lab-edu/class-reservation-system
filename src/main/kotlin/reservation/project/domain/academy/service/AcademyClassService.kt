package reservation.project.domain.academy.service

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.entity.Apply
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.infra.academy.JpaAcademyClassRepository
import reservation.project.presentation.academy.dto.academyClass.AcademyClassUpdateDto
import reservation.project.presentation.advice.exception.ErrorException

@Service
class AcademyClassService(
    private val jpaAcademyClassRepository: JpaAcademyClassRepository,
)  {
    fun findById(id: Long): AcademyClass {
        return jpaAcademyClassRepository.findById(id).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "AcademyClass Not Found") }
    }
    fun save(academyClass: AcademyClass): AcademyClass? {
        return jpaAcademyClassRepository.save(academyClass)
    }

    fun updateInfo(req: AcademyClassUpdateDto) {
        val classId = req.academyClassId ?:throw ErrorException(Response.SC_NOT_FOUND, "Invalid Class ID")
        val existInfo = jpaAcademyClassRepository.findById(classId)
        if(!existInfo.isPresent){
            throw ErrorException(Response.SC_BAD_REQUEST, "AcademyClass Not Exists")
        }
        val getExistInfo = existInfo.get()
        if (getExistInfo.classStatus == ClassStatus.OPEN){
            throw ErrorException(Response.SC_BAD_REQUEST, "AcademyClass is Open Status")
        }
        getExistInfo.updateFromDto(req)
        jpaAcademyClassRepository.save(getExistInfo)
    }



}
