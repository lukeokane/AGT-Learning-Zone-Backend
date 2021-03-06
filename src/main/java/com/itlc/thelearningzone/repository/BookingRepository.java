package com.itlc.thelearningzone.repository;

import com.itlc.thelearningzone.domain.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "select distinct booking from Booking booking left join fetch booking.userInfos",
        countQuery = "select count(distinct booking) from Booking booking")
    Page<Booking> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct booking from Booking booking left join fetch booking.userInfos")
    List<Booking> findAllWithEagerRelationships();

    @Query("select booking from Booking booking left join fetch booking.userInfos where booking.id =:id")
    Optional<Booking> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query(value = "select distinct booking from Booking booking left join fetch booking.userInfos u left join booking.topics t where booking.startTime between :startTime and :endTime",
    		countQuery = "select count(distinct booking) from Booking booking where booking.startTime between :startTime and :endTime")
    Page<Booking>findAllInTimeFrame(Pageable pageable, @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

    @Query(value = "select distinct booking from Booking booking join booking.userInfos u left join booking.topics t where u.id = :userId and booking.startTime between :startTime and :endTime",
    		countQuery = "select count(distinct booking) from Booking booking join booking.userInfos u left join booking.topics t where u.id = :userId and booking.startTime between :startTime and :endTime")
    Page<Booking> findUserBookingsInTimeFrame(Pageable pageable, @Param("userId") Long userId, @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);
    
    @Query(value = "select distinct booking from Booking booking join booking.userInfos u left join booking.topics t where u.id = :userId",
    		countQuery = "select count(distinct booking) from Booking booking join booking.userInfos u left join booking.topics t where u.id = :userId")
    Page<Booking> findUserBookings(Pageable pageable, @Param("userId") Long userId);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where u.id = :userId AND booking.modifiedTimestamp >= :startTime",
    		countQuery = "select count(distinct booking) from Booking booking join booking.userInfos u where u.id = :userId AND booking.modifiedTimestamp >= :startTime")
    Page<Booking> findUserBookingsModifiedAfterTime(Pageable pageable, @Param("userId") Long userId, @Param("startTime") Instant startTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.modifiedTimestamp >= :startTime",
    		countQuery = "select count(distinct booking) from Booking booking join booking.userInfos u where booking.modifiedTimestamp >= :startTime")
    Page<Booking> findBookingsModifiedAfterTime(Pageable pageable, @Param("startTime") Instant startTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId = :userId and booking.tutorAccepted = 1 and booking.modifiedTimestamp >= :startTime and booking.cancelled = 0",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId = :userId and booking.tutorAccepted = 0 and booking.modifiedTimestamp >= :startTime and booking.cancelled = 0")
    Page<Booking> findTutorBookingsModifiedAfterTime(Pageable pageable, @Param("userId") Integer userId, @Param("startTime") Instant startTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId = :userId and booking.tutorAccepted = 0 and booking.modifiedTimestamp >= :startTime",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId = :userId and booking.tutorAccepted = 0 and booking.modifiedTimestamp >= :startTime")
    Page<Booking> findTutorPendingRequestsBookingsModifiedAfterTime(Pageable pageable, @Param("userId") Integer userId, @Param("startTime") Instant startTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId = :userId and booking.tutorAccepted = 1 and booking.cancelled = 0",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId = :userId and booking.tutorAccepted = 0 and booking.cancelled = 0")
    Page<Booking> findTutorBookings(Pageable pageable, @Param("userId") Integer userId);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId = :userId and booking.tutorAccepted = 0 and booking.cancelled = 0",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId = :userId and booking.tutorAccepted = 0 and booking.cancelled = 0")
    Page<Booking> findTutorPendingRequestsBookings(Pageable pageable, @Param("userId") Integer userId);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId IS NOT NULL and booking.cancelled = 0 and booking.startTime between :startTime and :endTime",
    		countQuery = "select count(distinct booking) from Booking booking where booking.tutorAcceptedId IS NOT NULL and booking.cancelled = 0 and booking.startTime between :startTime and :endTime")
    Page<Booking>findConfirmedInTimeFrame(Pageable pageable, @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where u.id = :userId and booking.tutorAcceptedId IS NOT NULL and booking.cancelled = 0 and booking.startTime between :startTime and :endTime",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u where u.id = :userId and booking.tutorAcceptedId IS NOT NULL and booking.cancelled = 0 and booking.startTime between :startTime and :endTime")
    Page<Booking> findConfirmedUserBookingsInTimeFrame(Pageable pageable, @Param("userId") Long userId, @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId IS NOT NULL and booking.cancelled = 0 and booking.startTime > :startTime and booking.startTime <= :endTime",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u where and booking.tutorAcceptedId IS NOT NULL and booking.cancelled = 0 and booking.startTime > :startTime and booking.startTime <= :endTime")
    List<Booking> findConfirmedBookingsAfterStartTimeAndEndTimeInclusive(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);  
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where u.id = :userId and booking.tutorAcceptedId IS NOT NULL and booking.cancelled = 0",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u where u.id = :userId and booking.tutorAcceptedId IS NOT NULL and booking.cancelled = 0")
    Page<Booking> findUserConfirmedBookings(Pageable pageable, @Param("userId") Long userId);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId IS NOT NULL and booking.tutorAccepted = 1 and booking.cancelled = 0",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u where booking.tutorAcceptedId IS NOT NULL and booking.tutorAccepted = 1 and booking.cancelled = 0")
    Page<Booking> findConfirmedBookings(Pageable pageable);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.tutorAcceptedId IS NOT NULL and booking.tutorAccepted = 1 and booking.cancelled = 0 and booking.modifiedTimestamp >= :startTime",
    		countQuery = "select count(distinct booking) from Booking booking join booking.userInfos u where booking.tutorAcceptedId IS NOT NULL and booking.tutorAccepted = 1 and booking.cancelled = 0 and booking.modifiedTimestamp >= :startTime")
    Page<Booking> findConfirmedBookingsModifiedAfterTime(Pageable pageable, @Param("startTime") Instant startTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u where u.id = :userId and booking.tutorAcceptedId IS NOT NULL and booking.tutorAccepted = 1 and booking.cancelled = 0 and booking.modifiedTimestamp >= :startTime",
    		countQuery = "select count(distinct booking) from Booking booking join booking.userInfos u where u.id = :userId and booking.tutorAcceptedId IS NOT NULL and booking.tutorAccepted = 1 and booking.cancelled = 0 and booking.modifiedTimestamp >= :startTime")
    Page<Booking> findUserConfirmedBookingsModifiedAfterTime(Pageable pageable, @Param("userId") Long userId, @Param("startTime") Instant startTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.adminAcceptedId IS NULL and booking.cancelled = 0",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u where booking.adminAcceptedId IS NULL and booking.cancelled = 0")
    Page<Booking> findBookingsPendingAdminApproval(Pageable pageable);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos u left join booking.topics t where booking.adminAcceptedId IS NULL and booking.cancelled = 0 and booking.modifiedTimestamp >= :startTime",
    		countQuery = "select count(distinct booking) from Booking booking left join booking.userInfos u where booking.adminAcceptedId IS NULL and booking.cancelled = 0 and booking.modifiedTimestamp >= :startTime")
    Page<Booking> findBookingsPendingAdminApprovalChanges(Pageable pageable, @Param("startTime") Instant startTime);
    
    @Query(value = "select distinct booking from Booking booking left join booking.topics t where booking.cancelled = 0 and booking.startTime between :instantFromDate and :instantToDate order by booking.startTime asc")
	List<Booking> findAllBookings(@Param("instantFromDate") Instant instantFromDate,@Param("instantToDate") Instant instantToDate);
  
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos bui where bui.courseYear.id in (select courseYear.id from CourseYear cy where cy.courseYear = :selectedYear) and booking.startTime between :instantFromDate and :instantToDate and booking.cancelled=0 order by booking.startTime asc")
    List<Booking> findBookingsAllcoursesSelectedYear(@Param("instantFromDate") Instant instantFromDate, @Param("instantToDate") Instant instantToDate, @Param("selectedYear") Integer selectedYear);
    
    @Query(value = "select distinct booking from Booking booking left join booking.userInfos bui where bui.courseYear.id in (select courseYear.id from CourseYear cy where cy.courseYear = :selectedYear and cy.course.id = :lCourseId) and booking.startTime between :instantFromDate and :instantToDate and booking.cancelled=0 order by booking.startTime asc")
    List<Booking> findBookingsSelectedCourseSelectedYear(@Param("instantFromDate") Instant instantFromDate, @Param("instantToDate") Instant instantToDate, @Param("lCourseId") Long lCourseId, @Param("selectedYear") Integer selectedYear);

    @Query(value = "select distinct booking from Booking booking left join booking.userInfos bui where bui.courseYear.id in (select courseYear.id from CourseYear cy where cy.course.id = :lCourseId) and booking.startTime between :instantFromDate and :instantToDate and booking.cancelled=0 order by booking.startTime asc")
    List<Booking> findBookingsSelectedCourseAllYears(@Param("instantFromDate") Instant instantFromDate, @Param("instantToDate") Instant instantToDate, @Param("lCourseId") Long lCourseId);
}