package com.itlc.thelearningzone.service.impl;

import com.itlc.thelearningzone.service.BookingService;
import com.itlc.thelearningzone.domain.Booking;
import com.itlc.thelearningzone.domain.User;
import com.itlc.thelearningzone.repository.BookingRepository;
import com.itlc.thelearningzone.service.dto.BookingDTO;
import com.itlc.thelearningzone.service.dto.BookingUserDetailsDTO;
import com.itlc.thelearningzone.service.dto.UserInfoDTO;
import com.itlc.thelearningzone.service.mapper.BookingMapper;
import com.itlc.thelearningzone.repository.UserRepository;

import com.itlc.thelearningzone.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Booking.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;
    
    private final UserRepository userRepository;
    
    private final MailService mailService;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper, UserRepository userRepository,MailService mailService) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.userRepository = userRepository;
        this.mailService = mailService;
   
    }

    /**
     * Save a booking.
     *
     * @param bookingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookingDTO save(BookingDTO bookingDTO) {
        log.debug("Request to save Booking : {}", bookingDTO);
        
        
        if(bookingDTO.isCancelled() == true) {
        	for(UserInfoDTO userInfoDTO : bookingDTO.getUserInfos()) {
        		 
        		  Long id = userInfoDTO.getUserId();
        		  User user = userRepository.getOne(id);
        		  Integer idTut = bookingDTO.getTutorAcceptedId();
        		  Long  tutorID = Long.valueOf(idTut.longValue());
            	  User tutorUser = userRepository.getOne(tutorID);
            	  Booking booking = bookingMapper.toEntity(bookingDTO);
        		  String langKey = "en";
        		  user.setLangKey(langKey);
       		      mailService.sendBookingCancelledEmail(booking, user, tutorUser);
        	}
        	
        }
      if(bookingDTO.isTutorAccepted() == true) {
    	  
    	  BookingUserDetailsDTO bookingUserDetailsDTO = bookingDTO.getBookingUserDetailsDTO().iterator().next();
    	  User user = userRepository.getOne(bookingUserDetailsDTO.getId());
    	  Integer id = bookingDTO.getTutorAcceptedId();
    	  Long  tutorID = Long.valueOf(id.longValue());
    	  User tutorUser = userRepository.getOne(tutorID);
    	  Booking booking = bookingMapper.toEntity(bookingDTO);
		  String langKey = "en";
		  user.setLangKey(langKey);
		  mailService.sendBookingAcceptedByTutorEmail(booking, user, tutorUser);
		  
		  
    }
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking = bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    /**
     * Get all the bookings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAll(pageable)
            .map(bookingMapper::toDto);
    }

    /**
     * Get all the Booking with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<BookingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bookingRepository.findAllWithEagerRelationships(pageable).map(bookingMapper::toDto);
    }
    

    /**
     * Get one booking by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookingDTO> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findOneWithEagerRelationships(id)
            .map(bookingMapper::toDto);
    }

    /**
     * Delete the booking by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
    }
}
