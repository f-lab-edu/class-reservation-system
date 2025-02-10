package reservation.project.service.academy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.http.HttpStatus
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.academy.entity.Apply
import reservation.project.infra.academy.JpaAcademyClassRepository
import reservation.project.presentation.advice.exception.ErrorException
import java.time.LocalDateTime
import java.util.Optional


@ExtendWith(MockitoExtension::class)
class AcademyClassServiceTest {

    @Mock
    private lateinit var jpaAcademyClassRepository: JpaAcademyClassRepository

    @InjectMocks
    private lateinit var academyClassService: AcademyClassService

    private lateinit var testAcademyClass : AcademyClass

    @BeforeEach
    fun setUp() {
        testAcademyClass = AcademyClass(
            id = 1L,
            version = 1,
            className = "Kotlin Programming",
            maxAppliedStudents = 10,
            applicationStartTime = LocalDateTime.now().minusDays(1),
            applicationEndTime = LocalDateTime.now().plusDays(1),
            classStatus = ClassStatus.OPEN,
            classStartTime = LocalDateTime.now().plusDays(2),
            classEndTime = LocalDateTime.now().plusMonths(1),
            classDays = "MONDAY,TUESDAY",
            tuitionFee = 50000,
            academyId = 1L
        )
    }

    @Test
    fun `should throw exception when found`() {
        // given
        whenever(jpaAcademyClassRepository.findById(1L)).thenReturn(Optional.of(testAcademyClass))

        // when
        var result = academyClassService.findById(1L)

        // then
        assertNotNull(result)
        assertEquals(1L, result.id)
        assertEquals("Kotlin Programming", result.className)
        verify(jpaAcademyClassRepository, times(1)).findById(1L)
    }

    @Test
    fun `should throw exception when AcademyClass is not found`() {
        // given
        whenever(jpaAcademyClassRepository.findById(999L)).thenReturn(Optional.empty())

        // then
        val exception = assertThrows(ErrorException::class.java) {
            academyClassService.findById(999L)
        }
        assertEquals("AcademyClass Not Found", exception.errorMessage)
        verify(jpaAcademyClassRepository, times(1)).findById(999L)

    }

    @Test
    fun `should save a new AcademyClass successfully`(){
        // given
        val newAcademyClass = AcademyClass(
            id = null,  // 새로운 객체이므로 ID는 null
            version = 1,
            className = "Kotlin Programming",
            maxAppliedStudents = 1,
            applicationStartTime = LocalDateTime.now().minusDays(1),
            applicationEndTime = LocalDateTime.now().plusDays(1),
            classStatus = ClassStatus.OPEN,
            classStartTime = LocalDateTime.now().plusDays(2),
            classEndTime = LocalDateTime.now().plusMonths(1),
            classDays = "MONDAY,TUESDAY",
            tuitionFee = 50000,
            academyId = 1L
        )
        whenever(jpaAcademyClassRepository.save(any<AcademyClass>())).thenReturn(newAcademyClass)

        // when
        val savedClass = academyClassService.save(newAcademyClass)

        // then
        assertNotNull(savedClass)
        assertEquals("Kotlin Programming", savedClass?.className)
        verify(jpaAcademyClassRepository, times(1)).save(any())
    }

    @Test
    fun `should apply successfully when class has available slots`() {
        // given
        val apply = Apply(id = null, academyClass = testAcademyClass, customerId = 100L)
        whenever(jpaAcademyClassRepository.findById(1L)).thenReturn(Optional.of(testAcademyClass))

        //when
        academyClassService.applyFor(apply)

        // then
        assertEquals(1, testAcademyClass.applications.size)
        assertEquals(100L, testAcademyClass.applications[0].customerId)

        verify(jpaAcademyClassRepository, times(1)).save(testAcademyClass)
    }

    @Test
    fun `applyFor - Not exists Class`(){
        // given
        val apply = Apply(id = 1L, academyClass = testAcademyClass, customerId = 200L)

        whenever(jpaAcademyClassRepository.findById(1L)).thenReturn(Optional.empty())

        // when & then
        val exception = assertThrows<ErrorException>{
            academyClassService.applyFor(apply)
        }

        assertEquals(HttpStatus.NOT_FOUND.value(), exception.statusCode)
        assertEquals("AcademyClass not found", exception.errorMessage)

        verify(jpaAcademyClassRepository, never()).save(any())
    }

    @Test
    fun `applyFor - Exceeded number of people`() {
// Given
        val academyClass = AcademyClass(
            id = 1L,
            className = "Science Class",
            maxAppliedStudents = 1, // 최대 1명 신청 가능
            applicationStartTime = LocalDateTime.now().minusDays(1),
            applicationEndTime = LocalDateTime.now().plusDays(1),
            classStatus = ClassStatus.OPEN,
            classStartTime = LocalDateTime.now().plusDays(2),
            classEndTime = LocalDateTime.now().plusMonths(1),
            classDays = "MONDAY,TUESDAY",
            tuitionFee = 60000,
            academyId = 100L,
            applications = mutableListOf(Apply(id = 2L, academyClass = testAcademyClass, customerId = 300L)) // 이미 1명 신청 완료
        )

        val apply = Apply(
            id = 3L,
            academyClass = academyClass,
            customerId = 400L
        )

        whenever(jpaAcademyClassRepository.findById(1L)).thenReturn(Optional.of(academyClass))

        val exception = assertThrows<ErrorException> {
            academyClassService.applyFor(apply)
        }

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.statusCode)
        assertEquals("Class is already full", exception.errorMessage)
        verify(jpaAcademyClassRepository, never()).save(any())
    }

}
