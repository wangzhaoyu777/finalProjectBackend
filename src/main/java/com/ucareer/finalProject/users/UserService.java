package com.ucareer.finalProject.users;

import com.ucareer.finalProject.LandingPage.Landing;
import com.ucareer.finalProject.LandingPage.LandingRepository;
import com.ucareer.finalProject.core.JWT;
import com.ucareer.finalProject.heads.Head;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private PasswordEncoder encoder;

    private final UserRepository userRepository;
    private LandingRepository landingRepository;

    @Autowired
    private JWT jwt;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        encoder = new BCryptPasswordEncoder();
        //newUser.setPassword(encoder.encode(user.getPassword()));
        // return encoder.matches(user.getPassword(), loginUser.getPassword()) ?
        //createToken(loginUser) : null;
    }

    public User userRegister(User userBody) {
        User foundUser = userRepository.findByUsername(userBody.getUsername());
        if (foundUser != null) {
            // username already exist
            return null;
        } else {

            //huifu
            User savingUser = new User();
            savingUser.setFirstName(userBody.getFirstName());
            savingUser.setLastName(userBody.getLastName());
            savingUser.setUsername(userBody.getUsername());
            savingUser.setPhone(userBody.getPhone());
            savingUser.setAddress(userBody.getAddress());
            savingUser.setPassword(encoder.encode(userBody.getPassword()));

            //在用户注册完成之后，会自动生成一个default的landing page
            Landing defaultLanding = new Landing();
            defaultLanding.setName("Welcome");
            savingUser.setLanding(defaultLanding);
            //自动生成head
            Head defaultHead = new Head();
            defaultHead.setTitle("title");
            defaultHead.setDescription("Description");
            defaultHead.setImg_url("Img_url");
            defaultLanding.setHead(defaultHead);

            User savedUser = userRepository.save(savingUser);

            return savedUser;
        }
    }

    public String userLogin(LoginRequestBody body){
        User foundUser = userRepository.findByUsername(body.getUsername());
        if (foundUser != null) {
            if (encoder.matches(body.getPassword(), foundUser.getPassword())) {
                return jwt.creatLoginToken(foundUser.getUsername());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(String username, User userRequestBody) {
        User foundUser = userRepository.findByUsername(username);
        if(foundUser != null){
            if(userRequestBody.getFirstName() != null && !userRequestBody.getFirstName().isEmpty()){
                foundUser.setFirstName(userRequestBody.getFirstName());
            }
            if(userRequestBody.getLastName() != null && !userRequestBody.getLastName().isEmpty()){
                foundUser.setLastName(userRequestBody.getLastName());
            }
            if(userRequestBody.getPhone() != null && !userRequestBody.getPhone().isEmpty()){
                foundUser.setPhone(userRequestBody.getPhone());
            }
            if(userRequestBody.getAddress() != null && !userRequestBody.getAddress().isEmpty()){
                foundUser.setAddress(userRequestBody.getAddress());
            }
        }
        return userRepository.save(foundUser);
    }
}
