package com.tfg.redis.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.tfg.redis.model.Item;
import com.tfg.redis.model.ItemXML;

import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    public static final String KEY = "ITEMKEY";
    private RedisTemplate<String, Item> redisTemplate;
    private HashOperations hashOperations;

    public ItemRepository(RedisTemplate<String, Item> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    /*Getting a specific item by item id from table*/
    public Item getItem(int itemId){
        return (Item) hashOperations.get(KEY,itemId);
    }
    
    
   
    
    
    
	/*
	 * public List<Item> getAll() {
	 * 
	 * return (items) redisTemplate.opsForHash().entries(KEY), HttpStatus.OK); }
	 */
	 
    
	
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(redisTemplate.opsForHash().entries(KEY), HttpStatus.OK);
	}
    

    /*Adding an item into redis database*/
    public void addItem(Item item){
        hashOperations.put(KEY,item.getId(),item);
    }
    /*delete an item from database*/
    public void deleteItem(int id){
        hashOperations.delete(KEY,id);
    }

    /*update an item from database*/
    public void updateItem(Item item){
        addItem(item);
    }
}