package com.manthansharma.snippet.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class snippetList {

	private Integer count;
	private String next;
	private Object previous;
	private List<Snippet> results = new ArrayList<>();
	private Map<String, Object> additionalProperties = new HashMap<>();

	/**
	 * @return The count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count The count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return The next
	 */
	public String getNext() {
		return next;
	}

	/**
	 * @param next The next
	 */
	public void setNext(String next) {
		this.next = next;
	}

	/**
	 * @return The previous
	 */
	public Object getPrevious() {
		return previous;
	}

	/**
	 * @param previous The previous
	 */
	public void setPrevious(Object previous) {
		this.previous = previous;
	}

	/**
	 * @return The results
	 */
	public List<Snippet> getResults() {
		return results;
	}

	/**
	 * @return The results
	 * @param results The results
	 */
	public Boolean updateResults(List<Snippet> results) {
		return this.results.addAll(0, results);
	}

	/**
	 * @param results The results
	 */
	public void setResults(List<Snippet> results) {
		this.results = results;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}