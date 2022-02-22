package socialnetwork.backend.controller.visitor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialnetwork.backend.dto.reponse.ResponseDetails;
import socialnetwork.backend.dto.reponse.ResponseDetailsWithObject;
import socialnetwork.backend.dto.request.RegisterVisitorDto;
import socialnetwork.backend.dto.request.ResetPasswordDto;
import socialnetwork.backend.dto.request.ResponseDto;
import socialnetwork.backend.dto.request.UpdateVisitorDto;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;
import socialnetwork.backend.service.visitor.VisitorServiceImpl;
import socialnetwork.backend.service.visitorProfile.VisitorProfileServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j

public class VisitorController {

    @Autowired
    private VisitorServiceImpl visitorService;

    Visitor visitor;

    @Autowired
    VisitorProfileServiceImpl visitorProfileService;

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping("")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterVisitorDto registerVisitorDto, HttpServletRequest httpServletRequest) throws Exception {
        if (visitorProfileService.visitorProfileDoesntExistByPhoneNumber(registerVisitorDto.getPhoneNumber())) {
            VisitorProfile profile = modelMapper.map(registerVisitorDto, VisitorProfile.class);
            visitorService.registerVisitor(profile);
        } else {
            throw new GeneralException("User with that phone number already exist");
        }
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Your account has been created successfully", HttpStatus.OK.toString());

        return ResponseEntity.status(200).body(responseDetails);
    }

    @GetMapping("/verify/{phoneNumber}")
    public ResponseEntity<?> verifyUser(@PathVariable String phoneNumber) throws GeneralException {
        ResponseDto responseDto;
        if (phoneNumber == null){
            throw new GeneralException("Please pass a phone number");
        }else {
            responseDto = visitorService.checkIfUserExists(phoneNumber);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) throws GeneralException {
        visitorService.resetPassword(resetPasswordDto.getPhoneNumber(), resetPasswordDto.getPassword());
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Password have been changed successfully", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) throws GeneralException {
        Visitor visitor = visitorService.verifyUser(phoneNumber);
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "User confirmation successful",visitor, "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findVisitorById(@PathVariable String id) throws GeneralException {
        if (visitorService.visitorDoesNotExist(id)){
            throw new GeneralException("User with that id does not exist");
        }
        visitorService.findVisitorById(id);
        return new ResponseEntity<>(visitor, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllUsers() {
        List<Visitor> users = visitorService.findAllVisitors();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("")
    public ResponseEntity<?> updateVisitor(@Valid @RequestBody UpdateVisitorDto updateVisitorDto) throws GeneralException {
        if (updateVisitorDto.getId() == null) {
            throw new GeneralException("User id cannot be null");
        }
        if (visitorService.visitorDoesNotExist(updateVisitorDto.getId())){
            throw new GeneralException("User with that id does not exist");
        }

        Visitor visitor = visitorService.updateVisitor(updateVisitorDto);
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "User account updated successfully", visitor, "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVisitor(@PathVariable String id) throws GeneralException {
        if (visitorService.visitorDoesNotExist(id)){
            throw new GeneralException("User with that id does not exist");
        }
        visitorService.deleteVisitorById(id);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "User account deactivated successfully", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

}
