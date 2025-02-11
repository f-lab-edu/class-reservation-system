package reservation.project.service.academy

import org.apache.catalina.connector.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.service.AcademyInstructorService
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.service.academy.repository.AcademyInstructorFakeRepository
import java.time.LocalDateTime

class AcademyInstructorServiceTest {

    private lateinit var fakeRepository: AcademyInstructorFakeRepository
    private lateinit var service: AcademyInstructorService
    private val logger = LoggerFactory.getLogger(AcademyInstructorService::class.java)

    @BeforeEach
    fun setUp() {
        fakeRepository = AcademyInstructorFakeRepository()
        service = AcademyInstructorService(fakeRepository)
    }

    @Test
    fun `Success Test InstructorInfo should save and return not null`() {
        // given
        val now = LocalDateTime.now()
        val instructorInfo = AcademyInstructor(
            id= 0,
            customerId = 2L,
            academyId = 3L,
            createdAt = now,
            updatedAt = now
        )

        //when
        val savedInstructor = service.saveInfo(instructorInfo)

        //then
        assertThat(savedInstructor).isNotNull
        assertThat(savedInstructor.id).isEqualTo(1L)
    }

    @Test
    fun `findInfoById should return the correct AcademyInstructor`() {
        // Given
        val savedInstructor = service.saveInfo(AcademyInstructor(id = 0, academyId = 1L, customerId = 1L))

        // When
        val foundInstructor = service.findInfoById(savedInstructor.id)

        // Then
        assertThat(foundInstructor).isEqualTo(savedInstructor)
    }

    @Test
    fun `findInfoById should throw ErrorException when instructor not found`() {
        // When & Then
        val exception = assertThrows<ErrorException> {
            service.findInfoById(999L) // 존재하지 않는 ID
        }
        assertThat(exception.statusCode).isEqualTo(Response.SC_NOT_FOUND)
        assertThat(exception.errorMessage).isEqualTo("AcademyInstructor Not Found")
    }

    @Test
    fun `findInfoByCustomerId should return a list of instructors for the given customerId`() {
        // Given
        val result1 = service.saveInfo(AcademyInstructor(id = 0, academyId = 1L, customerId = 1L))
        val result2 = service.saveInfo(AcademyInstructor(id = 0, academyId = 2L, customerId = 1L))

        // When
        val instructors = service.findInfoByCustomerId(1)
        // Then
        assertThat(instructors).hasSize(2)
        assertEquals(instructors[1].academyId, 2L)
    }

    @Test
    fun `findInfoByAcademyId should return a list of instructors for the given academyId`() {
        // Given
        service.saveInfo(AcademyInstructor(id = 0, academyId = 1L, customerId = 1L))
        service.saveInfo(AcademyInstructor(id = 0, academyId = 1L, customerId = 2L))

        // When
        val instructors = service.findInfoByAcademyId(1L)

        // Then
        assertThat(instructors).hasSize(2)
    }

    @Test
    fun `findInfoByAcademyId should throw ErrorException when no instructors found`() {
        // When & Then
        val exception = assertThrows<ErrorException> {
            service.findInfoByAcademyId(999L) // 존재하지 않는 academyId
        }
        assertThat(exception.statusCode).isEqualTo(Response.SC_NOT_FOUND)
        assertThat(exception.errorMessage).isEqualTo("Academy Instructor Not Found")
    }

    @Test
    fun `findInfoByAcademyIdAndCustomerId should return the correct instructor`() {
        // Given
        val savedInstructor = service.saveInfo(AcademyInstructor(id = 0, academyId = 1L, customerId = 1L))

        // When
        val foundInstructor = service.findInfoByAcademyIdAndCustomerId(1L, 1L)

        // Then
        assertThat(foundInstructor).isEqualTo(savedInstructor)
    }

    @Test
    fun `findInfoByAcademyIdAndCustomerId should throw ErrorException when no match found`() {
        // When & Then
        val exception = assertThrows<ErrorException> {
            service.findInfoByAcademyIdAndCustomerId(999L, 999L) // 존재하지 않는 조건
        }
        assertThat(exception.statusCode).isEqualTo(Response.SC_NOT_FOUND)
        assertThat(exception.errorMessage).isEqualTo("Academy Instructor Not Found")
    }

    @Test
    fun `checkingValue Method should null when result is null`() {
        // When & Then
        val result = service.checkingValue(999L, 999L)
        assertThat(result).isNull()
    }
}