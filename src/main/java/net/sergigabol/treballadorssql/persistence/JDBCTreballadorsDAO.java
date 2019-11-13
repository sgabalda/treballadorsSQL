/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.treballadorssql.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sergigabol.treballadorssql.Treballador;

/**
 *
 * @author gabalca
 */
public class JDBCTreballadorsDAO implements TreballadorsDao {

    private JDBCConnectionSource conSource = new JDBCConnectionSource();

    @Override
    public Treballador findTreballador(Long id) throws TreballadorNotFoundException {
        String sql = "SELECT id, nom, title, department, salary, "
                + "salaryAfterTaxes FROM TREBALLADORS WHERE id = ?";

        try (Connection c = conSource.getConnection();
                PreparedStatement st = c.prepareStatement(sql)) {
            
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return getTreballadorFromRS(rs);
            }else{
                throw new TreballadorNotFoundException(id, "No s'ha obtingut cap fila al ResultSet");
            }

        } catch (SQLException ex) {
            Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenceException("SQLException en tornar el "
                    + "treballador amb id: " + id, ex);
        }

    }
    
    private Treballador getTreballadorFromRS(ResultSet rs) throws SQLException{
        Treballador t = new Treballador();
        t.setId(rs.getLong("id"));
        t.setName(rs.getString("nom"));
        t.setTitle(rs.getString("title"));
        //TODO que passa amb els departaments i els projects.
        t.setSalary(rs.getDouble("salary"));
        t.setSalaryAfterTaxes(rs.getDouble("salaryAfterTaxes"));
        return t;
    }

    @Override
    public List<Treballador> allTreballadors() {
        String sql = "SELECT id, nom, title, department, salary, "
                + "salaryAfterTaxes FROM TREBALLADORS";

        List<Treballador> result = new ArrayList<>();

        try (Connection c = conSource.getConnection();
                PreparedStatement st = c.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                result.add(getTreballadorFromRS(rs));
            }

            return result;

        } catch (SQLException ex) {
            Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenceException("SQLException en tornar tots els Treballadors", ex);
        }
    }

    @Override
    public void updateTreballador(Treballador t) throws TreballadorNotFoundException {
        String sql = "UPDATE TREBALLADORS set nom = ?, title = ?, department = ?,"
                + "salary = ?, salaryAfterTaxes = ? WHERE id = ?";
        try (Connection c = conSource.getConnection();
                PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, t.getName());
            st.setString(2, t.getTitle());
            //st.setString(3, t.getDepartment());
            st.setDouble(4, t.getSalary());
            st.setDouble(5, t.getSalaryAfterTaxes());
            st.setLong(6, t.getId());

            int inserted = st.executeUpdate();
            if (inserted != 1) {
                throw new PersistenceException("S'han modificat " + inserted + " registres "
                        + "en guardar un treballador: " + t);
            }
            //TODO: Si s'han modificat més de un Treballador, desfer la modificació
        } catch (SQLException ex) {
            Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenceException("SQLException en guardar Treballador: " + t, ex);
        }

        
    }

    @Override
    public void newTreballador(Treballador t) {
        String sql = "INSERT INTO TREBALLADORS (nom, title, department, "
                + "salary, salaryAfterTaxes) VALUES (?,?,?,?,?)";

        try (Connection c = conSource.getConnection();
                PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, t.getName());
            st.setString(2, t.getTitle());
            //st.setString(3, t.getDepartment());
            st.setDouble(4, t.getSalary());
            st.setDouble(5, t.getSalaryAfterTaxes());

            int inserted = st.executeUpdate();
            if (inserted != 1) {
                throw new PersistenceException("S'han modificat " + inserted + " registres "
                        + "en guardar un treballador: " + t);
            }
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next()){
                //podem saber quines columnes hi ha al resultset:
                ResultSetMetaData rsmd = rs.getMetaData();
                int columns = rsmd.getColumnCount();
                for(int i=1; i<=columns; i++){
                    System.out.println("La columna "+rsmd.getColumnName(i)
                            +" és de tipus "+rsmd.getColumnTypeName(i));
                }
                
                //podem accedir a la columna per al nou id amb l'index (pq només hi ha una columna
                t.setId(rs.getLong(1));
            }else{
                throw new PersistenceException("No puc obtenir el id del treballador afegit");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenceException("SQLException en guardar Treballador: " + t, ex);
        }
        
        /*
        SENSE TRY WITH RESOURCES
        
        Connection c=null;
        PreparedStatement st;
        try{
            c = DriverManager.getConnection(url, "sergi", "sergi");
            st = c.prepareStatement(sql);
            
        }catch(SQLException ex){
            //...
        }finally{
           if(c!=null){
               try{
                   c.close();
               }catch(SQLException ex){
                   //..
               }
           } 
        }
         */

    }

    @Override
    public void eliminaTreballador(Long id) throws TreballadorNotFoundException {
        String sql = "DELETE FROM TREBALLADOR WHERE id = ?";
        try (Connection c = conSource.getConnection();
                PreparedStatement st = c.prepareStatement(sql)) {

            st.setLong(1, id);

            int deleted = st.executeUpdate();
            if (deleted < 1) {
                throw new TreballadorNotFoundException(id, "No s'ha trobat el "
                        + "treballador amb id "+id+" per eliminar-lo");    
            }else if(deleted > 1){
                throw new PersistenceException("S'han eliminat " + deleted + " treballadorss ");
                //TODO: Si s'han modificat més de un Treballador, desfer la modificació
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenceException("SQLException en eliminar "
                    + "Treballador amb id: " + id, ex);
        }

    }

    @Override
    public void eliminaTreballadors(List<Long> ids) throws TreballadorNotFoundException {
        String sql = "DELETE FROM TREBALLADOR WHERE id = ?";
        try (Connection c = conSource.getConnection();
                PreparedStatement st = c.prepareStatement(sql)) {
            c.setAutoCommit(false);
            
            for(Long id:ids){
                st.setLong(1, id);
                try{
                    int deleted = st.executeUpdate();

                    if (deleted < 1) {
                        c.rollback();
                        throw new TreballadorNotFoundException(id, "No s'ha trobat el "
                                + "treballador amb id "+id+" per eliminar-lo");    
                        
                    }else if(deleted > 1){
                        c.rollback();
                        throw new PersistenceException("S'han eliminat " + deleted + " treballadorss ");
                    }
                } catch (SQLException ex) {
                    c.rollback();
                    Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
                    throw new PersistenceException("SQLException en eliminar "
                        + "El treballador amb id: " + id, ex);
                }
            }
            
            c.commit();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenceException("SQLException en eliminar "
                    + "Vàris treballadors: " + ids, ex);
        }
        
    }

    @Override
    public void eliminaGrupsTreballadors(List<List<Long>> idsGroups) {
        /*
        for(List<Long> ids : idsGroups){
            try {
                this.eliminaTreballadors(ids);
            } catch (Exception ex) {
                Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */
        
        String sql = "DELETE FROM TREBALLADOR WHERE id = ?";
        try (Connection c = conSource.getConnection();
                PreparedStatement st = c.prepareStatement(sql)) {
            c.setAutoCommit(false);
            
            for(List<Long> ids : idsGroups){
                Savepoint sp = c.setSavepoint();
                for(Long id:ids){
                    st.setLong(1, id);
                    try{
                        int deleted = st.executeUpdate();

                        if (deleted != 1) {
                            c.rollback(sp);
                            break;
                        }
                    } catch (SQLException ex) {
                        c.rollback(sp);
                        break;
                    }
                }            
            }
            c.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCTreballadorsDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenceException("SQLException en eliminar "
                    + "Vàris grups de treballadors: "+idsGroups, ex);
        }    
        
    }

}
