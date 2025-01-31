package com.project.practice.application.academy

import com.project.practice.domain.academy.entity.Academy
import com.project.practice.domain.academy.entity.AcademyInstructor
import com.project.practice.domain.academy.entity.InstructorRole
import com.project.practice.domain.academy.repository.AcademyInstructorRepository
import com.project.practice.domain.academy.repository.AcademyRepository
import com.project.practice.domain.academy.service.AcademyInstructorService
import com.project.practice.domain.academy.service.AcademyService
import com.project.practice.domain.customer.entity.Customers
import com.project.practice.domain.customer.repository.CustomerRepository
import com.project.practice.domain.customer.service.CustomerService
import com.project.practice.presentation.academy.dto.AcademyUpdateInfoReqDto
import com.project.practice.presentation.advice.exception.ErrorException
import com.project.practice.service.academy.repository.AcademyFakeJpaRepository
import com.project.practice.service.academy.repository.AcademyInstructorFakeRepository
import com.project.practice.service.customer.repository.CustomerFakeRepository
import org.apache.catalina.connector.Response
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        val customerInfo = customerService.saveCustomer(Customers(0, "uid", "1234", "name", "ADMIN"))
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
        val customerInfo = customerService.saveCustomer(Customers(0, "uid", "1234", "name", "ADMIN"))
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
        val customerInfo = customerService.saveCustomer(Customers(0, "uid", "1234", "name", "ADMIN"))
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
        val customerInfo = customerService.saveCustomer(Customers(0, "uid", "1234", "name", "ADMIN"))
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