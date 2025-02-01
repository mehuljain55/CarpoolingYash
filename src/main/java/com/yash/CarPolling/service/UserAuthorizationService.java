package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.enums.UserRoles;
import com.yash.CarPolling.entity.enums.UserStatus;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.entity.models.UserLoginModel;
import com.yash.CarPolling.repository.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserAuthorizationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  JwtUtils jwtUtils;

    public ApiResponseModel addUser(User user)
    {

        try {
            Optional<User> optionalUser=userRepo.findById(user.getEmailId());
            if(optionalUser.isPresent())
            {
                return new ApiResponseModel(StatusResponse.failed,null ,"User Already Present");
            }

            user.setRole(UserRoles.user);
            user.setStatus(UserStatus.not_active);
            user.setPassword(hashPassword(user.getPassword()));
            userRepo.save(user);
            return new ApiResponseModel(StatusResponse.success,null ,"Trainer Added");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ApiResponseModel(StatusResponse.failed,null ,"Unable to add trainer");
        }
    }

    public ApiResponseModel<UserLoginModel> validateUserLogin(String emailId, String password)
    {
        Optional<User> opt= userRepo.findById(emailId);
        if(opt.isPresent())
        {
            User user=opt.get();
            if(!(user.getStatus().equals(UserStatus.active)))
            {
                return  new ApiResponseModel<>(StatusResponse.unauthorized,null,"User approval pending");
            }
            else if((verifyPassword(password,user.getPassword())))
            {
                String token=jwtUtils.generateToken(user);
                UserLoginModel userLoginModel=new UserLoginModel(user,token);
                return  new ApiResponseModel<>(StatusResponse.success,userLoginModel,"User Validated");
            }else {
                return  new ApiResponseModel<>(StatusResponse.unauthorized,null,"Invalid password");
            }
        }else {
            return  new ApiResponseModel<>(StatusResponse.not_found,null,"User Not exists");
        }
    }

    public boolean validateUserToken(String emailId,String token)
    {
        Optional<User> opt= userRepo.findById(emailId);
        if(opt.isPresent())
        {
            User user=opt.get();
            boolean status=jwtUtils.validateTokenForUser(user,token);
            return  status;
        } else {
            return  false;
        }
    }

    public boolean validateUserAccess(User user, String token, UserRoles requestedAccessRole)
    {
        Optional<User> opt= userRepo.findById(user.getEmailId());
        if(opt.isPresent())
        {
            User validateUser=opt.get();
            boolean status=jwtUtils.validateTokenForUserRole(validateUser,token,requestedAccessRole);
            return  status;
        } else {
            return  false;
        }
    }

    private String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

}
