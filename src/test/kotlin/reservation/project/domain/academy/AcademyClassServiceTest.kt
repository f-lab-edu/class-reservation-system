package reservation.project.domain.academy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.entity.Category
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.infra.academy.JpaAcademyClassRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class AcademyClassServiceTest{

    @Mock
    private lateinit var jpaAcademyClassRepository: JpaAcademyClassRepository

    @InjectMocks
    private lateinit var academyClassService: AcademyClassService

    @Test
    fun `findByAcademyId should return class when found`() {
        //given
        val id = 1L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(id, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        val academyClasses = listOf(
            AcademyClass(1L, academy, "name", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName", 1L,1L, "register"),
            AcademyClass(2L, academy, "name2", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName2", 2L,2L, "register")
        )
        `when`(jpaAcademyClassRepository.findByAcademyId(id)).thenReturn(academyClasses)

        //when
        val result = academyClassService.findByAcademyId(id)

        //then
        assertEquals(2, result.size)
        assertEquals("name2", result[1].className)
        verify(jpaAcademyClassRepository, times(1)).findByAcademyId(id)
    }

    @Test
    fun `findByAcademyId should return empty when not found`() {
        //given
        val id = 1L
        val academyClasses = emptyList<AcademyClass>()
        `when`(jpaAcademyClassRepository.findByAcademyId(id)).thenReturn(academyClasses)

        //when
        val result = academyClassService.findByAcademyId(id)

        //then
        assertEquals(0, result.size)
        verify(jpaAcademyClassRepository, times(1)).findByAcademyId(id)
    }

    @Test
    fun `findByAcademyClassId should return class when found`() {
        //given
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(2L, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        val academyClasses = Optional.of(
            AcademyClass(1L, academy, "name", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName", 1L,1L, "register")
        )
        `when`(jpaAcademyClassRepository.findByAcademyClassId(academy.academyId)).thenReturn(academyClasses)

        //when
        val result = academyClassService.findByAcademyClassId(academy.academyId)

        //then
        assertEquals(2, result.size)
        assertEquals("name2", result[1].className)
        verify(jpaAcademyClassRepository, times(1)).findByAcademyClassId(academy.academyId)
    }


    @Test
    fun `findByCustomerId should return class when found`() {
        //given
        val customerId = 3L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(2L, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        val academyClasses = listOf(
            AcademyClass(1L, academy, "name", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName", customerId,1L, "register"),
            AcademyClass(2L, academy, "name2", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName2", customerId,2L, "register")
        )
        `when`(jpaAcademyClassRepository.findByCustomerId(customerId)).thenReturn(academyClasses)

        //when
        val result = academyClassService.findByCustomerId(customerId)

        //then
        assertEquals(2, result.size)
        assertEquals("name2", result[1].className)
        verify(jpaAcademyClassRepository, times(1)).findByCustomerId(customerId)
    }


    @Test
    fun `findByAdminId should return class when found`() {
        //given
        val customerId = 3L
        val adminId = 10L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(2L, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        val academyClasses = listOf(
            AcademyClass(1L, academy, "name", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName", customerId,adminId, "register"),
            AcademyClass(2L, academy, "name2", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName2", customerId,adminId, "register")
        )
        `when`(jpaAcademyClassRepository.findByAdminId(adminId)).thenReturn(academyClasses)

        //when
        val result = academyClassService.findByAdminId(adminId)

        //then
        assertEquals(2, result.size)
        assertEquals("name2", result[1].className)
        verify(jpaAcademyClassRepository, times(1)).findByAdminId(adminId)
    }

    @Test
    fun `findByAcademyIdAndAdminId should return class when found`() {
        //given
        val customerId = 3L
        val adminId = 10L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(2L, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        val academyClass = Optional.of(
            AcademyClass(1L, academy, "name", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName", customerId,adminId, "register")
        )
        `when`(jpaAcademyClassRepository.findByAcademyIdAndAdminId(academy.academyId,adminId)).thenReturn(academyClass)

        //when
        val result = academyClassService.findByAcademyIdAndAdminId(academy.academyId,adminId)

        //then
        assertTrue(result.isPresent)
        assertEquals(result, academyClass)
        verify(jpaAcademyClassRepository, times(1)).findByAcademyIdAndAdminId(academy.academyId,adminId)
    }

    @Test
    fun `findByAcademyIdAndAdminId should return empty when not found`() {
        // given
        val academyId = 1L
        val adminId = 3L
        `when`(jpaAcademyClassRepository.findByAcademyIdAndAdminId(academyId, adminId)).thenReturn(Optional.empty())

        // then
        val result = academyClassService.findByAcademyIdAndAdminId(academyId, adminId)

        // then
        assertFalse(result.isPresent)
        verify(jpaAcademyClassRepository, times(1)).findByAcademyIdAndAdminId(academyId, adminId)
    }

    @Test
    fun `save should return class when found`() {
        //given
        val customerId = 3L
        val adminId = 10L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(2L, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        val academyClass =
            AcademyClass(1L, academy, "name", 10, time, time.plusDays(1), time.toLocalTime(), time.toLocalTime().plusHours(1), BigDecimal(100000), "teacherName", customerId,adminId, "register")

        val savedClass = academyClass.copy(classId = 1L)
        `when`(jpaAcademyClassRepository.save(academyClass)).thenReturn(Optional.of(savedClass))

        // when
        val result = academyClassService.save(academyClass)

        // then
        assertTrue(result.isPresent)
        assertEquals(1L, result.get().classId)
        verify(jpaAcademyClassRepository, times(1)).save(academyClass)
    }

}