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
import reservation.project.application.academyClass.AcademyClassUseCase
import reservation.project.domain.academy.entity.*
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

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class ApplyIntegrationTest {
    private val logger = LoggerFactory.getLogger(ApplyIntegrationTest::class.java)

    @Autowired
    private lateinit var academyService: AcademyService

    @Autowired
    private lateinit var academyClassService: AcademyClassService

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    private lateinit var academyClassInstructorService: AcademyInstructorService

    @Autowired
    private lateinit var applyService: ApplyService

    @Autowired
    private lateinit var academyClassUsecase: AcademyClassUseCase

    private lateinit var openAcademyClass: AcademyClass
    private lateinit var openAcademyClass2: AcademyClass
    private lateinit var openAcademyClass3: AcademyClass
    private lateinit var openAcademyInfo: Academy
    private lateinit var customInfo :Customer
    private lateinit var customInfo2 :Customer
    private lateinit var customInfo3 :Customer
    private lateinit var customInfo4 :Customer
    private val time = LocalDateTime.now()

    @BeforeEach
    fun setup() {
        customInfo = customerService.saveCustomer(Customer(
            uid = "tcustom",
            passwordInfo = "12323",
            name = "testcus",
            roles = Role.USER.toString()
        ))
        customInfo2 = customerService.saveCustomer(Customer(
            uid = "tcustom2",
            passwordInfo = "12323",
            name = "testcus",
            roles = Role.ADMIN.toString()
        ))
        customInfo3 = customerService.saveCustomer(Customer(
            uid = "tcustom3",
            passwordInfo = "12323",
            name = "testcus",
            roles = Role.USER.toString()
        ))
        customInfo4 = customerService.saveCustomer(Customer(
            uid = "tcustom34",
            passwordInfo = "12323",
            name = "testcus",
            roles = Role.USER.toString()
        ))
        val academyInfo = Academy(academyName = "name", category = 1L,openTime = time, closeTime = time.plusHours(5), location = "location", socialNetworkAddress = "url", contactInfo = "0101111111",createdAt = time, updatedAt = time)
        openAcademyInfo =  academyService.saveAcademy(academyInfo)!!


        openAcademyClass = academyClassService.save(AcademyClass(
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
        ))!!

        openAcademyClass2 = academyClassService.save(AcademyClass(
            className = "math",
            maxAppliedStudents = 10,
            applicationStartTime = LocalDateTime.now().minusDays(1),
            applicationEndTime = LocalDateTime.now().plusDays(1),
            classStatus = ClassStatus.WAITING,
            classStartTime = LocalDateTime.now().plusDays(2),
            classEndTime = LocalDateTime.now().plusMonths(1),
            classDays = "MONDAY,TUESDAY",
            tuitionFee = 50000,
            academyId = 1L
        ))!!

        openAcademyClass = academyClassService.save(AcademyClass(
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
        ))!!

        openAcademyClass3 = academyClassService.save(AcademyClass(
            className = "math",
            maxAppliedStudents = 1,
            applicationStartTime = LocalDateTime.now().minusDays(1),
            applicationEndTime = LocalDateTime.now().plusDays(1),
            classStatus = ClassStatus.OPEN,
            classStartTime = LocalDateTime.now().plusDays(2),
            classEndTime = LocalDateTime.now().plusMonths(1),
            classDays = "MONDAY,TUESDAY",
            tuitionFee = 50000,
            academyId = openAcademyInfo.id
        ))!!



        academyClassInstructorService.saveInfo(AcademyInstructor(0, customInfo.id ,openAcademyClass.id!!, InstructorRole.MASTER, time, time ))
        academyClassInstructorService.saveInfo(AcademyInstructor(0, customInfo2.id ,openAcademyClass2.id!!, InstructorRole.MASTER, time, time ))

        val apply = Apply(academyClass = openAcademyClass, customerId = customInfo.id)
        applyService.saveInfo(apply)

        val apply2 = Apply(academyClass = openAcademyClass3, customerId = customInfo.id)
        applyService.saveInfo(apply2)

    }

    @Test
    fun `applyStudent - Class is not Opned`() {
        val request = AppliedInfoRequest(customInfo.uid, openAcademyClass2.id!!, openAcademyInfo.id)

        val result = assertThrows<ErrorException>{
             academyClassUsecase.applyStudent(request)
        }

        assertEquals(result.statusCode, 404)
        assertEquals(result.errorMessage, "Class is Not Opened")
    }

    @Test
    fun `applyStudent - Class is not User`() {
        val request = AppliedInfoRequest(customInfo2.uid, openAcademyClass.id!!, openAcademyInfo.id)

        val result = assertThrows<ErrorException>{
            academyClassUsecase.applyStudent(request)
        }

        assertEquals(result.statusCode, 400)
        assertEquals(result.errorMessage, "Not User")
    }

    @Test
    fun `applyStudent - Applied Customer`() {
        val request = AppliedInfoRequest(customInfo.uid, openAcademyClass.id!!, openAcademyInfo.id)

        val result = assertThrows<ErrorException>{
            academyClassUsecase.applyStudent(request)
        }

        assertEquals(result.statusCode, 400)
        assertEquals(result.errorMessage, "Class Applied")
    }

    @Test
    fun `applyStudent - Class is full`() {
        val request = AppliedInfoRequest(customInfo3.uid, openAcademyClass3.id!!, openAcademyInfo.id)

        val result = assertThrows<ErrorException>{
            academyClassUsecase.applyStudent(request)
        }

        assertEquals(result.statusCode, 400)
        assertEquals(result.errorMessage, "The Class is full")
    }

    @Test
    fun `applyStudent - Success Save`() {
        val request = AppliedInfoRequest(customInfo4.uid, openAcademyClass.id!!, openAcademyInfo.id)

        val result = academyClassUsecase.applyStudent(request)

        assertEquals(result.code, 200)
        assertEquals(result.message, true)

        val applyInfo = applyService.findByAcademyClassId(openAcademyClass.id!!)
        assertEquals(applyInfo.size, 2)
    }


}
