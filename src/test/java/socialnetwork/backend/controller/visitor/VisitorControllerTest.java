package socialnetwork.backend.controller.visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import socialnetwork.backend.dto.request.LoginDto;
import socialnetwork.backend.dto.request.RegisterVisitorDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest

public class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private LoginDto loginDto;

    private ObjectMapper objectMapper;

    private RegisterVisitorDto registerVisitorDto;

    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwODA4NzY0MzM2MiIsImV4cCI6MTY0NjQxNjMzN30.WQJFqN_bR1U640y0OX54hajaGL6bAO0XBc70FDNmYgquAr29l5eZFLKMPO0mKbQZsdkbd4Rc55L2fduQNETsJQ";

    @BeforeEach
    public void initSetUp(){
        objectMapper = new ObjectMapper();
        registerVisitorDto = new RegisterVisitorDto();
        loginDto = new LoginDto();
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

    @Test
    public void test_visitorCanLogin() throws Exception {
        loginDto.setPassword("meandmyself");
        loginDto.setPhoneNumber("08087643362");

        this.mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void test_findVisitorById() throws Exception {

        this.mockMvc.perform(get("/users/621519a468f0a2213ffec4a4")
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}