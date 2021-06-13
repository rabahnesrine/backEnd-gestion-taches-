package com.supportportal.repository;

import com.supportportal.domain.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint,Long> {
    public Sprint findSprintByNomSprint(String nomSprint);
    public Sprint findSprintByIdSprint(long idSprint);
}
