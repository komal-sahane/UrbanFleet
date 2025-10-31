package com.urbanfleet.repository;


import com.urbanfleet.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Integer>
{
    Optional<Visitor> findByvRNum(String vRnum);
    List<Visitor> findByvisitortypeIn(List<String> types);


}
