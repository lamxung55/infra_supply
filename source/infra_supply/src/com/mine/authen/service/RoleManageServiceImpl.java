/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.service;

import com.mine.authen.bean.OamUserRole;
import com.mine.exception.AppException;
import com.mine.exception.SysException;
import com.mine.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author havt4
 */
@Service(value = "roleService")
public class RoleManageServiceImpl implements RoleManageService, Serializable{

    private static Logger logger = Logger.getLogger("RoleManageServiceImpl.class");
   
    @Override
    public void insert(OamUserRole r) throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx=null;
       
            try{
                tx=session.beginTransaction();
                session.save(r);
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
    public void update(OamUserRole r) throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(r);

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
    public boolean delete(OamUserRole r) throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean result=false;
        try {
            tx = session.beginTransaction();
            session.delete(r);

            tx.commit();
            result=true;
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
        return result;
    }

    @Override
    public List<OamUserRole> list() throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<OamUserRole> ret = new ArrayList<OamUserRole>();

        try {
            tx = session.beginTransaction();
            String sql = "from OamUserRole order by roleId desc";
            Query query = session.createQuery(sql);
            ret = query.list();

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
    public OamUserRole findById(Integer id) throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            return (OamUserRole) session.get(OamUserRole.class, new Integer(id));
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
    public List<OamUserRole> findByRoleName(String roleName) throws AppException, SysException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<OamUserRole> ret = new ArrayList<OamUserRole>();

        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(OamUserRole.class);
            criteria.add(Restrictions.eq("roleName", roleName));
            criteria.addOrder(Order.asc("roleName"));
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
    public void deleteRoleByRoleId(Integer roleId) throws AppException, SysException {
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "DELETE FROM OAM_USER_ROLE_MAPPING WHERE ROLE_ID =:roleId";
            SQLQuery query=session.createSQLQuery(sql);
            query.setLong("roleId", roleId);
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
