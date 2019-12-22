package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		Session currentsession = sessionFactory.getCurrentSession();
		Query<Customer> query = currentsession.createQuery("from Customer order by lastName",Customer.class);
		List<Customer> customers = query.getResultList();
		
		return customers;
	}

	@Override
	public void saveCustomer(Customer cust) {
		Session currentsession = sessionFactory.getCurrentSession();
		currentsession.saveOrUpdate(cust);		
	}

	@Override
	public Customer getCustomer(int id) {
		
		Session currentsession = sessionFactory.getCurrentSession();
		Customer cust = currentsession.get(Customer.class,id);
		return cust;
	}

	@Override
	public void deleteCustomer(int id) {
		Session currentsession = sessionFactory.getCurrentSession();
		Query q=currentsession.createQuery("delete from Customer where id=:customerId");
		q.setParameter("customerId",id);
		q.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		Session currentSession = sessionFactory.getCurrentSession();
		    
		Query theQuery = null;
		    
		    // only search by name if theSearchName is not empty
		if (theSearchName != null && theSearchName.trim().length() > 0) {
		
		    // search for firstName or lastName ... case insensitive
		    theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
		    theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		}
		else {
		    // theSearchName is empty ... so just get all customers
		    theQuery =currentSession.createQuery("from Customer", Customer.class);            
		}
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		        
		// return the results        
		return customers;
	}

}
