package com.distribuida;

public class Autor {
private String firstname;
private String lastname;

public Autor() {

}
public Autor(String firstname, String lastname) {
	super();
	this.firstname = firstname;
	this.lastname = lastname;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
@Override
public String toString() {
	return "Autor [firstname=" + firstname + ", lastname=" + lastname + "]";
}

}