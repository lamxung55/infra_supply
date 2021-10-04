/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.service;


import com.mine.authen.bean.OamUserRoleMapping;
import com.mine.util.HibernateUtil;
import com.mine.exception.AppException;
import com.mine.exception.SysException;
import org.apache.log4j.Logger;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author havt4
 */
public class RoleUserMappingServiceImpl implements RoleUserMappingService {

    private static Logger logger = Logger.getLogger("RoleUserMappingServiceImpl.class");
    
    @Override
    public void insert(OamUserRoleMapping m) throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx=null;
       
            try{
                tx=session.beginTransaction();
                session.save(m);
                tx.commit();
            }catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                logger.error(e.getMessage(), e);
                throw new AppException();
            } catch (Throwable t) {
                if (tx != null) {
                    tx.rollback();
                }
                logger.error(t.getMessage(), t);
                throw new SysException();
            } finally {
                if (session != null) {
                    session.close();
                }
            }
    }

    @Override
    public void update(OamUserRoleMapping m) throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(m);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(e.getMessage(), e);
            throw new AppException();
        } catch (Throwable t) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(t.getMessage(), t);
            throw new SysException();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(OamUserRoleMapping m) throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(m);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(e.getMessage(), e);
            throw new AppException();
        } catch (Throwable t) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(t.getMessage(), t);
            throw new SysException();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<OamUserRoleMapping> list() throws AppException, SysException {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<OamUserRoleMapping> ret = new ArrayList<OamUserRoleMapping>();

        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(OamUserRoleMapping.class);
            ret = criteria.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(e.getMessage(), e);
            throw new AppException();
        } catch (Throwable t) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(t.getMessage(), t);
            throw new SysException();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return ret;
    }

    @Override
    public OamUserRoleMapping findById(Long id) throws AppException, SysException {
         Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            return (OamUserRoleMapping) session.get(OamUserRoleMapping.class, new Long(id));
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            throw new AppException();
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new SysException();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteByUserRole(Long userId, Integer roleId) throws AppException, SysException {
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "DELETE FROM OAM_USER_ROLE_MAPPING WHERE USER_ID =:userId AND ROLE_ID=:roleId";
            SQLQuery query=session.createSQLQuery(sql);
            query.setLong("userId", userId);
            query.setInteger("roleId", roleId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(e.getMessage(), e);
            throw new AppException();
        } catch (Throwable t) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(t.getMessage(), t);
            throw new SysException();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
}
