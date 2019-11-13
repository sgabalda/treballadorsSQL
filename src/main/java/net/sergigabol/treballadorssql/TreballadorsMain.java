/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.treballadorssql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sergigabol.treballadorssql.persistence.JDBCTreballadorsDAO;
import net.sergigabol.treballadorssql.persistence.TreballadorNotFoundException;
import net.sergigabol.treballadorssql.persistence.TreballadorsDao;

/**
 *
 * @author gabalca
 */
public class TreballadorsMain {
    
    public static void main(String ... args) throws TreballadorNotFoundException{
        
        TreballadorsDao treballadorsDao = new JDBCTreballadorsDAO();
        
        Departament d = new Departament();
        d.setName("Vendes");
        
        //guardem el departament
        Project p1 = new Project();
        p1.setName("Ampliació clients Europa");
        
        Project p2 = new Project();
        p2.setName("Consolidar mercat asiàtic");
        //guardem els projectes
        
        Treballador t = new Treballador();
        t.setName("Fulano De Tal3");
        t.setTitle("Becari");
        t.setDept(d);
        t.setSalary(1000.0);
        t.setSalaryAfterTaxes(900);
        
        List<Project> projectes = new ArrayList<>(Arrays.asList(p1,p2));
        t.setProjects(projectes);
        
        treballadorsDao.newTreballador(t);  //hem de fer que funcioni la setmana vinent!
 
        System.out.println("He guardat el treballador amb id: "+t.getId());
        
        
        
    }
    
}
