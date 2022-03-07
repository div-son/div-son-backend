package socialnetwork.backend.service.visitor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import socialnetwork.backend.dto.request.ResponseDto;
import socialnetwork.backend.dto.request.UpdateVisitorDto;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.exception.InvalidVisitorException;
import socialnetwork.backend.exception.VisitorAlreadyExistException;
import socialnetwork.backend.exception.VisitorDoesNotException;
import socialnetwork.backend.model.visitor.Gender;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.model.visitor.VisitorType;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;
import socialnetwork.backend.repository.visitorProfile.VisitorProfileRepository;
import socialnetwork.backend.repository.visitor.VisitorRepository;
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

    private VisitorProfile visitorProfile;
    @Autowired
    VisitorProfileServiceImpl visitorProfileService;

    @Autowired
    VisitorRepository visitorRepository;

    public Boolean visitorDoesNotExist(String id) {
        return !visitorRepository.existsById(id);
    }

    @Override
    public void registerVisitor(VisitorProfile visitorProfile) throws GeneralException {
        
        if(visitorProfile.getPassword().length() < 6){
            throw new VisitorDoesNotException("Password must have at least 6 characters.");
        }
        
        Visitor visitor = new Visitor();


        visitorProfile.setModifiedDate(LocalDateTime.now());
        visitorProfile.setIsVerified(false);
        visitorProfile.setUserType(VisitorType.USER);
        visitorProfile.setPassword(encryptPassword(visitorProfile.getPassword()));

        visitorProfileService.createVisitorProfile(visitorProfile);

        visitor.setUser(visitorProfile);
        visitor.setCreatedDate(LocalDateTime.now());
        visitor.setIsActive(true);
        visitor.setDateOfBirth("");
        visitor.setGender(null);

        visitorRepository.save(visitor);
    }

    @Override
    public void verifyEmailToken(String verificationToken, String url) throws GeneralException {
        Visitor visitor = visitorRepository.findByVerificationToken(verificationToken);

        if (visitor == null) {
            throw new VisitorDoesNotException("Visitor email token should not be null.");
        }

        else if (visitor.getUser().getIsVerified()) {
            throw new VisitorAlreadyExistException("Visitor has been verified.");
        }

        visitor.setVerificationToken(null);
        visitor.getUser().setIsVerified(true);
        visitorRepository.save(visitor);
    }

    @Override
    public void resetPassword(String oldPassword, String newPassword) throws GeneralException {
        VisitorProfile visitorProfile = visitorProfileRepository.findByEmail(oldPassword);
        Visitor visitor = visitorRepository.findByUser(visitorProfile);

        if (visitor == null) {
            throw new InvalidVisitorException("Visitor id can't be null.");
        } else {
            visitor.getUser().setPassword(encryptPassword(newPassword));
            visitorRepository.save(visitor);

            visitorProfile.setPassword(visitor.getUser().getPassword());
            visitorProfileRepository.save(visitorProfile);
        }
    }

    @Override
    public Visitor findVisitorByEmail(String email) {
        return visitorRepository.findByPhoneNumber(email);
    }

    @Override
    public Visitor findVisitorById(String id) throws GeneralException {
        if (id == null){
            throw new GeneralException("Visitor shouldn't be null.");
        }
        return visitorRepository.findVisitorById(id);
    }

    @Override
    public void deleteVisitorById(String id) throws GeneralException {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(()->
                new GeneralException("This visitor doesn't exist."));
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
            throw new GeneralException("You can not update a visitor that has been deactivated.");
        }
        if (visitorDto.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(visitorDto.getPhoneNumber());
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
        if (visitorDto.getEmail() != null) {
            existingUser.getUser().setEmail(visitorDto.getEmail());
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

    public ResponseDto checkIfVisitorExists(String email) throws GeneralException {
        if (!visitorProfileRepository.existsByEmail(email)) {
            throw new VisitorDoesNotException("Visitor with that email does not exist.");
        }

        else {
            VisitorProfile visitorProfile = visitorProfileRepository.findByEmail(email);
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
                    .email(visitorProfile.getEmail())
                    .firstName(visitor.getUser().getFirstName())
                    .lastName(visitor.getUser().getLastName())
                    .isActive(visitor.getIsActive())
                    .isVerified(visitor.getUser().getIsVerified())
                    .modifiedDate(visitor.getModifiedDate().toString())
                    .phoneNumber(visitor.getPhoneNumber())
                    .sex(visitor.getGender())
                    .token(token)
                    .build();
        }
    }

    public Visitor verifyVisitor(String email) throws GeneralException {
            if (!visitorProfileRepository.existsByEmail(email)) {
                throw new GeneralException("Visitor with that email does not exist.");
            }
            VisitorProfile profile = visitorProfileRepository.findByEmail(email);
            return visitorRepository.findByUser(profile);
        }
    }
