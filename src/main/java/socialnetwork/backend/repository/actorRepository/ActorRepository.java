package socialnetwork.backend.repository.actorRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import socialnetwork.backend.model.actor.Actor;

@Repository
public interface ActorRepository extends MongoRepository<Actor, String> {

    Actor findActorByEmail(String email);
}
