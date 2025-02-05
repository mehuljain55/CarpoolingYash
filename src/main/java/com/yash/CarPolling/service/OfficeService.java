package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.Office;
import com.yash.CarPolling.entity.enums.StatusResponse;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.repository.OfficeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepo officeRepo;

    public ApiResponseModel officeList()
    {
        List<Office> officeList=officeRepo.findAll();
        if(officeList.size()>0)
        {
            return new ApiResponseModel<>(StatusResponse.success,officeList,"Office List");
        }else{
            return new ApiResponseModel<>(StatusResponse.not_found,null,"Office List not found");
        }
    }


}
