package net.rkr.onlineshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.rkr.onlineshopping.dao.CategoryDAO;
import net.rkr.onlineshopping.dao.ProductDAO;
import net.rkr.onlineshopping.dto.Category;
import net.rkr.onlineshopping.dto.Product;
import net.rkr.onlineshopping.exception.ProductNotFoundException;


@Controller
public class PageController {
	
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	
	@RequestMapping(value = {"/", "/home", "/index"})
	public ModelAndView   index(@RequestParam(name="logout",required=false)String logout) {		
		System.out.println("Inside Index Home................");
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("title","Home");
		
		//passing the list of categories
		mv.addObject("categories", categoryDAO.list());
		
		
		if(logout!=null) {
			mv.addObject("message", "You have successfully logged out!");			
		}
		
		mv.addObject("userClickHome",true);
		return mv;				
	}

	
	/*@RequestMapping(value = {"/test"})
	public ModelAndView requestParam(@RequestParam(name = "key", required=false)String key) {		
		ModelAndView mv = new ModelAndView("page");		
		if(key==null){
			key = "Hi Key is not there";
		}
		mv.addObject("key",key);
		return mv;				
	}
	
	@RequestMapping(value = {"/test1/{key}"})
	public ModelAndView pathVariable(@PathVariable("key")String key) {		
		ModelAndView mv = new ModelAndView("page");		
		if(key==null){
			key = "Hi Key is not there";
		}
		mv.addObject("key",key);
		return mv;				
	}*/
	@RequestMapping(value = "/about")
	public ModelAndView about() {		
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("title","About Us");
		mv.addObject("userClickAbout",true);
		return mv;				
	}	
	
	@RequestMapping(value = "/contact")
	public ModelAndView contact() {		
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("title","Contact Us");
		mv.addObject("userClickContact",true);
		return mv;				
	}
	
	/*
	 * Methods to load all the products and based on category
	 * */
	
	@RequestMapping(value = "/show/all/products")
	public ModelAndView showAllProducts() {		
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("title","All Products");
		
		//passing the list of categories
		mv.addObject("categories", categoryDAO.list());
		
		mv.addObject("userClickAllProducts",true);
		return mv;				
	}	
	
	@RequestMapping(value = "/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {		
		ModelAndView mv = new ModelAndView("page");
		
		// categoryDAO to fetch a single category
		Category category = null;
		
		category = categoryDAO.get(id);
		
		mv.addObject("title",category.getName());
		
		//passing the list of categories
		mv.addObject("categories", categoryDAO.list());
		
		// passing the single category object
		mv.addObject("category", category);
		
		mv.addObject("userClickCategoryProducts",true);
		return mv;				
	}	
	
	
	/*
	 * Viewing a single product
	 * */
	
	@RequestMapping(value = "/show/{id}/product") 
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException {
		
		ModelAndView mv = new ModelAndView("page");
		
		Product product = productDAO.get(id);
		
		if(product == null) throw new ProductNotFoundException();
		
		// update the view count
		product.setViews(product.getViews() + 1);
		productDAO.update(product);
		//---------------------------
		
		mv.addObject("title", product.getName());
		mv.addObject("product", product);
		
		mv.addObject("userClickShowProduct", true);
		
		
		return mv;
		
	}
}
