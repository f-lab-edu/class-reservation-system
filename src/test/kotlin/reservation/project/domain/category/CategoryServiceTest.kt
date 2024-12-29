package reservation.project.domain.category

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import reservation.project.domain.academy.entity.Category
import reservation.project.domain.academy.service.CategoryService
import reservation.project.infra.academy.JpaCategoryRepository
import java.util.*

@ExtendWith(MockitoExtension::class)
class CategoryServiceTest {

    @Mock
    lateinit var jpaCategoryRepository: JpaCategoryRepository

    @InjectMocks
    lateinit var categoryService: CategoryService

    @Test
    fun `findByCategoryId should return Category when found`() {
        //given
        val id = 1L
        val category = Category(id, "etc")
        `when`(jpaCategoryRepository.findByCategoryId(id)).thenReturn(Optional.of(category))

        //when
        val result = categoryService.findByCategoryId(id)

        //then
        assertTrue(result.isPresent)
        assertEquals(category, result.get())
        verify(jpaCategoryRepository, times(1)).findByCategoryId(id)
    }

    @Test
    fun `findByCategoryId should return empty when not found`() {
        //given
        val id = 1L
        `when`(jpaCategoryRepository.findByCategoryId(id)).thenReturn(Optional.empty())

        //when
        val result = categoryService.findByCategoryId(id)

        //then
        assertFalse(result.isPresent)
        verify(jpaCategoryRepository, times(1)).findByCategoryId(id)
    }
}