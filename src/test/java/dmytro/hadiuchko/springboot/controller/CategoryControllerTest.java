package dmytro.hadiuchko.springboot.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.hadiuchko.springboot.dto.category.request.CategoryRequestDto;
import dmytro.hadiuchko.springboot.dto.category.responce.CategoryResponseDto;
import dmytro.hadiuchko.springboot.service.CategoryService;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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

    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private CategoryService categoryService;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createCategory_validAdminRole_success() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto(CATEGORY_NAME, CATEGORY_DESCRIPTION);

        CategoryResponseDto expected = new CategoryResponseDto();
        expected.setId(CATEGORY_ID);
        expected.setName(requestDto.name());
        expected.setDescription(requestDto.description());

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
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    void getAllCategories_withUserRole_ReturnAll() throws Exception {
        List<CategoryResponseDto> categoryList = List.of(new CategoryResponseDto());
        when(categoryService.findAll(any(Pageable.class))).thenReturn(categoryList);

        mockMvc.perform(get(CATEGORY_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteCategory_WithAdminRole_Success() throws Exception {
        doNothing().when(categoryService).deleteById(CATEGORY_ID);
        mockMvc.perform(delete(CATEGORY_BY_ID_URL))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteCategory_WIthInValidRoleUser_Fail() throws Exception {
        mockMvc.perform(delete(CATEGORY_BY_ID_URL))
                .andExpect(status().isForbidden());
    }
}
