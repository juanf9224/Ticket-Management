/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ticketmanagement.dao.impl;

import static com.mycompany.ticketmanagement.beans.LoginBean.USER_SESSION_KEY;
import com.mycompany.ticketmanagement.dao.LoginDAO;
import com.mycompany.ticketmanagement.entities.LoginInfo;
import com.mycompany.ticketmanagement.servicebeans.LoginServiceBean;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author juanf_000
 */
public class LoginDAOImpl implements LoginDAO{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_TicketManagement_war_1.0-SNAPSHOTPU");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    

    
    @Override
    public LoginInfo create(LoginInfo t) {
       
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LoginInfo retreive(LoginInfo t) {
        
        try{
        
            
           LoginInfo login = em.createNamedQuery("LoginInfo.findByUsernamePwdEmail", LoginInfo.class).setParameter("username", t.getUsername()).setParameter("pwd", t.getPwd()).getSingleResult();
                            
                        
              return login;
          
        
        }catch(NoResultException nre){
            nre.toString();
            return null;
        }
    }
    
    public String validateUser(LoginInfo li, String username, String pwd) {   
        FacesContext context = FacesContext.getCurrentInstance();
        LoginInfo login = retreive(li);
        
        if (login != null) {
            if (!login.getUsername().equals(username) && !login.getPwd().equals(pwd)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                           "Login Failed!",
                                           "Username or  password specified is not correct.");
                context.addMessage(null, message);
                return null;
            }
            
            
            if(login.getRole().equals("admin")){
                context.getExternalContext().getSessionMap().put(USER_SESSION_KEY, login);
                //String outcome = "admin-app-main";
            return "admin-app-main";
            }else{
            context.getExternalContext().getSessionMap().put(USER_SESSION_KEY, login);
            //String outcome = "app-main";
            return "app-main";
            }
        } else {           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Failed!",
                    "Username or password specified is not correct.");
            context.addMessage(null, message);
            return null;
        }
    }
    
   /* public String outcome(String outcome){
        
        if(outcome.equals("admin-app-main")){
            return lsb.outcomeAdmin();
        }else{
            return lsb.outcomeUser();
                }
    }*/
    
    @Override
    public LoginInfo update(LoginInfo t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LoginInfo delete(LoginInfo t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<LoginInfo> findAll() {
       
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
