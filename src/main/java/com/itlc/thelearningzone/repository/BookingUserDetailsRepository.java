package com.itlc.thelearningzone.repository;

import com.itlc.thelearningzone.domain.BookingUserDetails;

import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BookingUserDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingUserDetailsRepository extends JpaRepository<BookingUserDetails, Long> {

	@Query("select distinct bookingUserDetails from BookingUserDetails bookingUserDetails where bookingUserDetails.booking.id =:id and bookingUserDetails.usercheckInTime != null")
	Set<BookingUserDetails> findAlltest(@Param("id") Long id);
	
	@Query("select bookingUserDetails from BookingUserDetails bookingUserDetails where bookingUserDetails.booking.id =:bookingID and bookingUserDetails.userInfo.id =:userInfoID")
	BookingUserDetails findOneByBookingIdAndLogin(@Param("bookingID") Long bookingID, @Param("userInfoID") Long userInfoID);

}
