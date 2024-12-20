package reservation.project.presentation.customvalidation.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import reservation.project.presentation.customvalidation.ant.ValidRrn

class RrnValidator : ConstraintValidator<ValidRrn, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null || value.isBlank()) {
            return false
        }

        // 주민등록번호 형식 확인 (13자리 숫자)
        val regex = Regex("^\\d{6}[1-4]\\d{6}$")
        if (!regex.matches(value)) {
            return false
        }

        // 주민등록번호 유효성 검증 (체크섬 알고리즘)
        val weights = listOf(2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5)
        val checksumDigits = value.substring(0, 12).map { it.toString().toIntOrNull() }

        if (checksumDigits.size != weights.size || checksumDigits.any { it == null }) {
            return false
        }

        // zip 적용
        val checksum = checksumDigits.zip(weights)
            .sumOf { (digit, weight) -> digit!! * weight }

        val validationDigit = (11 - (checksum % 11)) % 10

        return validationDigit == value.last().toString().toIntOrNull()
    }
}