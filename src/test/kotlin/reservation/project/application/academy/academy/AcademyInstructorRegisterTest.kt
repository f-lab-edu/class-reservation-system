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
import com.project.practice.presentation.academy.dto.AcademyRegisterInfoDto
import com.project.practice.presentation.academy.dto.InstructorRegisterInfoDto
import com.project.practice.presentation.advice.exception.ErrorException
import com.project.practice.service.academy.repository.AcademyFakeJpaRepository
import com.project.practice.service.academy.repository.AcademyInstructorFakeRepository
import com.project.practice.service.customer.repository.CustomerFakeRepository
import org.apache.catalina.connector.Response
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class AcademyInstructorRegisterTest {

    val logger = LoggerFactory.getLogger(AcademyInstructorRegisterTest::class.java)

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

    @DisplayName("Master 등록 테스트: 학원에 등록이 되어 있으면 에러발생")
    @Test
    fun `Throw Error when Registration Requester belongs to an academy `() {

        //given
        val local = LocalDateTime.now()
        val customerResult = customerService.saveCustomer(Customers(0, "uid", "1234", "name", "ADMIN"))
        val saveAcademyInfo = academyService.saveAcademy(Academy(1L, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(0, customerResult.id ,saveAcademyInfo!!.id, InstructorRole.MASTER, local, local ))

        val request = InstructorRegisterInfoDto(customerResult.uid, null, saveAcademyInfo.id)

        //when
        val result = assertThrows<ErrorException> {
            academyUseCase.registerAcademyInstructorInfo(request)
        }

        //then
        assertEquals(Response.SC_BAD_REQUEST, result.statusCode)
        assertEquals("Instructor(master) already exists", result.errorMessage)
    }

    @DisplayName("Master 등록 테스트: 성공")
    @Test
    fun `SuccessTest when Registration Requester test execute`() {

        //given
        val local = LocalDateTime.now()
        val customerResult = customerService.saveCustomer(Customers(0, "uid", "1234", "name", "ADMIN"))
        val saveAcademyInfo = academyService.saveAcademy(Academy(1L, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))

        val request = InstructorRegisterInfoDto(customerResult.uid, null, saveAcademyInfo!!.id)

        //when
        val saveResult = academyUseCase.registerAcademyInstructorInfo(request)
        val result = academyInstructorService.findInfoById(customerResult.id)

        //then
        assertEquals(200, saveResult.code)
        assertEquals(true, saveResult.message)

        assertEquals(customerResult.id, result.customerId)
        assertEquals(saveAcademyInfo.id, result.academyId)
    }

    @DisplayName("Normal 등록 테스트: 요청자가 학원의 Master 가 아닌 경우")
    @Test
    fun `Throw Error When Registration Requester is not Master`() {

        //given
        val local = LocalDateTime.now()
        val masterResult = customerService.saveCustomer(Customers(0, "masterUid", "1234", "name", "ADMIN"))
        val normalResult = customerService.saveCustomer(Customers(0, "normalUid", "1234", "name", "ADMIN"))
        val saveAcademyInfo = academyService.saveAcademy(Academy(1L, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(0, masterResult.id ,saveAcademyInfo!!.id, InstructorRole.NORMAL, local, local ))

        val request = InstructorRegisterInfoDto(masterResult.uid, normalResult.uid, saveAcademyInfo.id)

        //when
        val result = assertThrows<ErrorException> {
            academyUseCase.registerAcademyInstructorInfo(request)
        }

        //then
        assertEquals(Response.SC_BAD_REQUEST, result.statusCode)
        assertEquals("Instructor is not Master", result.errorMessage)
    }

    @DisplayName("Normal 등록 테스트: 요청자에 의해 요청된 customerId가 Admin이 아닌경우")
    @Test
    fun `Throw Error When normalUid is not Admin`() {

        //given
        val local = LocalDateTime.now()
        val masterResult = customerService.saveCustomer(Customers(0, "masterUid", "1234", "name", "ADMIN"))
        val normalResult = customerService.saveCustomer(Customers(0, "normalUid", "1234", "name", "USER"))
        val saveAcademyInfo = academyService.saveAcademy(Academy(1L, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(0, masterResult.id ,saveAcademyInfo!!.id, InstructorRole.MASTER, local, local ))

        val request = InstructorRegisterInfoDto(masterResult.uid, normalResult.uid, saveAcademyInfo.id)

        //when
        val result = assertThrows<ErrorException> {
            academyUseCase.registerAcademyInstructorInfo(request)
        }

        //then
        assertEquals(Response.SC_BAD_REQUEST, result.statusCode)
        assertEquals("Register Request Instructor is not a Admin", result.errorMessage)
    }

    @DisplayName("Normal 등록 테스트: 요청자에 의해 요청된 customerId가 요청된 학원소속일경우")
    @Test
    fun `Throw Error When normalUid belongs to requested Academy`() {

        //given
        val local = LocalDateTime.now()
        val masterResult = customerService.saveCustomer(Customers(0, "masterUid", "1234", "name", "ADMIN"))
        val normalResult = customerService.saveCustomer(Customers(0, "normalUid", "1234", "name", "ADMIN"))
        val saveAcademyInfo = academyService.saveAcademy(Academy(1L, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(0, masterResult.id ,saveAcademyInfo!!.id, InstructorRole.MASTER, local, local ))
        academyInstructorService.saveInfo(AcademyInstructor(0, normalResult.id ,saveAcademyInfo.id, InstructorRole.NORMAL, local, local ))

        val request = InstructorRegisterInfoDto(masterResult.uid, normalResult.uid, saveAcademyInfo.id)

        //when
        val result = assertThrows<ErrorException> {
            academyUseCase.registerAcademyInstructorInfo(request)
        }

        //then
        assertEquals(Response.SC_BAD_REQUEST, result.statusCode)
        assertEquals("Register Request Instructor exists in Academy", result.errorMessage)
    }

    @DisplayName("Normal 등록 테스트: Success")
    @Test
    fun `Success Test When registering a Normal User `() {

        //given
        val local = LocalDateTime.now()
        val masterResult = customerService.saveCustomer(Customers(0, "masterUid", "1234", "name", "ADMIN"))
        val normalResult = customerService.saveCustomer(Customers(0, "normalUid", "1234", "name", "ADMIN"))
        val saveAcademyInfo = academyService.saveAcademy(Academy(1L, "name", 1L, local, local.plusHours(5), "location", "url", "0101111111", local, local))
        academyInstructorService.saveInfo(AcademyInstructor(0, masterResult.id ,saveAcademyInfo!!.id, InstructorRole.MASTER, local, local ))

        val request = InstructorRegisterInfoDto(masterResult.uid, normalResult.uid, saveAcademyInfo.id)

        //when
        val result = academyUseCase.registerAcademyInstructorInfo(request)
        val instructorInfo = academyInstructorService.findInfoByAcademyIdAndCustomerId(normalResult.id, saveAcademyInfo.id)

        //then
        assertEquals(200, result.code)
        assertEquals(true, result.message)

        assertEquals(normalResult.id, instructorInfo!!.id)
        assertEquals(InstructorRole.NORMAL, instructorInfo.role)
    }

}