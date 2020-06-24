package com.ucareer.finalProject.LandingPage;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LandingService {

    final LandingRepository landingRepository;

    @Autowired
    LandingRepository repository;

    public LandingService(LandingRepository landingRepository) {
        this.landingRepository = landingRepository;
    }

    public Landing addOne(Landing landing){
        return repository.save(landing);
    }

    public List<Landing> getAll(){ return  repository.findAll();}

    void deleteById(Long id){this.landingRepository.deleteById(id);}

    public Landing getOneById(Long id){
        return repository.findById(id).orElse(null);
    }

    public Landing update (Long id, Landing landing){
        Landing foundOne = repository.getOne(id);
        if (foundOne !=null){
            if (landing.getName() != null && !landing.getName().isEmpty()){
                foundOne.setName(landing.getName());
            }
            return repository.save(foundOne);
        } else {
            return null;
        }
    }

}
