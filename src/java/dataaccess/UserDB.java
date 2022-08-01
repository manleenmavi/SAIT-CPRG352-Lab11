package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import models.User;


public class UserDB {
    public User get(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }
    
    public User updateUser(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction etrans = em.getTransaction();
        
        User afterUpdateUser = null;
        
        try {
            etrans.begin();
            afterUpdateUser = em.merge(user);
            etrans.commit();
        } catch (Exception e) {
            etrans.rollback();
        } finally {
            em.close();
        }
        
        return afterUpdateUser;
    }
    
    public User getUuidUser(String uuid) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        User uuidUser = null;
        
        try {
            Query query = em.createNamedQuery("User.findByResetPasswordUuid", User.class);
            query.setParameter("resetPasswordUuid", uuid);
            uuidUser = (User) query.getSingleResult();
        } catch (Exception e) {
            
        }finally {
            em.close();
        }
        
        return uuidUser;
    }
}
