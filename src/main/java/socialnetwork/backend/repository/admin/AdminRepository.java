package socialnetwork.backend.repository.admin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import socialnetwork.backend.model.admin.Admin;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;

import java.time.LocalDateTime;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

    default void updateAdmin(Admin varAdmin) {
        varAdmin.setCreatedDate(LocalDateTime.now());
        save(varAdmin);
    }

    boolean existsByEmail(String email);

    Admin findByUser(VisitorProfile user);
}
