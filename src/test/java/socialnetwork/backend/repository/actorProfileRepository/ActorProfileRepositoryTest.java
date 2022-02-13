package socialnetwork.backend.repository.actorProfileRepository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import socialnetwork.backend.model.actorProfile.ActorProfile;
import socialnetwork.backend.model.actorProfile.ActorType;
import socialnetwork.backend.repository.actorRepository.ActorRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j

public class ActorProfileRepositoryTest {

    private ActorProfile actorProfile;

    @Autowired
    private ActorProfileRepository actorProfileRepository;

    @BeforeEach
    public void initSetup(){
        actorProfile = new ActorProfile();
    }


    @Test
    public void test_createActorProfile(){
        actorProfile.setActorType(ActorType.USER);
        actorProfile.setModifiedDate(LocalDate.now());
        actorProfile.setFirstname("Kelechi");
        actorProfile.setLastname("Okoroafor");
        actorProfile.setIsVerified(false);
        actorProfile.setPassword("password");
        actorProfile.setPhoneNumber("08082167764");

        assertDoesNotThrow(() ->{
            actorProfileRepository.save(actorProfile);
        });

        log.info("Actor profile saved successfully --> {}", actorProfile);
    }
}