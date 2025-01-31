package reservation.project.application.customer

import org.apache.catalina.connector.Response
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reservation.project.config.security.JwtTokenProvider
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.service.CustomerService
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

@Component
class AuthUseCase(
    private val customerService: CustomerService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {

    fun signUp(req: RegisterReqDto): ResponseDto<Boolean> {

        val info = customerService.findCustomerInfo(req.id)
        if (info != null) {
            throw ErrorException(Response.SC_CONFLICT, "The Information exists")
        }

        val customerInfo = Customer(
            uid = req.id,
            name = req.username,
            passwordInfo = passwordEncoder.encode(req.password),
            roles = req.role
        )

        customerService.saveCustomer(customerInfo)

        return ResponseDto(200,true)
    }

    fun signIn(req: LoginReqDto): ResponseDataDto<String> {
        val info = customerService.findCustomerInfo(req.userId) ?: throw ErrorException(Response.SC_NOT_FOUND, "The Information does not exist")

        if (!passwordEncoder.matches(req.password, info.password)) {
            throw ErrorException(Response.SC_CONFLICT, "The Passwords don't match")
        }

        return ResponseDataDto(200, "Success", jwtTokenProvider.createToken(info.uid, listOf(info.roles)))
    }

}