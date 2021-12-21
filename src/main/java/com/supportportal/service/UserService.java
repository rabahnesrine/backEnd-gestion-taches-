package com.supportportal.service;

import com.supportportal.domain.User;
import com.supportportal.exception.domain.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    User register( String username,String telephone ,String professionUser, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException, IOException;

    List<User> getUsers();
    User addNewUser(String nomUser, String emailUser, String telephone, String professionUser, String roles, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, MessagingException;

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User findUserById(long id);
    User findUserByUserId(String userId);
    User updateUser(String currentNomUser , String newNomUser, String newEmailUser, String telephone, String professionUser, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
    void deleteUser(String username) throws IOException;
    void resetPassword(String email) throws EmailNotFoundException, MessagingException;
    User updateProfileImage(String nomUser, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;


}
