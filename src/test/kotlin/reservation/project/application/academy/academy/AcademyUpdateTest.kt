package com.project.practice.application.academy

import org.apache.catalina.connector.Response
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import reservation.project.application.academy.AcademyUseCase
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.entity.InstructorRole
import reservation.project.domain.academy.repository.AcademyInstructorRepository
import reservation.project.domain.academy.repository.AcademyRepository
import reservation.project.domain.academy.service.AcademyInstructorService
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.repository.CustomerRepository
import reservation.project.domain.customer.service.CustomerService
import reservation.project.presentation.academy.dto.academy.AcademyUpdateInfoReqDto
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.service.academy.repository.AcademyFakeJpaRepository
import reservation.project.service.academy.repository.AcademyInstructorFakeRepository
import reservation.project.service.customer.repository.CustomerFakeRepository
import java.time.LocalDateTime

class AcademyUpdateTest {

    private lateinit var academyInstructorFakeRepository: AcademyInstructorRepository
    private lateinit var academyInstructorService: AcademyInstructorService

    private lateinit var academyFakeRepository: AcademyRepository
    private lateinit var academyService: AcademyService

    private lateinit var customerRepository: CustomerRepository
    private lateinit var customerService: CustomerService

    private lateinit var academyUseCase: AcademyUseCase

    @BeforeEach
    fun setUp() {
        academyInstructorFakeRepository = AcademyInstructorFakeRepository()
        academyInstructorService = AcademyInstructorService(academyInstructorFakeRepository)

        academyFakeRepository = AcademyFakeJpaRepository()
        academyService = AcademyService(academyFakeRepository)

        customerRepository = CustomerFakeRepository()
        customerService = CustomerService(customerRepository)

        academyUseCase = AcademyUseCase(academyService, customerService, academyInstructorService)
    }

    @DisplayName("수정 요청한 학원정보가 없을 경우")
    @Test
    fun `Fail Test When Not exists Academy`() {
        //given
        val local = LocalDateTime.now()
        val customerInfo = customerService.saveCustomer(Customer(0, "uid", "1234", "name", "ADMIN"))
        academyService.saveAcademy(Academy(0, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(id = 0, academyId = 1L, role = InstructorRole.NORMAL ,customerId = 1L))

        val request = AcademyUpdateInfoReqDto(customerInfo.uid, 9999L,"academy", 2L, "2025/01/30 15:30","2025/01/30 20:30", "서울", "url", "01011112222")

        //when
        val result = assertThrows<ErrorException> {
            academyUseCase.updateAcademyInfo(request)
        }

        //then
        assertEquals(result.statusCode, 404)
        assertEquals(result.errorMessage, "Academy Not Found")
    }

    @DisplayName("수정 요청한 유저가 학원 대표가 아닐경우")
    @Test
    fun `Fail Test When Not a Master`() {
        //given
        val local = LocalDateTime.now()
        val customerInfo = customerService.saveCustomer(Customer(0, "uid", "1234", "name", "ADMIN"))
        val academyInfo = academyService.saveAcademy(Academy(0, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(id = 0, academyId = 1L, role = InstructorRole.NORMAL ,customerId = 1L))

        val request = AcademyUpdateInfoReqDto(customerInfo.uid, academyInfo!!.id,"academy", 2L, "2025/01/30 15:30","2025/01/30 20:30", "서울", "url", "01011112222")

        //when
        val result = assertThrows<ErrorException> {
            academyUseCase.updateAcademyInfo(request)
        }

        //then
        assertEquals(result.statusCode, Response.SC_BAD_REQUEST)
        assertEquals(result.errorMessage, "Not a Master")
    }

    @DisplayName("업데이트 완료 테스트: 정보가2개일때")
    @Test
    fun `Success Update Test Info2`() {
        //given
        val local = LocalDateTime.now()
        val customerInfo = customerService.saveCustomer(Customer(0, "uid", "1234", "name", "ADMIN"))
        val academyInfo = academyService.saveAcademy(Academy(0, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(id = 0, academyId = 1L, role = InstructorRole.MASTER ,customerId = 1L))

        val request = AcademyUpdateInfoReqDto(customerInfo.uid, academyInfo!!.id,"updateAcademy", 2L, "2025/01/30 15:30","2025/01/30 20:30", "광명", "url", "01011112222")
        academyUseCase.updateAcademyInfo(request)

        //when
        val confirmAcademyInfo = academyService.findAcademyInfo(academyInfo.id)

        //then
        assertEquals(confirmAcademyInfo.academyName, request.academyName)
        assertEquals(confirmAcademyInfo.location, request.location)
    }

    @DisplayName("업데이트 완료 테스트: 정보가3개일때")
    @Test
    fun `Success Update Test Info3`() {
        //given
        val local = LocalDateTime.now()
        val customerInfo = customerService.saveCustomer(Customer(0, "uid", "1234", "name", "ADMIN"))
        val academyInfo = academyService.saveAcademy(Academy(0, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(id = 0, academyId = 1L, role = InstructorRole.MASTER ,customerId = 1L))

        val request = AcademyUpdateInfoReqDto(customerInfo.uid, academyInfo!!.id,"updateAcademy", 2L, "2025/01/30 15:30","2025/01/30 20:30", "광명", "url123", "01011112222")
        academyUseCase.updateAcademyInfo(request)

        //when
        val confirmAcademyInfo = academyService.findAcademyInfo(academyInfo.id)

        //then
        assertEquals(confirmAcademyInfo.academyName, request.academyName)
        assertEquals(confirmAcademyInfo.location, request.location)
        assertEquals(confirmAcademyInfo.socialNetworkAddress, request.socialNetworkAddress)
    }


}
