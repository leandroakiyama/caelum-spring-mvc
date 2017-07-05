package br.com.casadocodigo.loja.controllers;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.models.ShoppingItem;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@RequestMapping(method=RequestMethod.POST)
	public String add(Integer productId, @RequestParam BookType bookType, Model model){
		ShoppingItem item = createItem(productId, bookType);
		shoppingCart.add(item);
		return "redirect:/products";
	}

	private ShoppingItem createItem(Integer productId, BookType bookType) {
		Product product = productDAO.find(productId);
		ShoppingItem item = new ShoppingItem(product, bookType);
		return item;
	}
	
	
}
