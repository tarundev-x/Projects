package com.BootREST.RestApi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.BootREST.RestApi.dao.BookRepository;
import com.BootREST.RestApi.entity.Book;

@Component
public class Bookservice {

//	private static List<Book> list = new ArrayList<>();
//	//when class load first static variable loads thats why we are using static 
//	static {
//		list.add(new Book(12,"Java Reference","DEV"));
//		list.add(new Book(36,"Spring boot","TARUN"));
//		list.add(new Book(30,"Core Java","VIKI"));
//	}

	@Autowired
	private BookRepository bookRepository;

		// getting all the books
	public List<Book> getAllBooks()// for getting all the books
	{

		List<Book> list = (List<Book>) this.bookRepository.findAll();
		return list;
	}

		// getting book by id
	public Book getBookById(int id) {
		Book book = null;
		try {
			//book = list.stream().filter(ed->ed.getId()==id).findFirst().get();
			book = this.bookRepository.findById(id);
		} catch (Exception e) {
			e.printStackTrace();

			//Prints this throwable and its backtrace to thestandard error stream.

		}
		return book;
	}

		// for adding the book
	public Book addBook(Book b) 
	{
		//list.add(b);
		Book book = this.bookRepository.save(b);
		return book;
	}

//deleting book	by id
//	public void deleteBook(int id) 
//	{
////		list.stream().filter(e->e.getId()!=id).collect(Collectors.toList());
//
//		this.bookRepository.deleteById(id);
//
//	}

//	public void deleteBook(int id) 
//	{
//	    Optional<Book> bookOptional = Optional.of(bookRepository.findById(id));
//	    
//	    if (bookOptional.isPresent()) {
//	        bookRepository.deleteById(id);
//	    } 
//	}
	public void deleteBook(int id) 
	{
		try {
			bookRepository.deleteById(id);

			} 
		catch (Exception e) 
			{
			e.printStackTrace();
			}

	}

//uppdate book by id
	public void UpdateBook(Book book, int id) {

		Optional<Book> bookOptional = Optional.of(bookRepository.findById(id));

		if (bookOptional.isPresent()) {
			book.setId(id);

			bookRepository.save(book);
		}

	}
}
