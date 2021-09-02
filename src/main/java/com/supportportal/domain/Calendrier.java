package com.supportportal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
public class Calendrier implements Serializable{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        private String subject;
        private Date startTime;
        private Date endTime;

        public Calendrier() { }
        public Calendrier(long id, String title, Date startDate, Date endDate) {
            this.id=id;
            this.subject = title;
            this.startTime = startDate;
            this.endTime = endDate;
        }



        public long getId() {
            return this.id;
        }

        public void setId(long idEvent) {
            this.id = idEvent;
        }

        public String getSubject() {
            return this.subject;
        }

        public void setSubject(String title) {
            this.subject = title;
        }

        public Date getStartTime() {
            return this.startTime;
        }

        public void setStartTime(Date startDate) {
            this.startTime = startDate;
        }

        public Date getEndTime() {
            return this.endTime;
        }

        public void setEndTime(Date endDate) {
            this.endTime = endDate;
        }

}
