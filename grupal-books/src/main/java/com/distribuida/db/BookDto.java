package com.distribuida.db;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

public class BookDto {
private String id;
private String isbn;
private String title;
private Integer author;
private Double price;

private String authorName;

public BookDto() {
}
public BookDto(String id, String isbn, String title, Integer author, Double price, String authorName) {
	this.id = id;
	this.isbn = isbn;
	this.title = title;
	this.author = author;
	this.price = price;
	this.authorName = authorName;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getIsbn() {
	return isbn;
}
public void setIsbn(String isbn) {
	this.isbn = isbn;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public Integer getAuthor() {
	return author;
}
public void setAuthor(Integer author) {
	this.author = author;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public String getAuthorName() {
	return authorName;
}
public void setAuthorName(String authorName) {
	this.authorName = authorName;
}
@Override
public String toString() {
	return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title + ", author=" + author + ", price=" + price + "]";
}
}
