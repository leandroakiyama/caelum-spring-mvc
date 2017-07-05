package br.com.casadocodigo.loja.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private FileSaver fileSaver;
	
	@RequestMapping("/form")
	public String form(Product product, Model model){
		//ModelAndView modelAndView = new ModelAndView("products/form");
		model.addAttribute("types", BookType.values());
		return "products/form";
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST)
	public String save(MultipartFile summary, @Valid Product product, BindingResult bindingResult, RedirectAttributes attributes, Model model ){
		//Validação
		if (bindingResult.hasErrors()){
			return form(product, model);
		}
		
		//Upload de arquivos
		String webPath = fileSaver.write("uploaded-summaries", summary);
		product.setSummaryPath(webPath);
		
		//Submissão de dados
		productDAO.save(product);
		
		//Mantendo dados da requisição 
		attributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
		return "redirect:products";
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView modelAndView = new ModelAndView("products/list");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public String show(@PathVariable Integer id, Model model){
		Product product = productDAO.find(id);
		model.addAttribute("product", product);
		return "products/show";
	}
	
	/*
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new ProductValidator());
	} 
	*/
	
}
