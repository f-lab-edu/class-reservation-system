package reservation.project.application.academy.academy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import reservation.project.domain.academy.entity.Academy
import reservation.project.application.academyClass.AcademyClassUseCase
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.entity.InstructorRole
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.service.AcademyInstructorService
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.entity.Role
import reservation.project.domain.customer.service.CustomerService
import reservation.project.presentation.academy.dto.academyClass.AcademyClassRequest
import reservation.project.presentation.advice.exception.ErrorException
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class AcademyClassSaveIntegrationTest {
    private val logger = LoggerFactory.getLogger(AcademyClassSaveIntegrationTest::class.java)

    @Autowired
    private lateinit var academyService: AcademyService

    @Autowired
    private lateinit var academyClassService: AcademyClassService

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    private lateinit var academyClassInstructorService: AcademyInstructorService

    @Autowired
    private lateinit var academyClassUsecase: AcademyClassUseCase

    private lateinit var openAcademyClass: AcademyClass
    private lateinit var customInfo :Customer
    private lateinit var customInfo2 :Customer
    private val time = LocalDateTime.now()

    @BeforeEach
    fun setup() {
        openAcademyClass = AcademyClass(
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
        customInfo = Customer(
            uid = "tcustom",
            passwordInfo = "12323",
            name = "testcus",
            roles = Role.USER.toString()
        )

        customInfo2 = Customer(
            uid = "tcustom",
            passwordInfo = "12323",
            name = "testcus",
            roles = Role.ADMIN.toString()
        )
        customerService.saveCustomer(customInfo2)
        val academyInfo = Academy(academyName = "name", category = 1L,openTime = time, closeTime = time.plusHours(5), location = "location", socialNetworkAddress = "url", contactInfo = "0101111111",createdAt = time, updatedAt = time)
        academyService.saveAcademy(academyInfo)
    }

    @Test
    fun `DB Connect Test`() {
        val result = academyClassService.save(openAcademyClass)!!

        assertEquals(result.maxAppliedStudents, 10)
    }

    @Test
    fun `saveClassInfo - custom is not admin`() {
         customerService.saveCustomer(customInfo)
        val time = LocalDateTime.now()
        val request = AcademyClassRequest("className", 5, time, time.plusHours(4), time.plusHours(24), time.plusHours(40), listOf("MONDAY, TUESDAY"), 100000, 1L,"tcustom")

        var result = assertThrows<ErrorException>{
             academyClassUsecase.saveClassInfo(request)
        }

        assertEquals(result.statusCode, 400)
        assertEquals(result.errorMessage, "Not Admin")
    }

    @Test
    fun `saveClassInfo - academyInfo not exist`() {
        customInfo = Customer(
            uid = "tcustom",
            passwordInfo = "12323",
            name = "testcus",
            roles = Role.ADMIN.toString()
        )
        customerService.saveCustomer(customInfo)
        val time = LocalDateTime.now()
        val request = AcademyClassRequest("className", 5, time, time.plusHours(4), time.plusHours(24), time.plusHours(40), listOf("MONDAY, TUESDAY"), 100000, 1L,"tcustom")

        var result = assertThrows<ErrorException>{
            academyClassUsecase.saveClassInfo(request)
        }

        assertEquals(result.statusCode, 404)
        assertEquals(result.errorMessage, "Academy Not Found")
    }

    @Test
    fun `saveClassInfo - instructor not exist`() {
        val request = AcademyClassRequest("className", 5, time, time.plusHours(4), time.plusHours(24), time.plusHours(40), listOf("MONDAY, TUESDAY"), 100000, 2L,"tcustom")

        var result = assertThrows<ErrorException>{
            academyClassUsecase.saveClassInfo(request)
        }

        assertEquals(result.statusCode, 404)
        assertEquals(result.errorMessage, "Academy Instructor Not Found")
    }

    @Test
    fun `saveClassInfo - Success`() {
        val customInfo = Customer(
            uid = "tcustom2",
            passwordInfo = "12323",
            name = "testcus",
            roles = Role.ADMIN.toString()
        )
        val customResult = customerService.saveCustomer(customInfo)

        val academyInfo = Academy(academyName = "name", category = 1L,openTime = time, closeTime = time.plusHours(5), location = "location", socialNetworkAddress = "url", contactInfo = "0101111111",createdAt = time, updatedAt = time)
        val academySaveResult = academyService.saveAcademy(academyInfo)

        academyClassInstructorService.saveInfo(AcademyInstructor(0, customResult.id ,academySaveResult!!.id, InstructorRole.MASTER, time, time ))

        val request = AcademyClassRequest("className", 5, time, time.plusHours(4), time.plusHours(24), time.plusHours(40), listOf("MONDAY, TUESDAY"), 100000, academySaveResult.id,"tcustom2")

        val result =  academyClassUsecase.saveClassInfo(request)

        assertEquals(result.code, 200)
        assertTrue(result.message)
    }

}
