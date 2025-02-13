package reservation.project.application.academy.academy


import org.hibernate.validator.internal.util.Contracts.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.ObjectOptimisticLockingFailureException
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
import reservation.project.presentation.academy.dto.academyClass.AppliedInfoRequest
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class AcademyClassUseCaseLockTest {

    val log = org.slf4j.LoggerFactory.getLogger(AcademyClassUseCaseLockTest::class.java)
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
    private lateinit var academyClassUseCase: AcademyClassUseCase

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
            roles = Role.USER.toString()
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
            maxAppliedStudents = 1,
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
            maxAppliedStudents = 2,
            applicationStartTime = LocalDateTime.now().minusDays(1),
            applicationEndTime = LocalDateTime.now().plusDays(1),
            classStatus = ClassStatus.OPEN,
            classStartTime = LocalDateTime.now().plusDays(2),
            classEndTime = LocalDateTime.now().plusMonths(1),
            classDays = "MONDAY,TUESDAY",
            tuitionFee = 50000,
            academyId = 1L
        ))!!

        openAcademyClass = academyClassService.save(AcademyClass(
            className = "Kotlin Programming",
            maxAppliedStudents = 3,
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
        academyClassInstructorService.saveInfo(AcademyInstructor(0, customInfo3.id ,openAcademyClass3.id!!, InstructorRole.MASTER, time, time ))
        academyClassInstructorService.saveInfo(AcademyInstructor(0, customInfo4.id ,openAcademyClass3.id!!, InstructorRole.NORMAL, time, time ))
//
//        val apply = Apply(academyClass = openAcademyClass, customerId = customInfo.id)
//        applyService.saveInfo(apply)
//
//        val apply2 = Apply(academyClass = openAcademyClass3, customerId = customInfo.id)
//        applyService.saveInfo(apply2)

    }


    @Test
    fun `should throw OptimisticLockException when concurrent updates occur`() {

        val request1 = AppliedInfoRequest(classId = openAcademyClass.id!!, userUid = customInfo.uid, academyId = 1L)
        val request2 = AppliedInfoRequest(classId = openAcademyClass.id!!, userUid = customInfo2.uid, academyId = 1L)

        // 동시 실행을 위한 ExecutorService 및 CountDownLatch
        val executorService = Executors.newFixedThreadPool(2)
        val latch = CountDownLatch(2)

        // 결과를 저장할 리스트
        val exceptions = mutableListOf<Exception?>()

        // When: 두 개의 요청을 동시에 실행
        executorService.execute {
            try {
                log.info("thread num 1 시작")
                academyClassUseCase.applyStudent(request1)
            } catch (e: Exception) {
                log.error("thread num 1 실패", e)
                exceptions.add(e)
            } finally {
                latch.countDown()
            }
        }

        executorService.execute {
            try {
                Thread.sleep(100)
                log.info("thread num 2 시작")
                academyClassUseCase.applyStudent(request2)
            } catch (e: Exception) {
                log.error("thread num 2 실패", e)
                exceptions.add(e)
            } finally {
                latch.countDown()
            }
        }

        // 두 개의 요청이 모두 끝날 때까지 대기
        latch.await()

        // Then: OptimisticLockException이 발생했는지 확인
        assertTrue(exceptions.any { it is ObjectOptimisticLockingFailureException }, "OptimisticLockException이 발생해야 합니다.")
    }
}
