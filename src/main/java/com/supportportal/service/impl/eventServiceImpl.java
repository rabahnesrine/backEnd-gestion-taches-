package com.supportportal.service.impl;

import com.supportportal.domain.*;
import com.supportportal.repository.*;
import com.supportportal.service.eventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class eventServiceImpl implements eventService {
private eventRepository eventRepository ;
private UserRepository userRepository;
    @Autowired
    public eventServiceImpl(eventRepository eventRepository,UserRepository userRepository ) {
      this.eventRepository=eventRepository;
      this.userRepository=userRepository;
    }



    @Override
    public List<Event> findEventByEventUser(Long id) {

        User findUser= new User();
        findUser=userRepository.findUserById(id);

        return this.eventRepository.findEventByEventUser(findUser);
    }





    @Override
    public List<Event> findEventByArchiveTrue() {

        return eventRepository.findEventByArchiveTrue();
    }

    public   Event updateEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    @Override
    public void deleteEvent(long id) {
        eventRepository.deleteById(id);

    }
}
