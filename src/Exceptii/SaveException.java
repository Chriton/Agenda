/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptii;

/**
 * @author dorumuntean
 */
public class SaveException extends RuntimeException {
    private boolean filePermission;

    public SaveException() {
        filePermission = false;
    }

    public boolean getFilePermission() {
        return filePermission;
    }
}
