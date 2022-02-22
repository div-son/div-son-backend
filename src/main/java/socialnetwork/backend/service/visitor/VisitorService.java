package socialnetwork.backend.service.visitor;

import socialnetwork.backend.dto.request.UpdateVisitorDto;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;

import java.util.List;

public interface VisitorService {

    void registerVisitor(VisitorProfile visitorProfile) throws GeneralException;

    void verifyEmailToken(String verificationToken, String url) throws GeneralException;

//    void sendResetPasswordToken(String email, String url) throws GeneralException;

    void resetPassword(String resetPasswordToken, String newPassword) throws GeneralException;

    Visitor findVisitorByEmail(String email);

    Visitor findVisitorById(String id) throws GeneralException;

    void deleteVisitorById(String id) throws GeneralException;

    List<Visitor> findAllVisitors();

    Visitor updateVisitor(UpdateVisitorDto visitorDto) throws GeneralException;
}
