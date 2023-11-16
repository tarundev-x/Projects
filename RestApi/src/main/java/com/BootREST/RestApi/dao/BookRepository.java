package com.BootREST.RestApi.dao;

import org.springframework.data.repository.CrudRepository;

import com.BootREST.RestApi.entity.Book;

public interface BookRepository extends CrudRepository<Book,Integer>{
	public Book findById(int id);


}
