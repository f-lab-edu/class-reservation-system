package reservation.project.domain.customer.entity


import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import lombok.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val uid: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    val passwordInfo: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val roles: String = "USER"
): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return mutableListOf (SimpleGrantedAuthority(this.roles) )
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getUsername(): String = uid

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isAccountNonExpired(): Boolean = true

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isAccountNonLocked(): Boolean = true

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isCredentialsNonExpired(): Boolean = true

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isEnabled(): Boolean = true

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getPassword(): String = passwordInfo
}
