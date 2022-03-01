package socialnetwork.backend.service.visitorProfile;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import socialnetwork.backend.dto.request.ChangePasswordDto;
import socialnetwork.backend.dto.request.UpdateVisitorProfileDto;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;
import socialnetwork.backend.repository.visitorProfile.VisitorProfileRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorProfileServiceImpl implements VisitorProfileService{

    @Autowired
    VisitorProfileRepository visitorProfileRepository;

    public Boolean visitorProfileExistByEmail(String email){
        return visitorProfileRepository.existsByEmail(email);
    }

    public Boolean visitorProfileDoesntExistByEmail(String email){
        return !visitorProfileRepository.existsByEmail(email);
    }

    @Override
    public VisitorProfile findVisitorProfileById(String id) throws GeneralException {
        if (visitorProfileRepository.existsById(id)) {
            return visitorProfileRepository.findById(id).get();
        }
        else {
            throw new GeneralException("Visitor with id " + id + " does not exist.");
        }
    }

    @Override
    public List<VisitorProfile> findAllVisitorsProfiles() {
        return visitorProfileRepository.findAll();
    }

    @Override
    public void deleteVisitorsProfileById(String id) throws GeneralException {
        if (visitorProfileRepository.existsById(id)) {
            visitorProfileRepository.deleteById(id);
        }else {
            throw new GeneralException("Visitor with id " + id + " does not exist.");
        }
    }

    @Override
    public void updateVisitorsProfile(UpdateVisitorProfileDto updateProfileDto) throws GeneralException {
        VisitorProfile existingUser = visitorProfileRepository.findById(updateProfileDto.getId()).get();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapper.map(updateProfileDto, existingUser);

        existingUser.setModifiedDate(LocalDateTime.now());
        visitorProfileRepository.save(existingUser);
    }

    @Override
    public void createVisitorProfile(VisitorProfile visitorProfile) throws GeneralException {

        if (visitorProfileRepository.existsByEmail(visitorProfile.getEmail())){
            throw new GeneralException("Visitor with that email exist already.");
        }
        visitorProfileRepository.save(visitorProfile);
    }
    public void changePassword(ChangePasswordDto changePasswordDto) throws GeneralException {
        VisitorProfile profile = findVisitorProfileById(changePasswordDto.getVisitorsId());
        if (profile == null){
            throw new GeneralException("Visitor profile does not exist.");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), profile.getPassword())){
            throw new GeneralException("The old password is incorrect.");
        }
        profile.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
        visitorProfileRepository.save(profile);
    }
}
