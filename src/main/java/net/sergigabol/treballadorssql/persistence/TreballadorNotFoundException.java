/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.treballadorssql.persistence;

/**
 *
 * @author gabalca
 */
public class TreballadorNotFoundException extends Exception {

    private Long treballadorId;

    public TreballadorNotFoundException(Long treballadorId, String message) {
        super(message);
        this.treballadorId = treballadorId;
    }

    public TreballadorNotFoundException(Long treballadorId, String message, Throwable cause) {
        super(message, cause);
        this.treballadorId = treballadorId;
    }

    public Long getTreballadorId() {
        return treballadorId;
    }

}
