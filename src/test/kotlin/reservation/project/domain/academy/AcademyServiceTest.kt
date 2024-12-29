package reservation.project.domain.academy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.entity.Category
import reservation.project.domain.academy.service.AcademyService
import reservation.project.infra.academy.JpaAcademyRepository
import java.time.LocalDateTime
import java.util.Optional


@ExtendWith(MockitoExtension::class)
class AcademyServiceTest {

    @Mock
    private lateinit var jpaAcademyRepository: JpaAcademyRepository

    @InjectMocks
    private lateinit var academyService: AcademyService

    @Test
    fun `findByAcademyId should return academy when found`() {
        //given
        val id = 1L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(id, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        `when`(jpaAcademyRepository.findByAcademyId(id)).thenReturn(Optional.of(academy))

        //when
        val result = academyService.findByAcademyId(id)

        //then
        assertTrue(result.isPresent)
        assertEquals(academy, result.get())
        verify(jpaAcademyRepository, times(1)).findByAcademyId(id)
    }

    @Test
    fun `findByAcademyId should return empty when not found`() {
        //given
        val id = 2L
        `when`(jpaAcademyRepository.findByAcademyId(id)).thenReturn(Optional.empty())
        //when
        val result = academyService.findByAcademyId(id)
        //then
        assertFalse(result.isPresent)
        verify(jpaAcademyRepository, times(1)).findByAcademyId(id)
    }

    @Test
    fun `save should return saved academy`() {
        //given
        val id = 1L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(id, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        `when`(jpaAcademyRepository.save(academy)).thenReturn(Optional.of(academy))

        //when
        val result = academyService.save(academy)

        //then
        assertTrue(result.isPresent)
        assertEquals(academy, result.get())
        verify(jpaAcademyRepository, times(1)).save(academy)
    }
}