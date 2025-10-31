package com.urbanfleet.service;

import com.urbanfleet.dto.VisitorRequest;
import com.urbanfleet.dto.VisitorResponse;
import com.urbanfleet.model.Visitor;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitorService
{
    String SaveVisitor(VisitorRequest visitorRequest);
    VisitorResponse getByvRNum(String vRnum);
    boolean updateExitTime(String vRnum, LocalDateTime timeout);
    List<Visitor> getVisitorType(List<String> types);
}
