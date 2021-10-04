/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.service;

import com.mine.authen.bean.OamUser;
import com.mine.authen.bean.OamUserRole;
import com.mine.util.HibernateUtil;
import com.mine.exception.AppException;
import com.mine.exception.SysException;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class UserManageServiceImpl implements UserManageService,
		Serializable {

	private static Logger logger = Logger.getLogger("UserManageServiceImpl.class");

	@Override
	public boolean insert(OamUser o) throws AppException, SysException {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(o);
			tx.commit();
			result = true;
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

	public static synchronized Object getNewId(String sequenceName)
			throws AppException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Object object = null;
		try {
			tx = session.beginTransaction();
			String sql = "select ".concat(sequenceName).concat(
					".nextval from dual");
			SQLQuery q = session.createSQLQuery(sql);
			List<Object> l = q.list();
			tx.commit();
			if (l.size() > 0) {
				object = l.get(0);
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();

			throw new AppException();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw new SysException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return object;
	}

	@Override
	public boolean update(OamUser o) throws AppException, SysException {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(o);

			tx.commit();
			result = true;
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
	public boolean delete(OamUser o) throws AppException, SysException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		boolean result = false;
		try {
			tx = session.beginTransaction();
			session.delete(o);

			tx.commit();
			result = true;
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
	public List<OamUser> list() throws AppException, SysException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<OamUser> ret = new ArrayList<OamUser>();

		try {
			tx = session.beginTransaction();
			String sql = "from OamUser order by userId desc";
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
	public OamUser findById(Long id) throws AppException, SysException {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			return (OamUser) session.get(OamUser.class, new Long(id));
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
	public List<OamUser> findByUserName(String userName) throws AppException,
            SysException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<OamUser> ret = new ArrayList<OamUser>();

		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(OamUser.class);
			criteria.add(Restrictions.eq("username", userName));
			criteria.addOrder(Order.asc("username"));
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
	public List<OamUserRole> findRoleByUserId(Long userId) throws AppException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<OamUserRole> ret = new ArrayList<OamUserRole>();

		try {
			tx = session.beginTransaction();

			String sql = "SELECT r FROM OamUserRole r, OamUserRoleMapping m where m.roleId=r.roleId AND m.userId="
					+ userId;
			Query query = session.createQuery(sql);

			ret = query.list();

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
	public void deleteRoleByUserId(Long userId) throws AppException,
            SysException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String sql = "DELETE FROM OAM_USER_ROLE_MAPPING WHERE USER_ID =:userId";
			SQLQuery query = session.createSQLQuery(sql);
			query.setLong("userId", userId);
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
