package reservation.project.presentation.academy

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.academyClass.AcademyClassUseCase

@RestController
@RequestMapping("/class")
class AcademyClassController(
    private val academyClassUseCase: AcademyClassUseCase
) {


}
