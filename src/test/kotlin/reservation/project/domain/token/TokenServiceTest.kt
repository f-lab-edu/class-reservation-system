package reservation.project.domain.token

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import org.springframework.test.context.junit.jupiter.SpringExtension
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.entity.Category
import reservation.project.domain.token.entity.Token
import reservation.project.domain.token.service.TokenService
import reservation.project.domain.user.entity.Customer
import reservation.project.domain.user.entity.Role
import reservation.project.infra.token.JpaTokenRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@ExtendWith(SpringExtension::class)
class TokenServiceTest {

    @Mock
    private lateinit var jpaTokenRepository: JpaTokenRepository

    @InjectMocks
    private lateinit var tokenService: TokenService

    @Test
    fun `findByAcademyClassIdAndCustomerId should return Token when found`(){
        //given
        val tokenId = 1L
        val academyClass = getAcademyClass()
        val academy = academyClass.academy
        val customer = getCustomer()
        val now = LocalDateTime.now()
        val token = Token(tokenId, "access_token", now.plusHours(2), customer, academyClass, "Rervation", now )
        `when`(jpaTokenRepository.findByAcademyClassIdAndCustomerId(academyClass.classId, customer.id)).thenReturn(Optional.of(token))

        //when
        val result = tokenService.findByAcademyClassIdAndCustomerId(academyClass.classId, customer.id)

        //then
        assertTrue(result.isPresent)
        assertEquals(token, result.get())
        verify(jpaTokenRepository, times(1)).findByAcademyClassIdAndCustomerId(academyClass.classId, customer.id)
    }

    @Test
    fun `findByAcademyClassId should return Token when found`() {
        //given
        val tokenId = 1L
        val academyClass = getAcademyClass()
        val customer = getCustomer()
        val now = LocalDateTime.now()
        val token = Token(tokenId, "access_token", now.plusHours(2), customer, academyClass, "Rervation", now )
        `when`(jpaTokenRepository.findByAcademyClassId(academyClass.classId)).thenReturn(Optional.of(token))

        //when
        val result = tokenService.findByAcademyClassId(academyClass.classId)

        //then
        assertTrue(result.isPresent)
        assertEquals(token, result.get())
        verify(jpaTokenRepository, times(1)).findByAcademyClassId(academyClass.classId)
    }

    @Test
    fun `findByCustomerId should return Token when found`() {
        //given
        val tokenId = 1L
        val academyClass = getAcademyClass()
        val customer = getCustomer()
        val now = LocalDateTime.now()
        val token = Token(tokenId, "access_token", now.plusHours(2), customer, academyClass, "Rervation", now )
        `when`(jpaTokenRepository.findByCustomerId(customer.id)).thenReturn(Optional.of(token))

        //when
        val result = tokenService.findByCustomerId(customer.id)

        //then
        assertTrue(result.isPresent)
        assertEquals(token, result.get())
        verify(jpaTokenRepository, times(1)).findByCustomerId(customer.id)
    }

    @Test
    fun `findByAcademyClassId should return Empty when not found`(){
        //given
        val academyClassID = 2L
        `when`(jpaTokenRepository.findByAcademyClassId(academyClassID)).thenReturn(Optional.empty())

        //when
        val result = tokenService.findByAcademyClassId(academyClassID)

        //then
        assertFalse(result.isPresent)
        verify(jpaTokenRepository, times(1)).findByAcademyClassId(academyClassID)
    }

    @Test
    fun `findByCustomerId should return Empty when not found`(){
        //given
        val customerId = 2L
        `when`(jpaTokenRepository.findByCustomerId(customerId)).thenReturn(Optional.empty())

        //when
        val result = tokenService.findByCustomerId(customerId)

        //then
        assertFalse(result.isPresent)
        verify(jpaTokenRepository, times(1)).findByCustomerId(customerId)
    }

    @Test
    fun `findByAcademyClassIdAndCustomerId should return Empty when not found`(){
        //given
        val customerId = 1L
        val academyClassID = 2L
        `when`(jpaTokenRepository.findByAcademyClassIdAndCustomerId(academyClassID, customerId)).thenReturn(Optional.empty())

        //when
        val result = tokenService.findByAcademyClassIdAndCustomerId(academyClassID, customerId)

        //then
        assertFalse(result.isPresent)
        verify(jpaTokenRepository, times(1)).findByAcademyClassIdAndCustomerId(academyClassID, customerId)
    }

    @Test
    fun `saveAndModify should return savedAndModified Token`() {
        //given
        val tokenId = 1L
        val academyClass = getAcademyClass()
        val customer = getCustomer()
        val now = LocalDateTime.now()
        val token = Token(tokenId, "access_token", now.plusHours(2), customer, academyClass, "Rervation", now )
        `when`(jpaTokenRepository.save(token)).thenReturn(Optional.of(token))

        //when
        val result = tokenService.addQueue(token)

        //then
        assertTrue(result.isPresent)
        assertEquals(token, result.get())
        verify(jpaTokenRepository, org.mockito.kotlin.times(1)).save(token)
    }

    private fun getAcademyClass(): AcademyClass {
        val day = LocalDateTime.now()
        val time = LocalTime.now()
        return AcademyClass(
            1L, getAcademy(2L), "className", 10,
            day, day.plusDays(5), time.plusHours(1), time.plusHours(6), BigDecimal(100000),
            "강사", getCustomer(), 1L, "end")

    }

    private fun getAcademy(id:Long): Academy {
        val category = Category(1L, "etc")
        val time = LocalDateTime.now()
        val academy = Academy(id, "tennis", category,time, time.plusHours(8),
            "location", "url_address", 0.0, "contactInfo")
        return academy
    }

    private fun getCustomer(): Customer{
        return Customer(1L, BigDecimal.ZERO, "customer", "rrn",
            "password", Role.USER)
    }
}