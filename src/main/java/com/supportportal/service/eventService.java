package com.supportportal.service;

import com.supportportal.domain.Event;
import com.supportportal.domain.User;

import java.util.List;

public interface eventService {
      List<Event> findEventByEventUser(Long id);
     List<Event> findEventArchiveByEventUser(Long id);
      Event updateEvent(Event event);
      void deleteEvent(long id);
      List<Event> findEventByArchiveTrue();


}
