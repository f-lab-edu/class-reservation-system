package reservation.project.application.academy

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Component
import reservation.project.application.security.util.GenerateService
import reservation.project.domain.academy.entity.SecretManagement
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.academy.service.CategoryService
import reservation.project.domain.academy.service.SecretManagementService
import reservation.project.presentation.academy.dto.AcademyReqDto
import reservation.project.presentation.academy.dto.AcademyResDto
import reservation.project.presentation.academy.dto.AcademySearchingResData
import reservation.project.presentation.advice.exception.ErrorException

@Component
class AcademyUseCase(
    private val academyService: AcademyService,
    private val secretManagementService: SecretManagementService,
    private val categoryService: CategoryService,
    private val generateService: GenerateService
)  {
     fun registerAcademy(req: AcademyReqDto): AcademyResDto {
        val categoryInfo = categoryService.findByCategoryId(req.categoryId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found categoryInfo")
        }
        val academy = req.toEntity()
        academy.category = categoryInfo

        val saveAcademy = academyService.save(academy).orElseThrow {
            ErrorException(Response.SC_BAD_REQUEST, "Academy Save Error")
        }

        val secretCode = generateService.generateSecretCode()

        val secretManage = secretManagementService.save(SecretManagement(0, saveAcademy, secretCode)).orElseThrow {
            ErrorException(Response.SC_BAD_REQUEST, "SecretCode Save Error")
        }

        return AcademyResDto(secretManage.secretKey)
    }

     fun searchingAcademy(academyId: Long): AcademySearchingResData {
        val data = academyService.findByAcademyId(academyId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "Academy data is not found")
        }
        return AcademySearchingResData.of(data)
    }


}