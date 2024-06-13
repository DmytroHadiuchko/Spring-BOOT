package dmytro.hadiuchko.springboot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.hadiuchko.springboot.dto.category.request.CategoryRequestDto;
import dmytro.hadiuchko.springboot.dto.category.responce.CategoryResponseDto;
import dmytro.hadiuchko.springboot.entity.Category;
import dmytro.hadiuchko.springboot.repository.CategoryRepository;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    private static final String CATEGORY_DESCRIPTION = "interesting book";
    private static final String CATEGORY_NAME = "Fiction";
    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_URL = "/api/categories";
    private static final String CATEGORY_BY_ID_URL = "/api/categories/1";
    private static final String A_CATEGORY_FOR_FICTIONAL_BOOKS = "A category for fictional books.";

    private static MockMvc mockMvc;
    private CategoryRequestDto requestDto;
    private CategoryResponseDto expected;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        requestDto = new CategoryRequestDto(CATEGORY_NAME, CATEGORY_DESCRIPTION);

        expected = new CategoryResponseDto();
        expected.setId(CATEGORY_ID);
        expected.setName(requestDto.name());
        expected.setDescription(requestDto.description());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Create new category")
    void createCategory_validAdminRole_success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(expected);

        MvcResult result = mockMvc.perform(post(CATEGORY_URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @Sql(scripts = "classpath:sql/scripts/insert-into-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/scripts/drop-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @DisplayName("Return all categories")
    void getAllCategories_withUserRole_ReturnAll() throws Exception {
        String result = mockMvc.perform(get(CATEGORY_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Category[] categories = objectMapper.readValue(result, Category[].class);

        assertEquals(5, categories.length);
        assertEquals(CATEGORY_NAME, categories[0].getName());
        assertEquals(A_CATEGORY_FOR_FICTIONAL_BOOKS, categories[0].getDescription());
    }

    @Test
    @Sql(scripts = "classpath:sql/scripts/insert-into-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/scripts/drop-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Delete category by id with valid role")
    void deleteCategory_WithAdminRole_Success() throws Exception {
        mockMvc.perform(delete(CATEGORY_BY_ID_URL, CATEGORY_ID))
                .andExpect(status().isNoContent());
        Optional<Category> category = categoryRepository.findById(CATEGORY_ID);
        assertTrue(category.isEmpty());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Delete category by id with invalid role")
    void deleteCategory_WIthInValidRoleUser_Fail() throws Exception {
        mockMvc.perform(delete(CATEGORY_BY_ID_URL))
                .andExpect(status().isForbidden());
    }
}
