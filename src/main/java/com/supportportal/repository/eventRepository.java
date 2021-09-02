package com.supportportal.repository;

import com.supportportal.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eventRepository  extends JpaRepository<Event,Long> {
}
