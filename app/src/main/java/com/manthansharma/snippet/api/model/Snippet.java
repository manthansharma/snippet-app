package com.manthansharma.snippet.api.model;

import java.util.HashMap;
import java.util.Map;

public class Snippet {

	private Integer id;
	private String title;
	private String code;
	private Boolean linenos;
	private String language;
	private String style;
	private String owner;
	private String highlight;
	private String next;
	private String prev;
	private String days_ago;
	private Map<String, Object> additionalProperties = new HashMap<>();

	public Snippet() {
	}

	public Snippet(String title, String language, String style, String code) {
		setLanguage(language);
		setStyle(style);
		this.code = code;
		this.title = title;
	}

	/**
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return The code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code The code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return The linenos
	 */
	public Boolean getLinenos() {
		return linenos;
	}

	/**
	 * @param linenos The linenos
	 */
	public void setLinenos(Boolean linenos) {
		this.linenos = linenos;
	}

	/**
	 * @return The language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language The language
	 */
	public void setLanguage(String language) {
		this.language = language.toLowerCase().replaceAll("\\s+", "");
	}

	/**
	 * @return The style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style The style
	 */
	public void setStyle(String style) {
		this.style = style.toLowerCase().replaceAll("\\s+", "");
	}

	/**
	 * @return The owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner The owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return The highlight
	 */
	public String getHighlight() {
		return highlight;
	}

	/**
	 * @param highlight The highlight
	 */
	public void setHighlight(String highlight) {
		this.highlight = highlight;
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
	 * @return The prev
	 */
	public String getPrev() {
		return prev;
	}

	/**
	 * @param prev The prev
	 */
	public void setPrev(String prev) {
		this.prev = prev;
	}

	/**
	 * @return The created
	 */
	public String getDaysAgo() {
		return days_ago;
	}

	/**
	 * @param created The created
	 */
	public void setDaysAgo(String created) {
		this.days_ago = days_ago;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}