package reservation.project.presentation.academy

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.academy.AcademyClassUseCase
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.presentation.academy.dto.AcademyClassRegisterReqDto
import reservation.project.presentation.academy.dto.AcademyClassReqDto
import reservation.project.presentation.academy.dto.AcademyClassUpdateReqDto
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.response.ResponseDto

@RestController
@RequestMapping("/class")
class AcademyClassController(
    private val academyClassUseCase: AcademyClassUseCase
) {

    @PostMapping("/apply")
    fun applyClass(): ResponseEntity<ResponseDto<String>> {
        return ResponseEntity.ok(ResponseDto(200, "Success"))
    }

    @PostMapping("/register")
    fun registerClassInfo(req: AcademyClassRegisterReqDto): ResponseEntity<ResponseDto<String>> {
        return ResponseEntity.ok(ResponseDto(200, academyClassUseCase.registerClassInfo(req)))
    }

    @PostMapping("/modify")
    fun updateClassInfo(req: AcademyClassUpdateReqDto): ResponseEntity<ResponseDto<String>>{
        return ResponseEntity.ok(ResponseDto(200, academyClassUseCase.updateClassInfo(req)))
    }

    @PostMapping("/info")
    fun updateClassInfo(req: AcademyClassReqDto): ResponseEntity<ResponseDataDto<AcademyClass>>{
        return ResponseEntity.ok(ResponseDataDto(200, "Success" ,academyClassUseCase.findByAcademyIdAndAdminId(req)))
    }

    @GetMapping("/academy/{academyId}")
    fun getClassInfoByAcademyId(@PathVariable academyId: Long): ResponseEntity<ResponseDataDto<List<AcademyClass>>>{
        return ResponseEntity.ok(ResponseDataDto(200, "Success", academyClassUseCase.findByAcademyId(academyId)))
    }

    @GetMapping("/{academyClassId}")
    fun getClassInfoByAcademyClassId(@PathVariable academyClassId: Long): ResponseEntity<ResponseDataDto<AcademyClass>>{
        return ResponseEntity.ok(ResponseDataDto(200, "Success", academyClassUseCase.findByAcademyClassId(academyClassId)))
    }

    @GetMapping("/user/{userId}")
    fun getClassInfoByUserId(@PathVariable userId: Long): ResponseEntity<ResponseDataDto<List<AcademyClass>>>{
        return ResponseEntity.ok(ResponseDataDto(200, "Success", academyClassUseCase.findByCustomerId(userId)))
    }

    @GetMapping("/admin/{adminId}")
    fun getClassInfoByAdminId(@PathVariable adminId: Long): ResponseEntity<ResponseDataDto<List<AcademyClass>>>{
        return ResponseEntity.ok(ResponseDataDto(200, "Success", academyClassUseCase.findByAdminId(adminId)))
    }
}