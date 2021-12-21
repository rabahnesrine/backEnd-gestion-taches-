package com.supportportal.resource;

import com.supportportal.domain.*;
import com.supportportal.repository.CalendrierRepository;
import com.supportportal.repository.UserRepository;
import com.supportportal.repository.eventRepository;
import com.supportportal.service.*;
import com.supportportal.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
@RestController
@RequestMapping(path = { "/event"})
public class EventResource {
    private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

    private eventRepository eventRepository;

    private UserRepository userRepository ;
   private UserService userService;
   private eventService eventService;


    @Autowired
    public EventResource( UserService userService,eventService eventService,eventRepository eventRepository,UserRepository userRepository ) {
       this.eventRepository=eventRepository;
        this.userService = userService;
        this.userRepository=userRepository;
        this.eventService=eventService;

    }

    @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        List<Long> listinvitedTo=event.getInvitedPersons();
        List<Long> listinvitedToFiltre = new ArrayList<>();

        int occ=0;

        for (Long idpers : listinvitedTo) {
            for (Long pers : listinvitedTo) {
                if (idpers == pers) {
                    occ++;
                }
            }
            if(listinvitedToFiltre.indexOf(idpers)==-1 ) {
                listinvitedToFiltre.add(idpers);
            }
            occ=0;
              }

LOGGER.info("add liste users|||||||||||"+listinvitedToFiltre);


        event.setDateCreation(new Date());
        event.setArchive(false);
        event.setEtatEvent("Confirmer");
        event.setInvitedPersons(listinvitedToFiltre);
        Event e=eventRepository.save(event);
        return new ResponseEntity<>(e, OK);
    }
    @GetMapping("/getEvent/{id}")
    public ResponseEntity<List<Event>> getEvent(@PathVariable("id") Long id) {
        User user=userRepository.findUserById(id);
        List<Event> listEvent=eventRepository.findEventByArchiveFalse();
        List<Event> listEventFiltre = new ArrayList<>();


        for (Event event : listEvent) {
            if (event.getInvitedPersons().indexOf(id)!=-1)  {
                listEventFiltre.add(event);
                // break;  return filteredListSprint.size();
            }
        }


       /* listEventFiltre=listEvent.stream()
                .filter(e->e.getInvitedPersons().contains(user.getId()))
                .collect(Collectors.toList());*/
        return new ResponseEntity<>(listEventFiltre, OK);
    }




    @GetMapping("/getAllEvent")
    public ResponseEntity<List<Event>> getAllEvent() {
        List<Event> listEvent=eventRepository.findEventByArchiveFalse();

LOGGER.info(listEvent.toString());

        return new ResponseEntity<>(listEvent, OK);
    }







    @PutMapping("/evtEdit/{id}")
    public Event updateEvt(@PathVariable ("id") Long id,@RequestBody Event event) {
        event.setId(id);
        return this.eventService.updateEvent(event);
    }

   /* @GetMapping("/getEventcreated/{id}")
    public ResponseEntity<List<Event>> getEventcreated(@PathVariable("id") Long id) {
        User user=userRepository.findUserById(id);
        List<Event> listEvent=eventService.findEventByEventCreePar(user);

        return new ResponseEntity<>(listEvent, OK);
    }*/

    @GetMapping("/eventCreateur/{id}")
    public List<Event> findEventByEventUser(@PathVariable("id") Long id) {
        return this.eventService.findEventByEventUser(id);
    }


    @GetMapping("/eventArchivebyCreateur/{id}")
    public List<Event> findEventArchiveByEventUser(@PathVariable("id") Long id) {
        return this.eventService.findEventArchiveByEventUser(id);
    }



    @GetMapping("/archives")
    public List<Event> getAllArchivedEvent() {
        return eventService.findEventByArchiveTrue();
    }

    @DeleteMapping("/deleteEvt/{id}")
    public void deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);

    }

    @PutMapping("/archive/{id}")
    public Event archiveEvent(@PathVariable("id") Long id, @RequestBody Event event) {
        event.setId(id);
        event.setArchive(true);
        return eventService.updateEvent(event);
    }

    @PutMapping("/restaurer/{id}")
    public Event restaurerEvent(@PathVariable("id") Long id, @RequestBody Event event) {
        event.setId(id);
        event.setArchive(false);
        return eventService.updateEvent(event);
    }

}
