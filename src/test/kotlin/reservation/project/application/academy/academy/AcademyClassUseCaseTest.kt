package reservation.project.application.academy.academy

import jakarta.persistence.OptimisticLockException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reservation.project.application.academyClass.AcademyClassUseCase
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.service.AcademyInstructorService
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.academy.service.ApplyService
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.entity.Role
import reservation.project.domain.customer.service.CustomerService
import reservation.project.presentation.academy.dto.academyClass.AcademyClassRequest
import reservation.project.presentation.academy.dto.academyClass.AppliedInfoRequest
import reservation.project.presentation.advice.exception.ErrorException
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class AcademyClassUseCaseTest {
    @Mock
    private lateinit var customerService: CustomerService

    @Mock
    private lateinit var academyService: AcademyService

    @Mock
    private lateinit var academyInstructorService: AcademyInstructorService

    @Mock
    private lateinit var academyClassService: AcademyClassService

    @Mock
    private lateinit var applyService: ApplyService

    @InjectMocks
    private lateinit var academyClassUseCase: AcademyClassUseCase

    private lateinit var testAcademyClass: AcademyClass
    private lateinit var openAcademyClass: AcademyClass

    @BeforeEach
    fun setUp() {
        testAcademyClass = AcademyClass(
            id = 1L,
            className = "Math 101",
            maxAppliedStudents = 10,
            applicationStartTime = LocalDateTime.now(),
            applicationEndTime = LocalDateTime.now().plusDays(7),
            classStatus = ClassStatus.WAITING,
            classStartTime = LocalDateTime.now().plusDays(10),
            classEndTime = LocalDateTime.now().plusDays(20),
            classDays = "MONDAY,TUESDAY",
            tuitionFee = 500,
            academyId = 1L
        )

        openAcademyClass = AcademyClass(
            id = 2L,
            className = "Science 101",
            maxAppliedStudents = 10,
            applicationStartTime = LocalDateTime.now(),
            applicationEndTime = LocalDateTime.now().plusDays(7),
            classStatus = ClassStatus.OPEN,
            classStartTime = LocalDateTime.now().plusDays(10),
            classEndTime = LocalDateTime.now().plusDays(20),
            classDays = "MONDAY,TUESDAY",
            tuitionFee = 500,
            academyId = 2L
        )
    }

    @Test
    fun `saveClassInfo should save class when user is admin`() {
        // Given
        val request = AcademyClassRequest(
            customerUid = "admin123",
            className = "Math 101",
            maxAppliedStudents = 10,
            applicationStartTime = LocalDateTime.now(),
            applicationEndTime = LocalDateTime.now().plusDays(7),
            classStartTime = LocalDateTime.now().plusDays(10),
            classEndTime = LocalDateTime.now().plusDays(20),
            classDays = listOf("MONDAY,TUESDAY"),
            tuitionFee = 500,
            academyId = 1L
        )

        val adminCustomer = Customer(id = 1L, uid = "admin123", passwordInfo = "12323", name = "test", roles = Role.USER.toString())

        whenever(customerService.findCustomerInfo("admin123")).thenReturn(adminCustomer)
        // When
        val response = assertThrows<ErrorException> {academyClassUseCase.saveClassInfo(request)  }

        // Then
        assertEquals(400, response.statusCode)
        assertEquals("Not Admin", response.errorMessage)
        verify(customerService).findCustomerInfo(adminCustomer.uid)
    }

    @Test
    fun `applyStudent should allow user to apply for open class`() {
        // Given
        val request = AppliedInfoRequest(classId = 1L, userUid = "user123", academyId = 2L)
        val userCustomer = Customer(id = 2L, uid = "user123", passwordInfo = "!234", name = "testAdmin", roles = Role.USER.toString())

        whenever(academyClassService.findById(1L)).thenReturn(openAcademyClass)
        whenever(customerService.findCustomerInfo("user123")).thenReturn(userCustomer)
        whenever(applyService.findByAcademyClassIdAndCustomerId(2L, 2L)).thenReturn(null)
        whenever(academyClassService.save(any())).thenReturn(openAcademyClass)

        // When
        val response = academyClassUseCase.applyStudent(request)

        // Then
        assertEquals(200, response.code)
        assertTrue(response.message)
        verify(academyClassService).save(any())
    }

    @Test
    fun `applyStudent should not allow user to apply for not open class`() {
        // Given
        val request = AppliedInfoRequest(classId = 1L, userUid = "user123", academyId = 2L)

        whenever(academyClassService.findById(1L)).thenReturn(testAcademyClass)

        // When
        val exception = assertThrows(ErrorException::class.java) {
            academyClassUseCase.applyStudent(request)
        }


        // Then
        assertEquals(404, exception.statusCode)
        assertEquals("Class is Not Opened", exception.errorMessage)
    }
}
