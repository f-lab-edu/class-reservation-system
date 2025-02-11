package reservation.project.service.academy

import org.apache.catalina.connector.Response.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.service.AcademyService
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.service.academy.repository.AcademyFakeJpaRepository
import java.time.LocalDateTime

class AcademyServiceTest {

    private val fakeRepository = AcademyFakeJpaRepository()
    private val academyService = AcademyService(fakeRepository)

    // 학원등록 성공
    @Test
    fun `Academy Test Success When Save`() {
        // 학원정보
        val local = LocalDateTime.now()
        val academyInfo = Academy(1L, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local)

        // 학원저장
        val saveAcademyInfo = academyService.saveAcademy(academyInfo)

        // 확인
        val result = academyService.findAcademyInfo(saveAcademyInfo!!.id)
        assertEquals(saveAcademyInfo, result)
    }

    @Test
    fun `Fail Test When Not Existing`() {
        //given
        val id = 1L

        //when
        val result = assertThrows<ErrorException> {
            academyService.findAcademyInfo(id)
        }

        //then
        assertEquals(SC_NOT_FOUND, result.statusCode)
        assertEquals("Academy Not Found", result.errorMessage)
    }
}