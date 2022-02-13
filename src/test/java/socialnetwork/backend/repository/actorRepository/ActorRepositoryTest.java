package socialnetwork.backend.repository.actorRepository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import socialnetwork.backend.model.actor.Actor;
import socialnetwork.backend.model.actor.Gender;
import socialnetwork.backend.model.actorProfile.ActorProfile;
import socialnetwork.backend.repository.actorProfileRepository.ActorProfileRepository;

import javax.swing.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j

public class ActorRepositoryTest {

    private Actor actor;

    ActorProfile actorProfile;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ActorProfileRepository actorProfileRepository;

    @BeforeEach
    public void initSetUp(){
        actor = new Actor();
        actorProfile = new ActorProfile();
    }

    @Test
    public void test_createAProfile(){
        actorProfile = actorProfileRepository.findById("6209374f5bd1094d76a2e04f").orElse(null);

        actor.setActorProfile(actorProfile);
        actor.setAddress("yaba, lagos");
        actor.setCreatedDate(LocalDate.now());
        actor.setEmail("okoroafor@gmail.com");
        actor.setDateOfBirth("16/12/2002");
        actor.setSex(Gender.MALE);

        assertDoesNotThrow(() ->{
            actorRepository.save(actor);
        });

        log.info("Created actor successfully --> {}", actor);
    }

}