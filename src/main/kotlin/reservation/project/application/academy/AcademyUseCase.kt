package reservation.project.application.academy

import reservation.project.presentation.academy.dto.AcademyReqDto
import reservation.project.presentation.academy.dto.AcademyResDto

interface AcademyUseCase {
    fun registerAcademy(req: AcademyReqDto): AcademyResDto
}