package com.supportportal.repository;

import com.supportportal.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface eventRepository  extends JpaRepository<Event,Long> {
    public List<Event> findEventByEventUser(User user);

    @Query("SELECT d FROM Event d Where d.archive=false AND d.eventUser.id=:id")
    List<Event> findEventByEventUserIdAndArchiveFalse(@Param("id") Long id);
   /* List<Event> findEventByArchiveFalseAndByEventUser(User user);*/

    @Query("SELECT d FROM Event d Where d.archive=false")
    List<Event> findEventByArchiveFalse();

    @Query("SELECT d FROM Event d Where d.archive=true")
    List<Event>findEventByArchiveTrue();

    @Query("SELECT d FROM Event d Where d.archive=true AND d.eventUser.id=:id")
     List<Event> findEventByArchiveTrueAndByEventUser(@Param("id") Long id);


}
