package socialnetwork.backend.service.visitorProfile;

import socialnetwork.backend.dto.request.UpdateVisitorProfileDto;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;

import java.util.List;

public interface VisitorProfileService {

    VisitorProfile findVisitorProfileById(String id) throws GeneralException;

    List<VisitorProfile> findAllVisitorsProfiles();

    void deleteVisitorsProfileById(String id) throws GeneralException;

    void updateVisitorsProfile(UpdateVisitorProfileDto updateProfileDto) throws GeneralException;

    void createVisitorProfile(VisitorProfile visitorProfile) throws GeneralException;
}
