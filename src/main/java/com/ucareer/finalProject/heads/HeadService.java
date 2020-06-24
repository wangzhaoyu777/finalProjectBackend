package com.ucareer.finalProject.heads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadService {

    final HeadRepository headRepository;

    @Autowired
    HeadRepository repository;

    public HeadService(HeadRepository headRepository) {
        this.headRepository = headRepository;
    }

    public Head addOne(Head head){
        return repository.save(head);
    }

    public List<Head> getAll(){
        return repository.findAll();
    }

    void deleteById(Long id){this.headRepository.deleteById(id);}

    public Head getOneById(Long id){
        return repository.findById(id).orElse(null);
    }

    public Head update (Long id, Head head){
        Head foundOne = repository.getOne(id);
        if (foundOne !=null){
            if (head.getDescription() != null && !head.getDescription().isEmpty()){
                foundOne.setDescription(head.getDescription());
            }
            if (head.getImg_url() !=null && !head.getImg_url().isEmpty()){
                foundOne.setImg_url(head.getImg_url());
            }
            if (head.getTitle() != null && !head.getTitle().isEmpty()){
                foundOne.setTitle(head.getTitle());
            }
            return repository.save(foundOne);
        } else {
            return null;
        }
    }


}
