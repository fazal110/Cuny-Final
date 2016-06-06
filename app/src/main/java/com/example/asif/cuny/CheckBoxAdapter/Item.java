/**
 * 
 */
package com.example.asif.cuny.CheckBoxAdapter;

/**
 * A POJO that contains some properties to show in the list
 * 
 * @author marvinlabs
 */
public class Item implements Comparable<Item> {

	private long id;
	private String title;
	private String date;
	private String schedule;
	private String loc;

	public Item(long id, String title, String date, String schedule, String loc) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.schedule = schedule;
		this.loc = loc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	/**
	 * Comparable interface implementation
	 * 
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(Item other) {
		return (int) (id - other.getId());
	}
}
