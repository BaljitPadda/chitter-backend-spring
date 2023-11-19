package com.chitterchallengespring.demo.services;

import com.chitterchallengespring.demo.dto.PeepResponseDTO;
import com.chitterchallengespring.demo.model.Peep;
import com.chitterchallengespring.demo.model.User;
import com.chitterchallengespring.demo.repositories.PeepRepository;
import com.chitterchallengespring.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeepService {

    @Autowired
    private PeepRepository peepRepository;
    @Autowired
    private UserRepository userRepository;

    public List<PeepResponseDTO> getAllPeeps() {

        List<Peep> allPeeps = peepRepository.findAll();
        List<User> allUsers = userRepository.findAll();

        return mapping(allUsers, allPeeps);
    }

    public Peep addPeep(Peep peep) {
        return peepRepository.save(peep);
    }

    public Peep editPeep(String id, Peep peep) {
        if(!peepRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That peep can't be found.");
        return peepRepository.save(peep);
    }

    private PeepResponseDTO peepToDTO(User user, Peep peep) {

        PeepResponseDTO peepResponseDTO = new PeepResponseDTO();

        peepResponseDTO.setName(user.getName());
        peepResponseDTO.setUserID(peep.getUserID());
        peepResponseDTO.setTime(peep.getTime());
        peepResponseDTO.setMessage(peep.getMessage());

        return peepResponseDTO;
    }

    private List<PeepResponseDTO> mapping (List<User> allUsers, List<Peep> allPeeps) {

        List<PeepResponseDTO> finalList = new ArrayList<>();

        for (Peep peep : allPeeps) {
            for(User user: allUsers) {

               if(user.getUsername().equals(peep.getUserID())){
                   finalList.add(peepToDTO(user, peep));
               }
            }
        }

       return finalList;
    }


}