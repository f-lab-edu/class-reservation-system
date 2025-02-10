package reservation.project.service.academy

import jakarta.persistence.OptimisticLockException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.academy.entity.Apply
import reservation.project.infra.academy.JpaAcademyClassRepository
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.logging.Logger
import kotlin.concurrent.thread

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = ["classpath:application-test.yml"])
//@Transactional
//@DirtiesContext
class ApplyOptimisticTest {

    val log = org.slf4j.LoggerFactory.getLogger(ApplyOptimisticTest::class.java)

    @Autowired
    private lateinit var jpaAcademyClassRepository: JpaAcademyClassRepository

    @Autowired
    private lateinit var academyClassService: AcademyClassService

    private lateinit var testAcademyClass: AcademyClass

    @BeforeEach
    fun setup() {
        testAcademyClass = AcademyClass(
            className = "Math 101",
            maxAppliedStudents = 10,
            applicationStartTime = LocalDateTime.now().minusDays(1),
            applicationEndTime = LocalDateTime.now().plusDays(1),
            classStatus = ClassStatus.WAITING,
            classStartTime = LocalDateTime.now().plusDays(2),
            classEndTime = LocalDateTime.now().plusMonths(1),
            classDays = "MONDAY,WEDNESDAY,FRIDAY",
            tuitionFee = 1000,
            academyId = 1L
        )
        jpaAcademyClassRepository.save(testAcademyClass)
    }


    @Test
    fun `강의 ID로 강의를 정상적으로 조회할 수 있다`() {
        val foundClass = academyClassService.findById(testAcademyClass.id!!)
        assertNotNull(foundClass)
        assertEquals(testAcademyClass.className, foundClass.className)
    }

    @Test
    fun `새로운 강의를 정상적으로 저장할 수 있다`() {
        val newAcademyClass = AcademyClass(
            className = "Physics 101",
            maxAppliedStudents = 15,
            applicationStartTime = LocalDateTime.now(),
            applicationEndTime = LocalDateTime.now().plusDays(7),
            classStatus = ClassStatus.WAITING,
            classStartTime = LocalDateTime.now().plusDays(10),
            classEndTime = LocalDateTime.now().plusMonths(2),
            classDays = "TUESDAY,THURSDAY",
            tuitionFee = 1200,
            academyId = 1L
        )

        val savedClass = academyClassService.save(newAcademyClass)
        assertNotNull(savedClass?.id)
        assertEquals("Physics 101", savedClass?.className)
    }

    @Test
    fun `정상적으로 강의 신청을 할 수 있다`() {
        val apply = Apply(
            academyClass = testAcademyClass,
            customerId = 100L
        )

        academyClassService.applyFor(apply)

        val updatedClass = academyClassService.findById(testAcademyClass.id!!)
        assertEquals(1, updatedClass.applications.size)
        assertEquals(100L, updatedClass.applications.first().customerId)
    }

    @Test
    fun `동시 신청 시 낙관적 락이 정상적으로 동작하는지 확인`() {
        val apply1 = Apply(academyClass = testAcademyClass, customerId = 101L)
        val apply2 = Apply(academyClass = testAcademyClass, customerId = 102L)

        academyClassService.applyFor(apply1)
        academyClassService.applyFor(apply2)

        val updatedClass = academyClassService.findById(testAcademyClass.id!!)
        assertEquals(2, updatedClass.applications.size)
    }

    @Test
    fun `멀티스레드 환경에서 applyFor() 실행 시 낙관적 락 충돌 테스트`() {
        var exceptionOccurred = false

        val thread1 = thread {
            try {
                applyForClass(testAcademyClass.id!!, 101L) // 고객 ID 101 신청
            } catch (e: Exception) {
                println("Thread 1 Exception: ${e.message}")
            }
        }

        val thread2 = thread {
            Thread.sleep(100) // 일부러 딜레이를 줘서 thread1이 먼저 저장하도록 함
            try {
                applyForClass(testAcademyClass.id!!, 102L) // 고객 ID 102 신청
            } catch (e: OptimisticLockException) {
                exceptionOccurred = true
                println("Expected OptimisticLockException: ${e.message}")
            }
        }

        thread1.join()
        thread2.join()

        assertTrue(exceptionOccurred, "OptimisticLockException이 발생해야 합니다")
    }

    @Transactional
    fun applyForClass(classId: Long, customerId: Long) {
        val academyClass = jpaAcademyClassRepository.findById(classId).orElseThrow()
        val apply = Apply(academyClass = academyClass, customerId = customerId)

        academyClassService.applyFor(apply) // 실제 신청 로직 실행
    }

}
