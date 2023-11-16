package com.BootREST.RestApi.controller;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.BootREST.RestApi.entity.Book;
import com.BootREST.RestApi.service.Bookservice;

@RestController
public class Restcontroller 
{
	@Autowired
	private Bookservice bookservice;
	
	
	
//	@RequestMapping(value = "/book",method = RequestMethod.GET)
	
	@GetMapping("/book")//for get request both will work  same
	public ResponseEntity<List<Book>> getBooks()
	{

		List<Book> list = this.bookservice.getAllBooks();
		
		if(list.size()<=0)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(list);
	}
	
	
	
	
	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id")int id)
	{
		Book book= bookservice.getBookById(id);
		if(book==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		//return ResponseEntity.status(HttpStatus.OK).body(book);
		return ResponseEntity.of(Optional.of(book));
	}
	
	
	
	
	//new book handler
	@PostMapping("/book")
	public ResponseEntity<Book> addBook(@RequestBody Book book)
	{
		
		try{
		Book b=this.bookservice.addBook(book);
		//requestbody-bookdata is stored in b  
		return ResponseEntity.status(HttpStatus.CREATED).body(b);
		//.body(b) se
		//return ResponseEntity.of(Optional.of(b));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") int id)
	{
		try{
			this.bookservice.deleteBook(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}


	
	
	
	//update book handler
	@PutMapping("book/{id}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book,@PathVariable("id") int id)
	{
		
		try{
			this.bookservice.UpdateBook(book,id);
			return ResponseEntity.ok().body(book);
		}catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}
