package com.manthansharma.snippet.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

	private Integer id;
	private String username;
	private String email;
	private String password;
	private String first_name;
	private String last_name;
	private String auth_token;
	private List<String> snippets = new ArrayList<String>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(String name, String email, String password) {
		setName(name);
		this.email = email;
		this.password = password;
	}

	/**
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return The username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username The username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return The email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return The firstName
	 */
	public String getFirstName() {
		return first_name;
	}

	/**
	 * @param firstName The first_name
	 */
	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	/**
	 * @return The lastName
	 */
	public String getLastName() {
		return last_name;
	}

	/**
	 * @param lastName The last_name
	 */
	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	/**
	 * @return The Name
	 */
	public String getName() {
		return first_name + " " + last_name;
	}

	/**
	 * @param name The name
	 */
	public void setName(String name) {
		String[] name_arr = name.split(" ", 2);
		first_name = name_arr[0];
		last_name = name_arr[1];
	}

	/**
	 * @return The snippets
	 */
	public List<String> getSnippets() {
		return snippets;
	}

	/**
	 * @param snippets The snippets
	 */
	public void setSnippets(List<String> snippets) {
		this.snippets = snippets;
	}

	/**
	 * @return The authToken
	 */
	public String getAuthToken() {
		return auth_token;
	}

	/**
	 * @param auth_token The auth_token
	 */
	public void setAuthToken(String auth_token) {
		this.auth_token = auth_token;
	}


	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}