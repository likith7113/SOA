package com.example.demo1.entity;
// himport jakarta.persistence.Entity;

public class Restaurant {

    public Restaurant(int id, String name, String loc) {
		super();
		this.id = id;
		this.name = name;
		this.loc = loc;
	}
	public Restaurant() {
		super();
	}
	private int id;
    private String name;
    private String loc;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", loc=" + loc + ", getId()=" + getId() + ", getName()="
				+ getName() + ", getLoc()=" + getLoc() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}