package com.ucareer.finalProject.menusItems;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenusItemsService {

    final MenusItemRepository menusItemRepository;

    @Autowired
    MenusItemRepository repository;

    public MenusItemsService(MenusItemRepository menusItemRepository) {
        this.menusItemRepository = menusItemRepository;
    }

    public MenusItem addOne(MenusItem item){
        return repository.save(item);
    }

    public List<MenusItem> getAll(){
        return repository.findAll();
    }

    void deleteById(Long id){this.menusItemRepository.deleteById(id);}

    public MenusItem getOneById(Long id){
        return repository.findById(id).orElse(null);
    }

    public MenusItem update (Long id, MenusItem item){
        MenusItem foundOne = repository.getOne(id);
        if (foundOne !=null){
            if (item.getDescription() != null && !item.getDescription().isEmpty()){
                foundOne.setDescription(item.getDescription());
            }
            if (item.getImg_url() !=null && !item.getImg_url().isEmpty()){
                foundOne.setImg_url(item.getImg_url());
            }
            if (item.getName() != null && !item.getName().isEmpty()){
                foundOne.setName(item.getName());
            }
            if (item.getCategory() != null && !item.getCategory().isEmpty()){
                foundOne.setCategory(item.getCategory());
            }
            if (item.getPrice() >= 0 ){
                foundOne.setPrice(item.getPrice());
            }
            return repository.save(foundOne);
        } else {
            return null;
        }
    }

}
