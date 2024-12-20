package reservation.project.presentation.customvalidation.ant

import jakarta.validation.Constraint
import jakarta.validation.Payload
import reservation.project.presentation.customvalidation.validator.RrnValidator
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [RrnValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidRrn(
    val message: String = "유효하지 않은 주민등록번호입니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
