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
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.academy.entity.Apply
import reservation.project.infra.academy.JpaAcademyClassRepository
import reservation.project.presentation.academy.dto.academyClass.AcademyClassUpdateDto
import java.time.LocalDateTime
import kotlin.concurrent.thread

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = ["classpath:application-test.yml"])
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
    @Transactional
    fun `멀티스레드 환경에서 applyFor() 실행 시 낙관적 락 충돌 테스트`() {
        var exceptionOccurred = 0
        val academyClass = jpaAcademyClassRepository.findAll().first()
        val classId = academyClass.id

        val thread1 = thread {
            try {
                val apply = Apply(academyClass = testAcademyClass, customerId = 101L)
                academyClass.applications.add(apply)
                academyClassService.save(academyClass) // 실제 신청 로직 실행
            } catch (e: Exception) {
                println("Thread 1 Exception: ${e.message}")
            }
        }

        val thread2 = thread {
            Thread.sleep(100) // 일부러 딜레이를 줘서 thread1이 먼저 저장하도록 함
            try {
                val apply = Apply(academyClass = testAcademyClass, customerId = 101L)
                academyClass.applications.add(apply)
                academyClassService.save(academyClass) // 실제 신청 로직 실행
            } catch (e: ObjectOptimisticLockingFailureException) {
                exceptionOccurred += 1
                println("Expected OptimisticLockException: ${e.message}")
            }
        }



        thread1.join()
        thread2.join()

        assertEquals(exceptionOccurred, 1)
    }



}
