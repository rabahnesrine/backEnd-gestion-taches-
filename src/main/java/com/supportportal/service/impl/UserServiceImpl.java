package com.supportportal.service.impl;

import com.supportportal.constant.FileConstant;
import com.supportportal.domain.User;
import com.supportportal.domain.UserPrincipal;
import com.supportportal.enumeration.Role;
import com.supportportal.exception.domain.*;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.EmailService;
import com.supportportal.service.LoginAttemptService;
import com.supportportal.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static com.supportportal.constant.FileConstant.*;
import  static com.supportportal.constant.UserImplConstant.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.supportportal.enumeration.Role.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.*;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService ;
    private EmailService emailService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,LoginAttemptService loginAttemptService
            ,EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService=loginAttemptService;
        this.emailService=emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
           validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    @Override
    public User register( String username,String telephone ,String professionUser, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException, IOException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
    user.setTelephone(telephone);
    user.setProfessionUser(professionUser);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setIsActive(true);
        user.setIsNotLocked(false);
        user.setRole(ROLE_MEMBER.name());
        user.setAuthorities(ROLE_MEMBER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        Path userFolder = Paths.get(FileConstant.USER_FOLDER +user.getUsername()).toAbsolutePath().normalize(); //user/home/supportportal/user/ness
        if(!Files.exists(userFolder)){
            Files.createDirectories(userFolder);
            LOGGER.info(DIRECTORY_CREATED + userFolder);
        }
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        emailService.sendNewPasswordEmail(user.getUsername(),password,user.getEmail());
        return user;
    }

    @Override
    public User addNewUser(String userName, String emailUser, String telephone, String professionUser, String roles, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, MessagingException {
        validateNewUsernameAndEmail(StringUtils.EMPTY,userName,emailUser);
        User user =new User();
        String password = generatePassword();
        user.setUserId(generateUserId());
        user.setUsername(userName);
        user.setEmail(emailUser);
        user.setTelephone (telephone);
        user.setProfessionUser(professionUser);
        user.setJoinDate(new Date());
        user.setPassword(encodePassword(password));
        user.setIsActive(isActive);
        user.setIsNotLocked(isNotLocked);

        user.setRole(getRoleEnumName(roles).name());

        user.setAuthorities(getRoleEnumName(roles).getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(userName));
        userRepository.save(user);
        saveProfileImage(user,profileImage);
        LOGGER.info("New user password: " + password);
        emailService.sendNewPasswordEmail(user.getUsername(),password,user.getEmail());



        return user;
    }



    @Override
    public List<User> getUsers() {
        LOGGER.info("|||"+userRepository.findAll().get(0).getIsActive());
        return userRepository.findAll();
    }


    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException, NotAnImageFileException {
        if(profileImage!=null){
            if(!Arrays.asList(IMAGE_JPEG_VALUE,IMAGE_PNG_VALUE,IMAGE_GIF_VALUE).contains(profileImage.getContentType())){
               throw new NotAnImageFileException(profileImage.getOriginalFilename()+"is not an image file.Please upload an image");
            }
            Path userFolder = Paths.get(FileConstant.USER_FOLDER +user.getUsername()).toAbsolutePath().normalize(); //user/home/supportportal/user/ness
            if(!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder+user.getUsername()+DOT+ JPG_EXTENSION));//supp img s'il existe
            Files.copy(profileImage.getInputStream(),userFolder.resolve(user.getUsername()+DOT+JPG_EXTENSION),REPLACE_EXISTING); //replace img existe qu a la meme nom et folder
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            userRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM+ profileImage.getOriginalFilename());
        }}
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserById(long idUser){return  userRepository.findUserById(idUser);}

    @Override
    public User findUserByUserId(String userId) {
        return  userRepository.findUserByUserId(userId);
    }

    @Override
    public User updateUser(String currentNomUser, String userName, String emailUser, String telephone, String professionUser, String roles, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        validateNewUsernameAndEmail(currentNomUser,userName,emailUser);
        User user =userRepository.findUserByUsername(currentNomUser);
        user.setUsername(userName);
        user.setEmail(emailUser);
        user.setTelephone (telephone);
        user.setProfessionUser(professionUser);
        user.setIsActive(isActive);
        user.setIsNotLocked(isNotLocked);

        user.setRole(getRoleEnumName(roles).name());
        user.setAuthorities(getRoleEnumName(roles).getAuthorities());
        saveProfileImage(user,profileImage);
        userRepository.save(user);
        return user;

    }


    @Override
    public void deleteUser(String username)throws IOException {
        User user =userRepository.findUserByUsername(username);
        Path userFolder =Paths.get(USER_FOLDER+user.getUsername()).toAbsolutePath().normalize();
        FileUtils.deleteDirectory(new File(userFolder.toString()));
        userRepository.deleteById(user.getId());

    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
        User user =userRepository.findUserByEmail(email);
        if(user==null){
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL+email);
        }
        String password=generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(user.getUsername(),password,user.getEmail());
        LOGGER.info("New user password:"+ password);

    }

    @Override
    public User updateProfileImage(String nomUser, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user= validateNewUsernameAndEmail(nomUser,null,null);
        saveProfileImage(user,profileImage);
        return user;
    }



    private String getTemporaryProfileImageUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.DEFAULT_USER_IMAGE_PATH).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }


public void validateLoginAttempt(User user)  {
        //check si user compte verrouillé  isNOTlocked=false
        if(user.getIsNotLocked()){
            boolean maxAttemps=this.loginAttemptService.hasExceededMaxAttempts(user.getUsername());
            LOGGER.info("valueattemps"+maxAttemps);
            if(maxAttemps==true){
                user.setIsNotLocked(false); //bloquée
                LOGGER.info(user.getIsNotLocked()+"|||1|");

            }else{
                this.loginAttemptService.addUserToLoginAttemptCache(user.getUsername());
                user.setIsNotLocked(true); //ouvert
LOGGER.info(user.getIsNotLocked()+"|||2|");
            }

        }else{
            loginAttemptService.evitUserFromLoginAttemptCache(user.getUsername()); //true :ouvert
            LOGGER.info(user.getIsNotLocked()+"|||3|");

        }
    }


/* plusieurs erreur ici

    public void validateLoginAttempt(User user)  {
        //check si user compte verrouillé  isNOTlocked=false
        if(user.getIsNotLocked()==true){
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())){
                user.setIsNotLocked(false);;//bloquée
                userRepository.update(user);
                LOGGER.info("||||| Bloque user"+user.getUsername()+user.getIsNotLocked());
            }else{
                loginAttemptService.addUserToLoginAttemptCache(user.getUsername());

                user.setIsNotLocked(true); //ouvert
                userRepository.update(user);

                LOGGER.info("||||| attemps<5 user"+user.getUsername()+user.getIsNotLocked());

            }

        }else {
            loginAttemptService.evitUserFromLoginAttemptCache(user.getUsername()); //true :ouvert
            LOGGER.info("||||| ouvrir session user"+user.getUsername()+user.getIsNotLocked());

        }
    }*/

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }
    //return actuel location of img

    private String setProfileImageUrl(String nomUser) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + nomUser + FORWARD_SLASH + nomUser + DOT + JPG_EXTENSION).toUriString();
    }
        private String getTemporaryProfileImageUrl(String userName) {
            return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.DEFAULT_USER_IMAGE_PATH+ userName).toUriString() ;
        }



}
