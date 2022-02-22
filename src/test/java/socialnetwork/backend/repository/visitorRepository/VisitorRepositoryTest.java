package socialnetwork.backend.repository.visitorRepository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import socialnetwork.backend.model.visitor.Gender;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;
import socialnetwork.backend.repository.visitorProfileRepository.VisitorProfileRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j

public class VisitorRepositoryTest {


    @Autowired
    private VisitorRepository visitorRepository;


    @Autowired
    private VisitorProfileRepository visitorProfileRepository;


    private Visitor visitor;


    @BeforeEach
    public void initSetUp(){
        visitor = new Visitor();
    }

    @Test
    @DisplayName("Create visitor")
    public void test_registerVisitor(){
        VisitorProfile visitorProfile = visitorProfileRepository.findById("62149abf15bfe760e8f75f43").get();

        visitor.setPhoneNumber("okoroaforkelechi123@gmail.com");
        visitor.setCreatedDate(LocalDateTime.now());
        visitor.setModifiedDate(LocalDateTime.now());
        visitor.setDateOfBirth("16/12/2002");
        visitor.setGender(Gender.MALE);
        visitor.setIsActive(false);
        visitor.setUser(visitorProfile);

        visitorRepository.save(visitor);
        Assertions.assertThat(visitor.getId()).isNotNull();
        log.info("Visitor after creation -> {}", visitor);
    }

    @Test
    public void test_deleteAVisitor(){
        visitor = visitorRepository.findById("6214a174c3e3a90291bf8bc0").orElse(null);

        assert visitor != null;
        visitorRepository.deleteById(visitor.getId());
        log.info("Successfully deleted visitor --> {}", visitor);
    }


    @Test
    @DisplayName("Find visitor")
    public void test_findVisitorById() {
        visitor = visitorRepository.findById("6214a174c3e3a90291bf8bc0").orElse(null);
        log.info("visitor details --> {}", visitor);
    }

    @Test
    @DisplayName("Find visitor")
    public void test_findVisitorByEmail() {
        visitor = visitorRepository.findByPhoneNumber("OnHover@gmail.com");
        Assertions.assertThat(visitor).isNotNull();
        log.info("existed visitor details --> {}", visitor);
    }

    @Test
    @DisplayName("Find  all visitor")
    public void test_findAllVisitors() {
        List<Visitor> visitorList = visitorRepository.findAll();
        log.info("List of visitor --> {}", visitorList);
    }

    @Test
    @DisplayName("Delete visitor account")
    public void test_deleteVisitorAccount () {
        visitor = visitorRepository.findById("6214a174c3e3a90291bf8bc0").orElse(null);
        Assertions.assertThat(visitorRepository.existsById("6214a174c3e3a90291bf8bc0")).isTrue();
        visitorRepository.deleteById("6214a174c3e3a90291bf8bc0");
        log.info("Visitor --> {}",visitor);
    }

    @Test
    @DisplayName("Update account")
    public void test_updateAVisitorAccountById(){
        visitor = visitorRepository.findById("6214a174c3e3a90291bf8bc0").orElse(null);
        Assertions.assertThat(visitor).isNotNull();
        visitor.setPhoneNumber("OnHover@gmail.com");
        visitorRepository.updateVisitor(visitor);
        org.junit.jupiter.api.Assertions.assertEquals("OnHover@gmail.com", visitor.getPhoneNumber());

    }
}