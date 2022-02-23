package socialnetwork.backend.repository.visitor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitorRepository extends MongoRepository<Visitor, String> {

    Visitor findByPhoneNumber(String phoneNumber);

    Visitor findByUser(VisitorProfile user);

    Visitor findVisitorById(String id);

    Boolean existsByPhoneNumber(String phoneNumber);

    default  void updateVisitor(Visitor visitor){
        visitor.setModifiedDate(LocalDateTime.now());
        save(visitor);
    }

    List<Visitor> findAll();

    Visitor findByVerificationToken(String verificationToken);
}
