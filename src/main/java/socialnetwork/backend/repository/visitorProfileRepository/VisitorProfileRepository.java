package socialnetwork.backend.repository.visitorProfileRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;

@Repository
public interface VisitorProfileRepository extends MongoRepository<VisitorProfile, String> {

    VisitorProfile findByEmail(String email);

    Boolean existsByEmail(String email);
}
