package reservation.project.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customerGroupOpenApi(): GroupedOpenApi {
        return GroupedOpenApi
            .builder()
            .group("Auth")
            .pathsToMatch("/auth/**")
            .addOpenApiCustomizer{ openApi ->
                openApi.info(
                    Info()
                        .title("Customer api")
                        .description("유저 관련 API")
                        .version("1.0.0")
                )
            }
            .build()
    }

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(apiInfo())
    }

    private fun apiInfo(): Info {
        return Info()
            .title("Practice")
            .description("descrption")
            .version("1.0.0")
    }
}