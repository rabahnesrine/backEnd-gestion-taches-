package com.supportportal.repository;

import com.supportportal.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface eventRepository  extends JpaRepository<Event,Long> {
    public List<Event> findEventByEventUser(User user);

    List<Event> findEventByArchiveTrue();

}
