package reservation.project.domain.user.service.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import reservation.project.application.security.util.JwtUtils
import reservation.project.domain.user.entity.Customer
import reservation.project.domain.user.entity.Role
import reservation.project.domain.user.repository.UserRepository
import reservation.project.domain.user.service.UserServiceImpl
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.user.dto.RegisterReqDto
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {

    @InjectMocks
    lateinit var userServiceImpl: UserServiceImpl

    @Mock
    lateinit var userRepository: UserRepository
    @Mock
    lateinit var jwtUtils: JwtUtils
    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `test findByUsername wit existing user`() {
        val username = "testUser"
        val expectedRes = ResponseDataDto(200, "Success", true)

        whenever(userRepository.findByUsername(username)).thenReturn(Optional.of(Customer()))

        val res = userServiceImpl.findByUsername(username)

        assertEquals(expectedRes, res)
    }

    @Test
    fun `test findByUsername with non-existing user`() {
        val username = "nonExistentUser"
        val expectedResponse = ResponseDataDto(200, "Success", false)

        whenever(userRepository.findByUsername(username)).thenReturn(Optional.empty())

        val response = userServiceImpl.findByUsername(username)

        // 반환 값 검증
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `test save when user already exists`() {
        val registerReqDto = RegisterReqDto(username = "testUser", password = "password123")

        whenever(userRepository.findByUsername(registerReqDto.username)).
        thenReturn(Optional.of(Customer(username = registerReqDto.username, password = "encodedPassword", role = Role.USER)))

        assertThrows(UserExistException::class.java) {
            userServiceImpl.save(registerReqDto)
        }
    }

    @Test
    fun `test save when user is successfully saved`() {
        val registerReqDto = RegisterReqDto(username = "newUser", password = "password123")

        whenever(userRepository.findByUsername(registerReqDto.username)).thenReturn(Optional.empty())

        whenever(passwordEncoder.encode(registerReqDto.password)).thenReturn("encodedPassword")

        val savedCustomer = Customer(
            username = registerReqDto.username,
            password = "encodedPassword",
            role = Role.USER
        )
        whenever(userRepository.save(any<Customer>())).thenReturn(Optional.of(savedCustomer))

        val response = userServiceImpl.save(registerReqDto)

        assertEquals(200, response.code)
        assertEquals("Success", response.message)
        assertTrue(response.data as Boolean)
    }

    @Test
    fun `test save when user save fails`() {
        val registerReqDto = RegisterReqDto(username = "failUser", password = "password123")

        whenever(userRepository.findByUsername(registerReqDto.username)).thenReturn(Optional.empty())

        whenever(passwordEncoder.encode(registerReqDto.password)).thenReturn("encodedPassword")

        whenever(userRepository.save(any<Customer>())).thenReturn(Optional.empty())

        assertThrows(Exception::class.java) {
            userServiceImpl.save(registerReqDto)
        }
    }
}

