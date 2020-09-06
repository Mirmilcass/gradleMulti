package edu.mirmil.web.controller

import com.google.gson.Gson
import edu.mirmil.domain.entity.User
import edu.mirmil.web.RestDocsConfig
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UserControllerTest {

    private val log = KotlinLogging.logger { }

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val endPoint = "/user"

    @Test
    @DisplayName("사용자 등록")
    fun created() {
        val data = User(
            id = "test",
            pass = "test",
            name = "test"
        )

        log.info { "\n----------------------------------\n" }
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("$endPoint/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(data))
        )
            .andExpect(status().isCreated)
            .andDo { println("result :: ${it.response.contentAsString}") }
            .andDo(
                document(
                    "$endPoint/create",
                    RestDocsConfig.getDocsRequest(),
                    RestDocsConfig.getDocsResponse(),
                    requestFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
                        fieldWithPath("pass").type(JsonFieldType.STRING).description("비밀번호"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                    ),
                    responseFields(
                        fieldWithPath("idx").description("인덱스"),
                        fieldWithPath("created").description("생성일"),
                        fieldWithPath("updated").description("수정일"),
                        fieldWithPath("id").description("아이디"),
                        fieldWithPath("name").description("이름")
                    )
                )
            )
        log.info { "----------------------------------\n" }
    }
}