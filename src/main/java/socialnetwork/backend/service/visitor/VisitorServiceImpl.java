package socialnetwork.backend.service.visitor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import socialnetwork.backend.dto.request.ResponseDto;
import socialnetwork.backend.dto.request.UpdateVisitorDto;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.exception.VisitorAlreadyExistException;
import socialnetwork.backend.model.visitor.Gender;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.model.visitor.VisitorType;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;
import socialnetwork.backend.repository.visitorProfileRepository.VisitorProfileRepository;
import socialnetwork.backend.repository.visitorRepository.VisitorRepository;
import socialnetwork.backend.service.visitorProfile.VisitorProfileServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static socialnetwork.backend.security.SecurityConstants.EXPIRATION_TIME;
import static socialnetwork.backend.security.SecurityConstants.SECRET;

@Service
public class VisitorServiceImpl implements VisitorService{

    @Autowired
    VisitorProfileRepository visitorProfileRepository;

    @Autowired
    VisitorProfileServiceImpl visitorProfileService;

    @Autowired
    VisitorRepository visitorRepository;

    public Boolean visitorDoesNotExist(String id) {
        return !visitorRepository.existsById(id);
    }

    public Boolean visitorDoesntExist(String email) {
        return !visitorRepository.existsByEmail(email);
    }

    private final Logger logger = LoggerFactory.getLogger(Visitor.class);

    @Override
    public void registerVisitor(VisitorProfile visitorProfile) throws GeneralException {
        Visitor visitor = new Visitor();

        visitorProfile.setModifiedDate(LocalDateTime.now());
        visitorProfile.setIsVerified(false);
        visitorProfile.setUserType(VisitorType.USER);
        visitorProfile.setPassword(encryptPassword(visitorProfile.getPassword()));

        visitorProfileService.createVisitorProfile(visitorProfile);

        visitor.setUser(visitorProfile);
        visitor.setCreatedDate(LocalDateTime.now());
        visitor.setIsActive(true);
        visitor.setEmail("");
        visitor.setDateOfBirth("");
        visitor.setGender(null);

        visitorRepository.save(visitor);

    }

    @Override
    public void verifyEmailToken(String verificationToken, String url) throws GeneralException {
        Visitor visitor = visitorRepository.findByVerificationToken(verificationToken);

        if (visitor == null) {
            throw new GeneralException("User does not exist.");
        }

        else if (visitor.getUser().getIsVerified()) {
            throw new VisitorAlreadyExistException("User has been verified.");
        }

        visitor.setVerificationToken(null);
        visitor.getUser().setIsVerified(true);
        visitorRepository.save(visitor);
    }

    @Override
    public void resetPassword(String phoneNumber, String newPassword) throws GeneralException {
        VisitorProfile visitorProfile = visitorProfileRepository.findByPhoneNumber(phoneNumber);
        Visitor visitor = visitorRepository.findByUser(visitorProfile);

        if (visitor == null) {
            throw new GeneralException("User does not exist");
        } else {
            visitor.getUser().setPassword(encryptPassword(newPassword));
            visitorRepository.save(visitor);
            visitorProfile.setPassword(visitor.getUser().getPassword());
            visitorProfileRepository.save(visitorProfile);
        }
    }

    @Override
    public Visitor findVisitorByEmail(String email) {
        return visitorRepository.findByEmail(email);
    }

    @Override
    public Visitor findVisitorById(String id) throws GeneralException {
        if (id == null){
            throw new GeneralException("Visitor's id is null.");
        }
        return visitorRepository.findVisitorById(id);
    }

    @Override
    public void deleteVisitorById(String id) throws GeneralException {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(()-> new GeneralException("User with that id has already been deleted somewhere.."));
        if (!visitor.getIsActive()) {
            throw new GeneralException("This user has already been deactivated");
        }
        visitorRepository.deleteById(id);
    }

    @Override
    public List<Visitor> findAllVisitors() {
        return visitorRepository.findAll();
    }

    @Override
    public Visitor updateVisitor(UpdateVisitorDto visitorDto) throws GeneralException {
        Visitor existingUser = findVisitorById(visitorDto.getId());

        if (!existingUser.getIsActive()) {
            throw new GeneralException("You can not update a user that has been deactivated");
        }
        if (visitorDto.getEmail() != null) {
            existingUser.setEmail(visitorDto.getEmail());
        }
        if (visitorDto.getGender() != null) {
            existingUser.setGender(Gender.valueOf(String.valueOf(visitorDto.getGender())));
        }
        if (visitorDto.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(visitorDto.getDateOfBirth());
        }
        if (visitorDto.getFirstName() != null) {
            existingUser.getUser().setFirstName(visitorDto.getFirstName());
        }
        if (visitorDto.getLastName() != null) {
            existingUser.getUser().setLastName(visitorDto.getLastName());
        }
        if (visitorDto.getPhoneNumber() != null) {
            existingUser.getUser().setPhoneNumber(visitorDto.getPhoneNumber());
        }
        existingUser.setModifiedDate(LocalDateTime.now());

        visitorProfileRepository.save(existingUser.getUser());
        visitorRepository.save(existingUser);
        return existingUser;
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public ResponseDto checkIfUserExists(String phoneNumber) throws GeneralException {
        if (!visitorProfileRepository.existsByPhoneNumber(phoneNumber)) {
            throw new GeneralException("User does not exist");
        }

        else {
            VisitorProfile visitorProfile = visitorProfileRepository.findByPhoneNumber(phoneNumber);
            Visitor visitor = visitorRepository.findByUser(visitorProfile);
            visitor.getUser().setIsVerified(Boolean.TRUE);
            visitorRepository.save(visitor);

            visitorProfile.setIsVerified(Boolean.TRUE);
            visitorProfileRepository.save(visitorProfile);

            String token = JWT.create()
                    .withSubject(visitor.getUser().getFirstName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(SECRET.getBytes(StandardCharsets.UTF_8)));

            return ResponseDto.builder()
                    .token(visitor.getVerificationToken())
                    .createdDate(visitor.getCreatedDate().toString())
                    .id(visitor.getId())
                    .email(visitor.getEmail())
                    .firstName(visitor.getUser().getFirstName())
                    .lastName(visitor.getUser().getLastName())
                    .isActive(visitor.getIsActive())
                    .isVerified(visitor.getUser().getIsVerified())
                    .modifiedDate(visitor.getModifiedDate().toString())
                    .phoneNumber(visitor.getUser().getPhoneNumber())
                    .sex(visitor.getGender())
                    .token(token)
                    .build();
        }
    }

    public Visitor verifyUser(String phoneNumber) throws GeneralException {
            if (!visitorProfileRepository.existsByPhoneNumber(phoneNumber)) {
                throw new GeneralException("User with that phone number does not exist");
            }
            VisitorProfile profile = visitorProfileRepository.findByPhoneNumber(phoneNumber);
            return visitorRepository.findByUser(profile);
        }
    }

