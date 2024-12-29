package reservation.project.domain.token.status

enum class TokenStatus {
    PENDING, // 대기열 추가
    COMPLETED, // 결제 완료
    CANCELLED // 수강 취소
}