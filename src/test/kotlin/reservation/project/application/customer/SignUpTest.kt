package reservation.project.application.customer

import org.apache.catalina.connector.Response
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import reservation.project.config.security.JwtTokenProvider
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.service.CustomerService
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

@ExtendWith(MockitoExtension::class)
class SignUpTest {

    @Mock
    private lateinit var customerService: CustomerService
    @Mock
    private lateinit var jwtTokenProvider: JwtTokenProvider
    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var authUseCase: AuthUseCase

    @Test
    fun `Success SignUp Test`() {
        //given
        val req = RegisterReqDto("uid", "name", "password111", "USER")
        whenever(passwordEncoder.encode(anyString())).thenReturn(req.password)
        val customer = Customer(1L, req.id, req.password, req.username, req.role)
        whenever(customerService.findCustomerInfo(req.id)).thenReturn(customer)

        //when
        val result = authUseCase.signUp(req)

        //then
        assertEquals(result.code, 200)
        assertTrue(result.message)
    }

    @Test
    fun `Success Sign In Test`() {
        //given
        val req = LoginReqDto("uid", "name")
        val customer = Customer(1L, "uid", "name", "username", "USER")
        whenever(customerService.findCustomerInfo(req.userId)).thenReturn(customer)
        whenever(passwordEncoder.matches(req.password, customer.password)).thenReturn(true)

        //when
        val result = authUseCase.signIn(req)

        //then
        assertEquals(result.code, 200)
    }

    @Test
    fun `Fail Sign In Test with wrong password`() {
        //given
        val req = LoginReqDto("uid", "name")
        val customer = Customer(1L, "uid", "name", "username", "USER")
        whenever(customerService.findCustomerInfo(req.userId)).thenReturn(customer)
        whenever(passwordEncoder.matches(req.password, customer.password)).thenReturn(false)

        //when
        val result = org.junit.jupiter.api.assertThrows<ErrorException> {
            authUseCase.signIn(req)
        }

        //then
        assertEquals(Response.SC_CONFLICT, result.statusCode)
    }

}