package com.urbanfleet.controller;

import com.urbanfleet.dto.ResidentRequest;
import com.urbanfleet.dto.ResidentResponse;
import com.urbanfleet.model.Resident;
import com.urbanfleet.service.ResidentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resident")
public class ResidentController
{
    @Autowired
    private ResidentService residentService;

    @PostMapping("/add")
    public ResponseEntity<String> addResident(@RequestBody ResidentRequest request)
    {
        try {
            residentService.saveResident(request);
            return new ResponseEntity<>("✅ Resident Saved Successfully!", HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e)
        {
            return new ResponseEntity<>("❌ " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllData")
    public ResponseEntity<List<Resident>> getData()
    {
        List<Resident> residentList = residentService.getAllResidentData();
        return new ResponseEntity<>(residentList,HttpStatus.OK);
    }
    @GetMapping("/getByName")
    public ResponseEntity<List<Resident>> getByName(@RequestParam(required = false) String fname, @RequestParam(required = false)  String lname)
    {
        List<Resident> residentbynamelist = residentService.getByName(fname, lname);
        return new  ResponseEntity<>(residentbynamelist, HttpStatus.OK);
    }

    @GetMapping("/getByVehicleRegNum/{registrationnumber}")
    public ResponseEntity<ResidentResponse> getByregistrationNumber(@PathVariable String registrationnumber)
    {
        ResidentResponse residentList = residentService.getByregistrationNumber(registrationnumber);
        return new ResponseEntity<>(residentList, HttpStatus.OK);
    }


}
