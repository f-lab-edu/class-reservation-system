package reservation.project.presentation.customvalidation.ant

import jakarta.validation.Constraint
import jakarta.validation.Payload
import reservation.project.presentation.customvalidation.PathVariableValidator
import kotlin.reflect.KClass

@Constraint(validatedBy = [PathVariableValidator::class])
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidPath(
    val message: String = "유효하지 않는 값 입니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
