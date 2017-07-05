package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	@NotBlank
	private String title;
	
	@Lob
	@NotBlank
	private String description;
	
	@Min(30)
	private int numberOfPages;
	
	@ElementCollection
	@Valid
	@NotEmpty
	private List<Price> prices = new ArrayList<Price>();
	
	@NotNull
	@DateTimeFormat
	private Calendar releaseDate;
	
	private String summaryPath;
	
	
	public String getSummaryPath() {
		return summaryPath;
	}
	public void setSummaryPath(String summaryPath) {
		this.summaryPath = summaryPath;
	}
	public Calendar getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}
	public List<Price> getPrices() {
		return prices;
	}
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getNumberOfPages() {
		return numberOfPages;
	}
	public void setNumberOfPages(int numerOfPages) {
		this.numberOfPages = numerOfPages;
	}
	
	@Override
	public String toString() {
		return "Product [title=" + title + ", description=" + description + ", numberOfPages=" + numberOfPages + "]";
	}
	
	public BigDecimal priceFor(BookType bookType){
		return prices.stream()
				.filter(price -> price.getBookType().equals(bookType)) //method reference
				.findFirst().get().getValue();
	}
		
}
