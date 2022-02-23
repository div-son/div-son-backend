package socialnetwork.backend.repository.visitorProfile;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import socialnetwork.backend.model.visitor.VisitorType;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@SpringBootTest
@Slf4j

public class VisitorProfileRepositoryTest {

    @Autowired
    private VisitorProfileRepository visitorProfileRepository;

    private VisitorProfile visitorProfile;

    @BeforeEach
    public void initSetUp(){
        visitorProfile = new VisitorProfile();
    }


    @Test
    @DisplayName("Create profile")
    public void test_createVisitorProfile(){
        visitorProfile.setFirstName("Spencer");
        visitorProfile.setLastName("Bull");
        visitorProfile.setIsVerified(true);
        visitorProfile.setPassword("love");
        visitorProfile.setEmail("08082167764");
        visitorProfile.setUserType(VisitorType.USER);
        visitorProfile.setModifiedDate(LocalDateTime.now());

        assertDoesNotThrow(() ->{
            visitorProfileRepository.save(visitorProfile);
        });
    }

    @Test
    @DisplayName("Find visitor profile")
    public void test_findVisitorProfileById() {
        VisitorProfile savedVisitorProfile = visitorProfileRepository.findById("62149abf15bfe760e8f75f43").orElse(null);
        log.info("Visitor profile -> {}", savedVisitorProfile);
        Assertions.assertThat(savedVisitorProfile).isNotNull();
    }

    @Test
    @DisplayName("Read all visitor profile")
    public void test_readAllVisitorProfiles(){
        List<VisitorProfile> visitorProfiles = visitorProfileRepository.findAll();
        Assertions.assertThat(visitorProfiles).isNotNull();
        log.info("List of all visitors profiles --> {}", visitorProfiles);
    }

    @Test
    @DisplayName("Update visitor profile")
    public void test_updateProfile(){
        visitorProfile = visitorProfileRepository.findById("62149abf15bfe760e8f75f43").orElse(null);
        Assertions.assertThat(visitorProfile).isNotNull();
        visitorProfile.setPassword("OnHover");
        visitorProfileRepository.save(visitorProfile);
        org.junit.jupiter.api.Assertions.assertEquals("OnHover", visitorProfile.getPassword());
    }
}