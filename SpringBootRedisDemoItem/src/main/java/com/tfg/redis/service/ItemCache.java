package com.tfg.redis.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import com.tfg.redis.model.Item;
import com.tfg.redis.repository.ItemRepository;

@Component
public class ItemCache {
	
	
    @Autowired
    ItemRepository itemRepo;

    @Cacheable(value="itemCache", key="#id")
    public Item getItem(int id){
        System.out.println("In getItem cache Component..");
        Item item = null;
        try{
            item = itemRepo.getItem(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return item;
    }
    
    
   
   public ResponseEntity<Object> getAll() {
	   ResponseEntity<Object> responseEntity =   itemRepo.getAll();
    	return responseEntity;
    }
    
    
    
    @CacheEvict(value="itemCache",key = "#id")
    public void deleteItem(int id){
        System.out.println("In deleteItem cache Component..");
        itemRepo.deleteItem(id);
    }

    @CachePut(value="itemCache",key = "#id")
    public void addItem(Item item){
        System.out.println("In addItem cache component..");
        itemRepo.addItem(item);
    }

    @CachePut(value="itemCache",key = "#id",condition = "#result != null")
    public void updateItem(Item item){
        System.out.println("In UpdateItem cache Component..");
        itemRepo.updateItem(item);
    }
}