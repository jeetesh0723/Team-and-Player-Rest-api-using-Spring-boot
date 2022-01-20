package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.*;

@Entity
@Table(name = "team")

public class Team {
	 // constructors for all fields 
	public Team(Long id, String name, String location, Date createdAt, Date updatedAt, Set<Player> players) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.players = players;
	}


	public Team() {} //default cunstructor for Team

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
	
    @CreationTimestamp
   	@Column(name="created_at", nullable=false, updatable=false)
   	private Date createdAt;
   	
   	@UpdateTimestamp
   	@Column(name="updated_at")
   	private Date updatedAt;


   	 @OneToMany(cascade = CascadeType.ALL,
   	            fetch = FetchType.LAZY,
   	            mappedBy = "team")
   	 private Set<Player> players = new HashSet<>();
   	@JsonManagedReference  // to solve recursion problem
	public Set<Player> getPlayers() {
		return players;
	}


	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



   	 
   	 
 
}