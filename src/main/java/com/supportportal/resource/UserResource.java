package com.supportportal.resource;

import com.supportportal.domain.*;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.*;
import com.supportportal.repository.CalendrierRepository;
import com.supportportal.repository.UserRepository;
import com.supportportal.repository.eventRepository;
import com.supportportal.service.LoginAttemptService;
import com.supportportal.service.UserService;
import com.supportportal.utility.JWTTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.support.EventPublishingRepositoryProxyPostProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.supportportal.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static  com.supportportal.constant.FileConstant.*;


import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping(path = { "/", "/user"})
public class UserResource extends ExceptionHandling {
    public static final String EMAIL_SENT = "An email with a new password was sent to :";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private eventRepository eventRepository;
    @Autowired
    private CalendrierRepository calendrierRepository;

    @Autowired
private UserRepository userRepository ;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private LoginAttemptService loginAttemptService;
    @Autowired
    public UserResource(AuthenticationManager authenticationManager, UserService userService, JWTTokenProvider jwtTokenProvider,LoginAttemptService loginAttemptService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginAttemptService=loginAttemptService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }



    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException, IOException {
        User newUser = userService.register(user.getUsername(),user.getTelephone(),user.getProfessionUser(), user.getEmail());
        return new ResponseEntity<>(newUser, OK);
    }
    @PostMapping("user/add")
    @PreAuthorize("hasAnyAuthority('user:create')")

    public ResponseEntity<User> addNewUser(@RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("telephone")String telephone,
                                           @RequestParam("professionUser")String professionUser,
                                           @RequestParam("role") String role,
                                           @RequestParam("isNotLocked") String isNotLocked,
                                           @RequestParam("isActive")String isActive,
                                           @RequestParam( value = "profileImage",required = false) MultipartFile profileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, MessagingException {
        User newUser=userService.addNewUser(username,email,telephone,professionUser,role,Boolean.parseBoolean(isNotLocked),Boolean.parseBoolean(isActive),profileImage);
        return new ResponseEntity<>(newUser,OK);
    }
    @PostMapping("/update")
    //@PreAuthorize("hasAnyAuthority('user:update')")
    public ResponseEntity<User> update(@RequestParam("currentNomUser") String currentNomUser,
                                       @RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam("telephone")String telephone,
                                       @RequestParam("professionUser")String professionUser,
                                       @RequestParam("role") String role,
                                       @RequestParam("isNotLocked") String isNotLocked,
                                       @RequestParam("isActive")String isActive,
                                       @RequestParam( value = "profileImage",required = false)MultipartFile profileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User updatedUser=userService.updateUser(currentNomUser,username,email,telephone,professionUser,role,Boolean.parseBoolean(isNotLocked),Boolean.parseBoolean(isActive),profileImage);
        return new ResponseEntity<>(updatedUser,OK);
    }
   @PostMapping("/updateProfile")

    public ResponseEntity<User> updateProfile(@RequestParam("currentNomUser") String currentNomUser,
                                       @RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam("telephone")String telephone,
                                       @RequestParam("professionUser")String professionUser,
                                       @RequestParam("role") String role,
                                       @RequestParam("isNotLocked") String isNotLocked,
                                       @RequestParam("isActive")String isActive,
                                       @RequestParam( value = "profileImage",required = false)MultipartFile profileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User updatedUser=userService.updateUser(currentNomUser,username,email,telephone,professionUser,role,Boolean.parseBoolean(isNotLocked),Boolean.parseBoolean(isActive),profileImage);
        return new ResponseEntity<>(updatedUser,OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username")String username){
        User user=userService.findUserByUsername(username);
        return new ResponseEntity<>(user,OK);

    }


   /* @GetMapping("/findProjet/{username}")
    public ResponseEntity<List<Projet>> getUserProjet(@PathVariable("username")String username){
        User user=userService.findUserByUsername(username);
        List<Projet> projets =user.getProjets();
        return new ResponseEntity<>(projets,OK);
    }*/



    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users= userService.getUsers();
        return new ResponseEntity<>(users,OK);

    }
    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email")String email) throws EmailNotFoundException, MessagingException {
        userService.resetPassword(email);
        return response(OK,  EMAIL_SENT + email);
    }

    @DeleteMapping("/delete/{username}")
   @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
      //  userRepository.deleteById();

      userService.deleteUser(username);
        return response(NO_CONTENT, USER_DELETED_SUCCESSFULLY);

    }
    @GetMapping("/findbyUserId/{userId}")
    public ResponseEntity<User> findUserByUserId(@PathVariable String userId){
        User user=userService.findUserByUserId(userId);
        return new ResponseEntity<>(user,OK);}


    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username,
                                                   @RequestParam( value = "profileImage")MultipartFile profileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user=userService.updateProfileImage(username,profileImage);
        return new ResponseEntity<>(user,OK);
    }
    @GetMapping(path="/image/{username}/{fileName}",produces =IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username,
                                  @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER+username+FORWARD_SLASH+fileName));
        //user.home + /supportPortal/user/+ness+/+ness.jpg
    }
    //imageTempor
    @GetMapping(path="/image/profile/{username}",produces =IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username ) throws IOException {
        URL url =new URL(TEMP_PROFILE_IMAGE_BASE_URL+ username);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(); //to store all the  data come from url
        try (InputStream inputStream=url.openStream())/*open url*/{
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead=inputStream.read(chunk))>0){
                byteArrayOutputStream.write(chunk,0,bytesRead); //0 -1024 bytes ...
            }
        }
        return byteArrayOutputStream.toByteArray();
    }



    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


 /*   @PostMapping("/addtoCalendrier")
    public ResponseEntity<List<Calendrier>> addtoCalendrie(@RequestBody Calendrier calendrier) {
        Calendrier c=calendrierRepository.save(calendrier);
        List<Calendrier> listEvtCal=calendrierRepository.findAll();
        return new ResponseEntity<>(listEvtCal, OK);
    }
    @GetMapping("/listEvt")
    public ResponseEntity<List<Calendrier>> getAllEvent(){
        List<Calendrier> listEvt= calendrierRepository.findAll();
        return new ResponseEntity<>(listEvt,OK);

    }*/

  /* @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        event.setDateCreation(new Date());
        event.setArchive(false);
        event.setEtatEvent("Confirmer");
        Event e=eventRepository.save(event);
        return new ResponseEntity<>(e, OK);
    }
    @GetMapping("/getEvent/{id}")
    public ResponseEntity<List<Event>> getEvent(@PathVariable("id") Long id) {
        User user=userRepository.findUserById(id);
        List<Event> listEvent=eventRepository.findAll();
        List<Event> listEventFiltre = new ArrayList<>();


            for (Event event : listEvent) {
                if (event.getInvitedPersons().indexOf(id)!=-1)  {
                    listEventFiltre.add(event);
                    // break;  return filteredListSprint.size();
                }
            }


       /* listEventFiltre=listEvent.stream()
                .filter(e->e.getInvitedPersons().contains(user.getId()))
                .collect(Collectors.toList());*/
      /*  return new ResponseEntity<>(listEventFiltre, OK);
    } */
}
