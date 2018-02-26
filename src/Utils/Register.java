/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author dorumuntean
 */
public class Register implements Serializable {
    
    private static final String REGISTRATION_FILE = "src%sregister.reg"; 
    private static final String registrationCode = "test";
    private final String registered;

    public Register(boolean register) {
        registered = register + registrationCode;  
    }
    
    @Override
    public String toString() {
        return registered;   
    }
    
    
    public static String getRegistrationCode() {
        return registrationCode;
    }
    
    private static boolean existaFisierRegister() {
        File fisierRegister = new File(String.format(REGISTRATION_FILE, File.separator));
        return !(!fisierRegister.exists() || !fisierRegister.canRead());
    }
    
    public static boolean isRegistered() {
        
        if (!existaFisierRegister()) {
            return false;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(String.format(REGISTRATION_FILE, File.separator)));) {
          
            Register registration = (Register) ois.readObject();
            if (new Register(true).toString().equals(registration.toString())) {   
                return true;
            }
         
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        
        return false;
    }

    public static void register() throws IOException {
        try {

            if (isRegistered()) {
                return;
            }
            
            File fisierRegister = new File(String.format(REGISTRATION_FILE, File.separator));
            
            //daca nu putem sa citim fisierul de inregistrare, incercam sa il stergem si sa il scriem din nou
            if (fisierRegister.exists() && !fisierRegister.canWrite()) {
                if (!fisierRegister.delete()) {
                   throw new IOException("Fisierul de inregistrare corupt nu poate fi sters! Nu exista permisiuni de scriere."); 
                }
            }
            
            try (FileOutputStream fos = new FileOutputStream(fisierRegister); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(new Register(true));
            } 
            
            } catch (IOException e) {
                throw new IOException("Fisierul de inregistrare nu poate fi creat!");
            }
    }    
}