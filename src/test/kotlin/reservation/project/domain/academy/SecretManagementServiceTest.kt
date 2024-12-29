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
import reservation.project.domain.academy.entity.SecretManagement
import reservation.project.domain.academy.service.SecretManagementService
import reservation.project.infra.academy.JpaSecretManagementRepository
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class SecretManagementServiceTest {
    @Mock
    private lateinit var jpaSecretManagementRepository: JpaSecretManagementRepository

    @InjectMocks
    private lateinit var secretManagementService: SecretManagementService

    @Test
    fun `findByKeyId should return SecretManagement when found`() {
        //given
        val id = 1L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(2L, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        val secretManagement = SecretManagement(id, academy, "secretKey")
        `when`(jpaSecretManagementRepository.findById(id)).thenReturn(Optional.of(secretManagement))

        //when
        val result = secretManagementService.findByKeyId(id)

        //then
        assertTrue(result.isPresent)
        assertEquals(secretManagement, result.get())
        verify(jpaSecretManagementRepository, times(1)).findById(id)
    }

    @Test
    fun `findByKeyId should return empty when not found`() {
        //given
        val id = 1L
        `when`(jpaSecretManagementRepository.findById(id)).thenReturn(Optional.empty())

        //when
        val result = secretManagementService.findByKeyId(id)

        //then
        assertFalse(result.isPresent)
        verify(jpaSecretManagementRepository, times(1)).findById(id)
    }

    @Test
    fun `save should return saved SecretManagement`() {
        //given
        val id = 1L
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(2L, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        val secretManagement = SecretManagement(id, academy, "secretKey")
        `when`(jpaSecretManagementRepository.save(secretManagement)).thenReturn(Optional.of(secretManagement))

        //when
        val result = secretManagementService.save(secretManagement)

        //then
        assertTrue(result.isPresent)
        assertEquals(secretManagement, result.get())
        verify(jpaSecretManagementRepository, times(1)).save(secretManagement)
    }
}