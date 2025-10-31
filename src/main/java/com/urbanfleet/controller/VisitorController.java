package com.urbanfleet.controller;

import com.urbanfleet.dto.VehicleResponse;
import com.urbanfleet.dto.VisitorRequest;

import com.urbanfleet.dto.VisitorResponse;
import com.urbanfleet.model.Visitor;
import com.urbanfleet.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Visitor")
public class VisitorController
{
    @Autowired
    private VisitorService visitorService;

    @PostMapping("/addvisitor")
    private ResponseEntity<String> addVisitor(@RequestBody VisitorRequest visitorRequest)
    {
        visitorService.SaveVisitor(visitorRequest);
        return new ResponseEntity<>("Visitor Saved", HttpStatus.CREATED);
    }

    @GetMapping("/getByVisitorVehicleRegisterNum/{vRnum}")
    public ResponseEntity<VisitorResponse> getByvRNum(@PathVariable String vRnum)
    {
        VisitorResponse visitorResponse=visitorService.getByvRNum(vRnum);
        return new ResponseEntity<>(visitorResponse, HttpStatus.OK);
    }

    @PatchMapping("/getexitTime/{vRNum}")
    public ResponseEntity<String> updateExitTime(@PathVariable String vRNum, @RequestBody Map<String, String> body)
    {
        String timeoutString = body.get("timeout");
        LocalDateTime timeout = LocalDateTime.parse(timeoutString);
        boolean updated = visitorService.updateExitTime(vRNum, timeout);

        if(updated)
        {
            return new ResponseEntity<>("ExitTime Updated Succesffully", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Visitor didn't exit", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getVisitorType/{VisitorType}")
    public ResponseEntity<List<Visitor>> getVisitorType(@RequestParam(required = false) List<String> types)
    {
        List<Visitor> visitor = visitorService.getVisitorType(types);
        return new ResponseEntity<>(visitor, HttpStatus.OK);
    }
}
