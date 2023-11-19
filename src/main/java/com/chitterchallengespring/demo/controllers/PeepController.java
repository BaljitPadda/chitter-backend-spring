package com.chitterchallengespring.demo.controllers;

import com.chitterchallengespring.demo.dto.PeepResponseDTO;
import com.chitterchallengespring.demo.model.Peep;
import com.chitterchallengespring.demo.services.PeepService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PeepController {

    private final PeepService peepService;

    @Autowired
    public PeepController(PeepService peepService){
        this.peepService = peepService;
    }

    @GetMapping(value="/peeps")
    @CrossOrigin
    public List<PeepResponseDTO> getAllPeeps() {
        return peepService.getAllPeeps();
    }

    @PostMapping(value="/addPeep")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public Peep addPeep(@Valid @RequestBody Peep peep){
        return peepService.addPeep(peep);
    }

    @PutMapping(value="/{id}")
    public Peep editPeep(@PathVariable String id, @Valid @RequestBody Peep peep) {
        return peepService.editPeep(id, peep);
    }

}

