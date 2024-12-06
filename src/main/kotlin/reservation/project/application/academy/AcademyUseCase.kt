package reservation.project.application.academy

import reservation.project.presentation.academy.dto.AcademyReqDto
import reservation.project.presentation.academy.dto.AcademyResDto
import reservation.project.presentation.academy.dto.AcademySearchingResData

interface AcademyUseCase {
    fun registerAcademy(req: AcademyReqDto): AcademyResDto
    fun searchingAcademy(academyId: Int): AcademySearchingResData
}