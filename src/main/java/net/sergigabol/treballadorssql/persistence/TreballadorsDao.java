/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.treballadorssql.persistence;

import java.util.List;
import net.sergigabol.treballadorssql.Treballador;

/**
 *
 * @author gabalca
 */
public interface TreballadorsDao {
    
    public Treballador findTreballador(Long id) throws TreballadorNotFoundException;
    public List<Treballador> allTreballadors();
    public void updateTreballador(Treballador t) throws TreballadorNotFoundException;
    public void newTreballador(Treballador t);
    public void eliminaTreballador(Long id) throws TreballadorNotFoundException;
    public void eliminaTreballadors(List<Long> ids) throws TreballadorNotFoundException;
    
    public void eliminaGrupsTreballadors(List<List<Long>> ids);
    
}
