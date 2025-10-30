package com.urbanfleet.serviceImpl;

import com.urbanfleet.dto.ResidentResponse;
import com.urbanfleet.dto.VisitorRequest;
import com.urbanfleet.dto.VisitorResponse;
import com.urbanfleet.model.Resident;
import com.urbanfleet.model.Visitor;
import com.urbanfleet.repository.ResidentRepository;
import com.urbanfleet.repository.VisitorRepository;
import com.urbanfleet.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VisitorServiceImpl implements VisitorService
{
    @Autowired
    private VisitorRepository visitorRepository;
    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public String SaveVisitor(VisitorRequest visitorRequest)
    {

        Visitor visitor = new Visitor();
        visitor.setVisitorName(visitorRequest.getVisitorName());
        visitor.setVehicleName(visitorRequest.getVehicleName());
        visitor.setVRNum(visitorRequest.getVRNum());
        visitor.setVisitPurpose(visitorRequest.getVisitPurpose());
        visitor.setTimein(visitorRequest.getTimein());
        visitor.setTimeout(visitorRequest.getTimeout());
        visitor.setPhoneNumber(visitorRequest.getPhoneNumber());
        visitor.setActiveVisitor(visitorRequest.isActiveVisitor());
        visitor.setVisitortype(Visitor.VisitorType.valueOf(visitorRequest.getVisitorType().toUpperCase()));
        var resident  = residentRepository.findById(visitorRequest.getResidentId())
                        .orElseThrow(()-> new IllegalArgumentException("Resident not found"));
        visitor.setResident(resident);
        visitorRepository.save(visitor);
        return "Visitor Saved";
    }

    @Override
    public VisitorResponse getByvRNum(String vRnum)
    {
        VisitorResponse visitorResponse = visitorRepository.findByvRNum(vRnum).orElseThrow(() -> new IllegalArgumentException("Oops!! Resident not Found"));
//        VisitorResponse response = new VisitorResponse();
//        response.setVisitorName(visitor.getVisitorName());
//        response.setVisitortype(visitor.getVisitortype());
//        response.setVisitPurpose(visitor.getVisitPurpose());
//        response.setVRNum(visitor.getVRNum());
//        response.setTimein(visitor.getTimein());
//        response.setTimeout(visitor.getTimeout());
//        response.setVehicleName(visitor.getVehicleName());
//        response.setPhoneNumber(visitor.getPhoneNumber());
//        response.setActiveVisitor(visitor.isActiveVisitor());
//        response.setResident_id(visitor.getId());
//
//
//        Resident resident = visitor.getResident();
//        var residentResponse = new ResidentResponse();
//        residentResponse.setFname(resident.getFname());
//        residentResponse.setLname(resident.getLname());
//        residentResponse.setFlatno(resident.getFlatno());
//        residentResponse.setMobileno(resident.getMobileno());
//        residentResponse.setEmail(resident.getEmail());
//
//        response.setResidentResponse(residentResponse);

        return visitorResponse;
    }

    @Override
    public boolean updateExitTime(String vRnum, LocalDateTime timeout)
    {
        Optional<Visitor> visitorOpt = visitorRepository.findByvRNum(vRnum);

        if(visitorOpt.isPresent())
        {
            Visitor visitor = visitorOpt.get();
            visitor.setTimeout(timeout);
            visitor.setActiveVisitor(true);
            visitorRepository.save(visitor);
            return true;
        }
        return false;
    }


}
