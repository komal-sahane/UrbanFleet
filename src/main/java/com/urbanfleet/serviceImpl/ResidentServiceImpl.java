package com.urbanfleet.serviceImpl;

import com.urbanfleet.dto.ResidentRequest;
import com.urbanfleet.dto.ResidentResponse;
import com.urbanfleet.model.Resident;
import com.urbanfleet.model.Vehicle;
import com.urbanfleet.model.Visitor;
import com.urbanfleet.repository.ResidentRepository;
import com.urbanfleet.repository.VehicleRepository;
import com.urbanfleet.repository.VisitorRepository;
import com.urbanfleet.service.ResidentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class ResidentServiceImpl implements ResidentService
{
    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Override
    public String saveResident(ResidentRequest request)
    {
        Resident resident = new Resident();
        resident.setFname(request.getFname());
        resident.setLname(request.getLname());
        resident.setFlatno(request.getFlatno());
        resident.setMobileno(request.getMobileno());
        resident.setEmail(request.getEmail());
        resident.setRtype(Resident.RType.valueOf(request.getRtype().toUpperCase()));

        residentRepository.save(resident);
        return "Resident Saved";
    }

    @Override
    public List<Resident> getAllResidentData()
    {
        List<Resident> residentList= residentRepository.findAll();
        return residentList;
    }

    @Override
    public List<Resident> getByName(String fname, String lname)
    {
        List<Resident> result;
        if((fname == null || fname.isEmpty())  && (lname == null || lname.isEmpty()))
        {
            throw new IllegalArgumentException("Hey Sherlock! At least tell me a fname or lname to search!");
        }
        else if(fname!=null || !fname.isEmpty() && fname.matches("^[A-Za-z]+$") && (lname!=null || !lname.isEmpty())&& lname.matches("^[A-Za-z]+$"))
        {
            result= residentRepository.findByFnameIgnoreCaseOrLnameIgnoreCase(fname, lname);
        }
        else if (fname!=null || !fname.isEmpty() && fname.matches("^[A-Za-z]+$") && (lname==null || lname.isEmpty()))
        {
            result = residentRepository.findByFnameIgnoreCase(fname);
        }
        else
        {
            result = residentRepository.findByLnameIgnoreCase(lname);
        }
        if (result == null || result.isEmpty())
        {
            throw new IllegalArgumentException("🙃 Oops! No resident found with that name.");

        }
        return result;
    }

    @Override
    public ResidentResponse getByregistrationNumber(String registrationnumber)
    {
        Resident resident = vehicleRepository.findByregistrationNumber(registrationnumber)
                .map(Vehicle::getResident)
                .orElseThrow(() -> new IllegalArgumentException("Oops no Resident Found!!"));
        return new ResidentResponse(resident);
    }

//    @Override
//    public ResidentResponse getByvRNum(String vRnum)
//    {
//        Resident resident = visitorRepository.findByvRNum(vRnum)
//                .map(Visitor::getResident)
//                .orElseThrow(()-> new IllegalArgumentException("Oops!! No Resident Found"));
//        return new ResidentResponse(resident);
//    }



}
