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
import socialnetwork.backend.exception.InvalidVisitorException;
import socialnetwork.backend.exception.VisitorAlreadyExistException;
import socialnetwork.backend.exception.VisitorDoesNotException;
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
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = " https://div-son.herokuapp.com")

public class VisitorController {

    @Autowired
    private VisitorServiceImpl visitorService;

    Visitor visitor;

    @Autowired
    VisitorProfileServiceImpl visitorProfileService;

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping("")
    public ResponseEntity<?> registerVisitor(@Valid @RequestBody RegisterVisitorDto registerVisitorDto, HttpServletRequest httpServletRequest) throws Exception {
        if (visitorProfileService.visitorDoesNotExistByEmail(registerVisitorDto.getEmail())) {
            VisitorProfile profile = modelMapper.map(registerVisitorDto, VisitorProfile.class);
            visitorService.registerVisitor(profile);
        }
        else {
            throw new VisitorAlreadyExistException("Visitor with that email already exist.");
        }
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Your account has been created successfully.", HttpStatus.OK.toString());
        return ResponseEntity.status(200).body(responseDetails);
    }

    @GetMapping("/verify/{email}")
    public ResponseEntity<?> verifyIfVisitorExist(@PathVariable String email) throws GeneralException {
        ResponseDto responseDto;
        if (email == null){
            throw new InvalidVisitorException("Visitor email shouldn't be empty.");
        }else {
            responseDto = visitorService.checkIfVisitorExists(email);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) throws GeneralException {
        visitorService.resetPassword(resetPasswordDto.getEmail(), resetPasswordDto.getPassword());
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Password have been changed successfully.", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyVisitorsEmail(@RequestParam("email") String email) throws GeneralException {
        Visitor visitor = visitorService.verifyVisitor(email);
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "Visitor confirmation successful.",visitor, "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findVisitorById(@PathVariable String id) throws GeneralException {
        if (visitorService.visitorDoesNotExist(id)){
            throw new VisitorDoesNotException("A visitor with that id does not exist.");
        }
        visitorService.findVisitorById(id);
        return new ResponseEntity<>(visitor, HttpStatus.OK);
    }

    @GetMapping("/all-visitors")
    public ResponseEntity<?> findAllVisitors() {
        List<Visitor> allVisitors = visitorService.findAllVisitors();
        return new ResponseEntity<>(allVisitors, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateVisitor(@Valid @RequestBody UpdateVisitorDto updateVisitorDto) throws GeneralException {
        if (updateVisitorDto.getId() == null) {
            throw new InvalidVisitorException("Visitor id is null.");
        }
        if (visitorService.visitorDoesNotExist(updateVisitorDto.getId())){
            throw new VisitorDoesNotException("Visitor with that id does not exist.");
        }

        Visitor visitor = visitorService.updateVisitor(updateVisitorDto);
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "Visitor's account updated successfully.", visitor, "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVisitor(@PathVariable String id) throws GeneralException {
        if (visitorService.visitorDoesNotExist(id)){
            throw new VisitorDoesNotException("Visitor with that id does not exist.");
        }
        visitorService.deleteVisitorById(id);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Visitor's account has been deactivated successfully.", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }
}
