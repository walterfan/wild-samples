package com.github.walterfan.account.service;

import com.github.walterfan.account.dao.LinkDao;
import com.github.walterfan.account.domain.Category;
import com.github.walterfan.account.domain.Link;

import java.util.List;

public class LinkService {
		 
	    
	    private LinkDao linkDao;
	    
	    
	 
	    public LinkDao getLinkDao() {
			return linkDao;
		}

		public void setLinkDao(LinkDao linkDao) {
			this.linkDao = linkDao;
		}

		public List<Link> selectAllLink(){
	        return linkDao.selectAllLink();
	    }
	 
	    public Link selectLink(int id){
	        return linkDao.selectLink(id);
	    }
	 
	    public int insertLink(Link parent){
	        return linkDao.insertLink(parent);
	    }
	 
	    public List<Category> selectAllCategories(int parnetId){
	        return linkDao.selectAllCategories(parnetId);
	    }
	 
	    public Category selectCategory(int id){
	        return linkDao.selectCategory(id);
	    }
	 
	    public int insertCategory(Category child){
	        return linkDao.insertCategory(child);
	    }
	}
