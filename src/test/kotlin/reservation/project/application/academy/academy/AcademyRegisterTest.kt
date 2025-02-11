package com.project.practice.application.academy


import org.apache.catalina.connector.Response
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import reservation.project.application.academy.AcademyUseCase
import reservation.project.domain.academy.repository.AcademyInstructorRepository
import reservation.project.domain.academy.repository.AcademyRepository
import reservation.project.domain.academy.service.AcademyInstructorService
import reservation.project.domain.academy.service.AcademyService
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.repository.CustomerRepository
import reservation.project.domain.customer.service.CustomerService
import reservation.project.presentation.academy.dto.academy.AcademyRegisterInfoDto
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.service.academy.repository.AcademyFakeJpaRepository
import reservation.project.service.academy.repository.AcademyInstructorFakeRepository
import reservation.project.service.customer.repository.CustomerFakeRepository

class AcademyRegisterTest {

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

    @DisplayName("학원 등록 주체는 Admin 이어야 한다.")
    @Test
    fun `The entity responsible for academy registration must be an Admin`() {

        //given
        val customerResult = customerService.saveCustomer(Customer(0, "uid", "1234", "name", "USER"))
        val request = AcademyRegisterInfoDto(customerResult.uid, "academy", 2L, "2025/01/30 15:30","2025/01/30 20:30", "서울", "url", "01011112222", "MASTER")

        //when
        val result = assertThrows<ErrorException> {
            academyUseCase.registerAcademyInfo(request)
        }

        //then
        assertEquals(Response.SC_BAD_REQUEST, result.statusCode)
        assertEquals("Not an Admin", result.errorMessage)
    }

    @DisplayName("Admin 이 학원 등록 신청을 하게되면 학원정보가 등록이 되어져 있어야 한다.")
    @Test
    fun `Success Save Academy Info`() {
        // given
        val customerResult = customerService.saveCustomer(Customer(0, "uid", "1234", "name", "ADMIN"))
        val request = AcademyRegisterInfoDto(customerResult.uid, "academy", 2L, "2025/01/30 15:30","2025/01/30 20:30", "서울", "url", "01011112222", "MASTER")

        //when
        academyUseCase.registerAcademyInfo(request)
        val result = academyService.findAcademyInfoByName(request.academyName)

        //then
        assertThat(result).hasSize(1)
        assertEquals(result[0].category, 2L)
    }

    @DisplayName("Admin 이 학원 등록 신청을 하게되면 학원정보와 강사정보 모두 존재해야한다.")
    @Test
    fun `Success Save Academy and Instructor`() {
        // given
        val customerResult = customerService.saveCustomer(Customer(0, "uid", "1234", "name", "ADMIN"))
        val request = AcademyRegisterInfoDto(customerResult.uid, "academy", 2L, "2025/01/30 15:30","2025/01/30 20:30", "서울", "url", "01011112222", "MASTER")

        //when
        academyUseCase.registerAcademyInfo(request)
        val academyResult = academyService.findAcademyInfoByName(request.academyName)
        val instructorInfo = academyInstructorService.findInfoByAcademyId(academyResult[0].id)

        //then
        assertThat(academyResult).hasSize(1)
        assertThat(instructorInfo).hasSize(1)
        assertEquals(academyResult[0].id, instructorInfo[0].academyId)
    }

    @DisplayName("학원 정상 등록 테스트")
    @Test
    fun `Success Save Test`() {
        // given
        val customerResult = customerService.saveCustomer(Customer(0, "uid", "1234", "name", "ADMIN"))
        val request = AcademyRegisterInfoDto(customerResult.uid, "academy", 2L, "2025/01/30 15:30","2025/01/30 20:30", "서울", "url", "01011112222", "MASTER")

        //when
        val result = academyUseCase.registerAcademyInfo(request)

        //then
        assertEquals(result.code, 200)
        assertEquals(result.message, true)
    }
}
