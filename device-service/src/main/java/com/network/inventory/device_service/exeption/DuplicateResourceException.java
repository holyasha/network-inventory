package com.network.inventory.device_service.exeption;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message) {
        super(message);
    }
    
}
