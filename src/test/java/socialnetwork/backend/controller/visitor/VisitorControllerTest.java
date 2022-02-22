package socialnetwork.backend.controller.visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import socialnetwork.backend.dto.request.RegisterVisitorDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest

public class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private RegisterVisitorDto registerVisitorDto;


    @BeforeEach
    public void initSetUp(){
        objectMapper = new ObjectMapper();
        registerVisitorDto = new RegisterVisitorDto();
    }

    @Test
    public void test_createVisitor() throws Exception {
        registerVisitorDto.setPassword("KelechiDivine");
        registerVisitorDto.setFirstName("zip");
        registerVisitorDto.setLastName("demon");
        registerVisitorDto.setPhoneNumber("08082167763");

        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerVisitorDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

}