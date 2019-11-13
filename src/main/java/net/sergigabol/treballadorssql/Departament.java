/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.treballadorssql;

import java.util.List;

/**
 *
 * @author gabalca
 */
public class Departament {
    
    private Long id;
    private String name;
    private List<Treballador> treballadors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Treballador> getTreballadors() {
        return treballadors;
    }

    public void setTreballadors(List<Treballador> treballadors) {
        this.treballadors = treballadors;
    }
    
    
    @Override
    public String toString() {
        return "Departament{" + "id=" + id + ", name=" + name + ", treballadors=" + treballadors + '}';
    }
    
}
