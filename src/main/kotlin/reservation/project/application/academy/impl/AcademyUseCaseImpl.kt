package reservation.project.application.academy.impl

import org.springframework.stereotype.Component
import reservation.project.application.academy.AcademyUseCase
import reservation.project.domain.academy.entity.SecretManagement
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.academy.service.CategoryService
import reservation.project.domain.academy.service.SecretManagementService
import reservation.project.presentation.academy.dto.AcademyReqDto
import reservation.project.presentation.academy.dto.AcademyResDto
import reservation.project.presentation.advice.exception.NoInfoException
import reservation.project.presentation.response.ResponseDto

@Component
class AcademyUseCaseImpl(
    private val academyService: AcademyService,
    private val secretManagementService: SecretManagementService,
    private val categoryService: CategoryService
) : AcademyUseCase {
    override fun registerAcademy(req: AcademyReqDto): AcademyResDto {
        val categoryInfo = categoryService.findByCategoryId(req.categoryId).orElseThrow {
            NoInfoException(ResponseDto(500, "not found categoryInfo", null))
        }
        val academy = req.toEntity()
        academy.category = categoryInfo

        val saveAcademy = academyService.save(academy).orElseThrow {
            Exception("Academy Save Error")
        }

        val secretCode = generateSecretCode()

        val secretManage = secretManagementService.save(SecretManagement(0, saveAcademy, secretCode)).orElseThrow {
            Exception("SecretCode Save Error")
        }

        return AcademyResDto(secretManage.secretKey)
    }

    private fun generateSecretCode(): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..10)
            .map { chars.random() }
            .joinToString("")
    }
}