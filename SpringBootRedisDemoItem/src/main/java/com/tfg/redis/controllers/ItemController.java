package com.tfg.redis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.tfg.redis.model.Item;
import com.tfg.redis.model.ItemXML;
import com.tfg.redis.service.ItemCache;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemCache itemCache;

    @GetMapping("/item/{itemId}")
    @ResponseBody
    public ResponseEntity<Item> getItem(@PathVariable int itemId){
        Item item = itemCache.getItem(itemId);
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
    
    
   
 
     	@GetMapping(path="/itemxml/{itemId}",produces= {MediaType.APPLICATION_XML})
     	public ResponseEntity<Item>  getXmlItem(@PathVariable int itemId) { 
    		Item item = itemCache.getItem(itemId);
    	    return new ResponseEntity<Item>(item, HttpStatus.OK);
     		}
	
    
    
    
	  @GetMapping("/items")
	  @ResponseStatus(HttpStatus.OK)
	 public ResponseEntity<Object> getAll() {
		  ResponseEntity<Object> responseEntity =   itemCache.getAll();
			return responseEntity;
		  }
    

    @PostMapping(value = "/addItem",consumes = {"application/json"},produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Item> addItem(@RequestBody Item item, UriComponentsBuilder builder){
        itemCache.addItem(item);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addItem/{id}").buildAndExpand(item.getId()).toUri());
        return new ResponseEntity<Item>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/updateItem")
    @ResponseBody
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        if(item != null){
            itemCache.updateItem(item);
        }
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteItem(@PathVariable int id){
        itemCache.deleteItem(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}