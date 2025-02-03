package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.User;
import com.yash.CarPolling.entity.Vechile;
import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.enums.VechileStatus;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.repository.VechileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VechileService {

    @Autowired
    private VechileRepo vechileRepo;

    public ApiResponseModel addVechile(Vechile vechile, User user)
    {
        try {
            Optional<Vechile> optionalVechile=vechileRepo.findById(vechile.getVechileNo());

            if(optionalVechile.isPresent())
            {
                return new ApiResponseModel(StatusResponse.failed,null,"Vechile Already exists");
            }

            vechile.setUser(user);
            vechile.setStatus(VechileStatus.pending);
            vechileRepo.save(vechile);
            return new ApiResponseModel<>(StatusResponse.success,null,"Vechile Added");
        }catch (Exception e)
        {
            e.printStackTrace();
            return  new ApiResponseModel<>(StatusResponse.failed,null,"Error in adding vechile");
        }
    }
}
