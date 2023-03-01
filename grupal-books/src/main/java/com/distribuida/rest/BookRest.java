package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.db.BookDto;
import com.distribuida.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.distribuida.Autor;

import java.sql.SQLOutput;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookRest {
BookRepository bookRepository;
String authorProxy = System.getenv("AUTHOR_PROXY");
RestTemplate restTemplate = new RestTemplate();

public BookRest(BookRepository bookRepository) {
	this.bookRepository = bookRepository;
}

@PostMapping("")
public  ResponseEntity<?> insert(@RequestBody Book book) {
	System.out.printf("Insertando libros");
	Book bookEntity = new Book();
	bookEntity.setTitle(book.getTitle());
	bookEntity.setAuthor(book.getAuthor());
	bookEntity.setPrice(book.getPrice());
	bookEntity.setIsbn(book.getIsbn());
	bookRepository.save(bookEntity);
	return new ResponseEntity("Success", HttpStatus.OK);
}


@GetMapping("")
  public List<BookDto> findAll() {
		var books = bookRepository.findAll();
		if (books.isEmpty()) {
			return null;
		}
		List <BookDto> booksDto = books.stream().map(book -> {
			
			Autor author = null;
			try {
				System.out.println(authorProxy+"/"+book.getAuthor());
				author = restTemplate.getForObject(authorProxy + "/" +book.getAuthor(), Autor.class);
			} catch (Exception e) {
				System.out.println("Error"+ e.getMessage()+e);
				author = null;
			}
			System.out.println(author);
			String authorName = "";
			
			if (author != null) {
				authorName = author.getFirstname() + " " + author.getLastname();
			} else {
				authorName = "Sin nombre de autor";
			}
			BookDto bookDto = new BookDto();
			bookDto.setId(book.getId());
			bookDto.setIsbn(book.getIsbn());
			bookDto.setTitle(book.getTitle());
			bookDto.setAuthor(book.getAuthor());
			bookDto.setAuthorName(authorName);
			bookDto.setPrice(book.getPrice());
			return bookDto;
		}).collect(Collectors.toList());
		if (booksDto.isEmpty()) {
			return Collections.EMPTY_LIST;
		} else {
			return booksDto;
		}
		
	}
@GetMapping("/{id}")
	public <Optional>BookDto findById(@PathVariable("id") String id) {
	
		var resp = bookRepository.findById(id);
		if (resp.isPresent()) {
			Autor author = null;
			try {
				author = restTemplate.getForObject(authorProxy + "/" + resp.get().getAuthor(), Autor.class);
			} catch (Exception e) {
				System.out.println("Error"+ e.getMessage()+e);
				author = null;
			}
			System.out.println(author);
			String authorName = "";
			if (author != null) {
				authorName = author.getFirstname() + " " + author.getLastname();
			} else {
				authorName = "Sin nombre de autor";
			}
			return new BookDto(resp.get().getId(), resp.get().getIsbn(), resp.get().getTitle(), resp.get().getAuthor(), resp.get().getPrice(), authorName);
		} else {
			return null;
		}
	}
	@PutMapping("")
	public void update(Book book) {
		bookRepository.save(book);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		bookRepository.deleteById(id);
	}

}
