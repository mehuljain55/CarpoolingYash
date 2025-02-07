package com.yash.CarPolling.service;

import com.yash.CarPolling.entity.*;
import com.yash.CarPolling.entity.enums.*;
import com.yash.CarPolling.entity.models.ApiResponseModel;
import com.yash.CarPolling.entity.models.BuddiesDetail;
import com.yash.CarPolling.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private RoutesRepo routesRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private BookingRequestRepo bookingRequestRepo;

    @Autowired
    private VechileRepo vechileRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PickUpPlacesRepo pickUpPlacesRepo;

    public ApiResponseModel createBooking(int routeId, User user)
    {
        Optional<Routes> routesOptional=routesRepo.findById(routeId);
        List<User> userList=new ArrayList<>();

        if(routesOptional.isPresent())
        {
            Routes routes=routesOptional.get();
            Vechile vechile=routesRepo.findVechileByRouteNo(routeId);
            Bookings bookings=bookingRepo.findBookingsByRouteId(routeId);


            if(user.getBookingStatus().equals(BookingStatus.booked))
            {
                return new ApiResponseModel<>(StatusResponse.failed,null,"Cancel previous booking first");
            }

            if(vechile.getAvailable_capacity()==0)
            {
                return new ApiResponseModel<>(StatusResponse.failed,null,"Vechile is full");
            }

            if(routes.getBookingType().equals(BookingType.reserved))
            {
                System.out.println("Request reserved");
                List<BookingRequest> bookingRequestCheck=bookingRequestRepo.findBookingRequestEmployeeandRouteId(user.getEmailId(),routes.getRouteId(),RequestStatus.pending);

                if(bookingRequestCheck.size()>0)
                {
                    return new ApiResponseModel<>(StatusResponse.failed,null,"User already request wait for approval");
                }

                User vechileOwner=vechileRepo.findUserByVechileNo(vechile.getVechileNo());
                BookingRequest bookingRequest=new BookingRequest();
                bookingRequest.setName(user.getName());
                bookingRequest.setRouteId(routes.getRouteId());
                bookingRequest.setBookingId(bookings.getBookingId());
                bookingRequest.setEmployeeID(user.getEmailId());
                bookingRequest.setOwnerEmployeeId(vechileOwner.getEmailId());
                bookingRequest.setRequestStatus(RequestStatus.pending);
                bookingRequestRepo.save(bookingRequest);
                return new ApiResponseModel<>(StatusResponse.success,null,"Booking request sent awating for conformation");
            }

            if(bookings!=null)
            {
                userList.add(user);
                user.setBookings(bookings);
                bookings.setUsers(userList);

            }else{
                bookings=new Bookings();
                bookings.setRoutes(routes);
                bookings.setUsers(userList);
                Bookings booking=bookingRepo.save(bookings);
                user.setBookings(booking);
            }
            int available_capacity=vechile.getAvailable_capacity()-1;
            vechile.setAvailable_capacity(available_capacity);
            user.setBookingStatus(BookingStatus.booked);
            bookingRepo.save(bookings);
            userRepo.save(user);
            vechileRepo.save(vechile);
            return new ApiResponseModel<>(StatusResponse.success,null,"Booking conformed");

        }else {
            return new ApiResponseModel<>(StatusResponse.failed,null,"Route not found");
        }
    }



    public ApiResponseModel getBuddiesInformation(String userId)
    {

        Integer bookingId=userRepo.findBookingIdByEmailId(userId);

        if(bookingId==null)
        {
            return new ApiResponseModel<>(StatusResponse.not_found,null,"No booking found");
        }

        Integer routeId=bookingRepo.findRouteIdByBookingId(bookingId);


        List<User> userList=bookingRepo.findUsersByRouteId(routeId);
        List<BuddiesDetail> buddiesDetailList=new ArrayList<>();
        if(userList.size()>0)
        {
            Optional<Routes> routesOptional=routesRepo.findById(routeId);
            Routes routes=routesOptional.get();
            User user=vechileRepo.findUserByVechileNo(routes.getVechile().getVechileNo());
            String emailId=user.getEmailId();

            for(User users:userList)
            {
                BuddiesDetail buddiesDetail=new BuddiesDetail();
                buddiesDetail.setEmailId(users.getEmailId());
                buddiesDetail.setName(users.getName());
                buddiesDetail.setContactNo(user.getMobileNo());

                if(emailId.equals(users.getEmailId()))
                {
                    buddiesDetail.setBuddyTag(BuddyTag.owner);
                }else {
                    buddiesDetail.setBuddyTag(BuddyTag.buddy);
                }
                buddiesDetailList.add(buddiesDetail);
            }
           return new ApiResponseModel<>(StatusResponse.success,buddiesDetailList,"Buddies details found");
        }else {
            return new ApiResponseModel<>(StatusResponse.not_found,null,"No booking found");
        }

    }

    public ApiResponseModel findMyBooking(String emailId)
    {
        Bookings bookings=userRepo.findBookingByEmailId(emailId);
         if(bookings!=null) {
             bookings.setUsers(null);
             return new ApiResponseModel<>(StatusResponse.success, bookings, "Booking found");
         }else {
             return new ApiResponseModel<>(StatusResponse.not_found, bookings, "Booking found");

         }
    }

    public ApiResponseModel cancelBooking(String emailId)
    {
        try {
            Optional<User> userOptional = userRepo.findById(emailId);
            User user = userOptional.get();
            Integer bookingId=userRepo.findBookingIdByEmailId(emailId);

            if(user.getBookingStatus().equals(BookingStatus.not_booked)|| bookingId==null)
            {
                return new ApiResponseModel<>(StatusResponse.failed,null,"No booking found");
            }

            int routeId=bookingRepo.findRouteIdByBookingId(bookingId);
            Vechile vechile=routesRepo.findVechileByRouteNo(routeId);
            int avaibaleCapacity=vechile.getAvailable_capacity()+1;

            if(vechile.getUser().getEmailId().equals(emailId)) {
                Optional<Routes> routesOptional = routesRepo.findById(routeId);
                Bookings bookings = bookingRepo.findBookingsByRouteId(routeId);
                Routes routes = routesOptional.get();
                List<User> userList = bookingRepo.findUsersByRouteId(routeId);

                for (User userRequest : userList) {
                    userRequest.setBookings(null);
                    updateUserBookingStatus(userRequest, BookingStatus.not_booked);
                }

                vechile.setAvailable_capacity(vechile.getMax_capacity());
                bookingRepo.delete(bookings);
                routesRepo.delete(routes);
                vechileRepo.save(vechile);
                return new ApiResponseModel<>(StatusResponse.success,null,"Route removed");
            }

            if(avaibaleCapacity<=vechile.getMax_capacity())
            {
                vechile.setAvailable_capacity(avaibaleCapacity);
            }else{
                vechile.setAvailable_capacity(vechile.getMax_capacity());
            }
            user.setBookings(null);
            updateUserBookingStatus(user,BookingStatus.not_booked);
            userRepo.save(user);
            vechileRepo.save(vechile);
            return new ApiResponseModel<>(StatusResponse.success, null, "Booking cancelled successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ApiResponseModel<>(StatusResponse.failed, null, "Unable to cancel booking");

        }
    }

    public ApiResponseModel findBookingRequest(User user)
    {
        List<BookingRequest> bookingRequestList=bookingRequestRepo.findBookingRequestOwner(user.getEmailId(),RequestStatus.pending);
        if(bookingRequestList.size()>0)
        {
            return new ApiResponseModel<>(StatusResponse.success, bookingRequestList, "Booking Requests found");
        }else {
            return new ApiResponseModel<>(StatusResponse.not_found, null, "No request found");
        }

    }

    public ApiResponseModel updateBookingRequest(int requestId,RequestStatus requestStatus)
    {
        Optional<BookingRequest> optionalBookingRequest=bookingRequestRepo.findById(requestId);

        if(optionalBookingRequest.isPresent()) {
            BookingRequest bookingRequest = optionalBookingRequest.get();
             Bookings bookings=userRepo.findBookingByEmailId(bookingRequest.getEmployeeID());
             if(bookings!=null)
             {
                 bookingRequest.setRequestStatus(RequestStatus.rejected);
                 bookingRequest.setMessage("User already have booking request cancelled by system");
                 bookingRequestRepo.save(bookingRequest);
                 return new ApiResponseModel<>(StatusResponse.failed,null,"User already booked");
             }else{
                 bookingRequest.setMessage("Booking "+requestStatus.toString()+" by owner");
                  if(requestStatus.equals(RequestStatus.accepted))
                  {
                      Bookings userBooking=bookingRepo.findBookingsByBookingId(bookingRequest.getBookingId());
                      if(userBooking==null)
                      {
                          bookingRequest.setRequestStatus(RequestStatus.rejected);
                          bookingRequest.setMessage("Booking does not exists request cancelled by system");
                          bookingRequestRepo.save(bookingRequest);
                          return new ApiResponseModel<>(StatusResponse.failed,null,"Booking does not exists");
                      }else {
                        Optional<User> userOptional=userRepo.findById(bookingRequest.getEmployeeID());
                        User user=userOptional.get();
                        user.setBookings(userBooking);
                        bookingRequest.setRequestStatus(RequestStatus.accepted);
                        bookingRequestRepo.save(bookingRequest);
                        userRepo.save(user);
                        return new ApiResponseModel<>(StatusResponse.success,null,"Request updated");
                      }
                  }else {

                      bookingRequest.setRequestStatus(RequestStatus.rejected);
                      bookingRequest.setMessage("Request rejected by user");
                      bookingRequestRepo.save(bookingRequest);
                      return new ApiResponseModel<>(StatusResponse.failed,null,"Request rejected");

                  }
             }
        }else {
            return new ApiResponseModel<>(StatusResponse.failed,null,"No request found");
        }
    }

    public void updateUserBookingStatus(User user,BookingStatus status)
    {
        user.setBookingStatus(status);
        userRepo.save(user);
    }

}
