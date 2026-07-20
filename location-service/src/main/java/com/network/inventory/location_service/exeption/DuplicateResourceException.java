package com.network.inventory.location_service.exeption;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message) {
        super(message);
    }
    
}
