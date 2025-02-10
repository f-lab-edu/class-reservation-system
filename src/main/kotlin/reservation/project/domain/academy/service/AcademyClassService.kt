package reservation.project.domain.academy.service

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.entity.Apply
import reservation.project.infra.academy.JpaAcademyClassRepository
import reservation.project.presentation.advice.exception.ErrorException

@Service
class AcademyClassService(
    private val jpaAcademyClassRepository: JpaAcademyClassRepository,
)  {
    @Transactional(readOnly = true)
    fun findById(id: Long): AcademyClass {
        return jpaAcademyClassRepository.findById(id).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "AcademyClass Not Found") }
    }

    @Transactional
    fun save(academyClass: AcademyClass): AcademyClass? {
        return jpaAcademyClassRepository.save(academyClass)
    }

    @Transactional
    fun applyFor(apply: Apply) {
        println("DEBUG: apply.academyClass.id = ${apply.academyClass.id}")
        val classId = apply.academyClass.id ?: throw ErrorException(Response.SC_NOT_FOUND, "Invalid Class ID")
        val academyClass = jpaAcademyClassRepository.findById(classId)
            .orElseThrow {
                ErrorException(Response.SC_NOT_FOUND, "AcademyClass not found") }
        println("DEBUG: academyClass =" + academyClass.className)

        academyClass.applications.add(apply)
        if (!academyClass.canApply()){
            throw ErrorException(Response.SC_BAD_REQUEST, "Class is already full")
        }

        jpaAcademyClassRepository.save(academyClass)
    }

}
