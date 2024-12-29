package reservation.project.application.academy

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Component
import reservation.project.domain.academy.entity.SecretManagement
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.academy.service.CategoryService
import reservation.project.domain.academy.service.SecretManagementService
import reservation.project.presentation.academy.dto.AcademyReqDto
import reservation.project.presentation.academy.dto.AcademyResDto
import reservation.project.presentation.academy.dto.AcademySearchingResData
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDto

@Component
class AcademyUseCase(
    private val academyService: AcademyService,
    private val secretManagementService: SecretManagementService,
    private val categoryService: CategoryService
)  {
     fun registerAcademy(req: AcademyReqDto): AcademyResDto {
        val categoryInfo = categoryService.findByCategoryId(req.categoryId).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "not found categoryInfo"))
        }
        val academy = req.toEntity()
        academy.category = categoryInfo

        val saveAcademy = academyService.save(academy).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "Academy Save Error"))
        }

        val secretCode = generateSecretCode()

        val secretManage = secretManagementService.save(SecretManagement(0, saveAcademy, secretCode)).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "SecretCode Save Error"))
        }

        return AcademyResDto(secretManage.secretKey)
    }

     fun searchingAcademy(academyId: Long): AcademySearchingResData {
        val data = academyService.findByAcademyId(academyId).orElseThrow {
            ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "Academy data is not found"))
        }
        return AcademySearchingResData.of(data)
    }

    private fun generateSecretCode(): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..10)
            .map { chars.random() }
            .joinToString("")
    }
}