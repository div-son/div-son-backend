package socialnetwork.backend.repository.actorProfileRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import socialnetwork.backend.model.actorProfile.ActorProfile;

import java.time.LocalDate;

public interface ActorProfileRepository extends MongoRepository<ActorProfile, String > {
    default void updateActorProfile(ActorProfile actorProfile){
        actorProfile.setModifiedDate(LocalDate.now());
    }
}
