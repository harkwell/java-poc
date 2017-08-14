package com.khallware.hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="item")
public class Item
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable=false, updatable=true)
	private int id;

	@Column(name="key", unique=false, nullable=false, updatable=true)
	private String key = null;

	@Column(name="value", unique=false, nullable=true, updatable=true)
	private String value = null;

	public Item() {}

	public Item(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString()
	{
		return(new StringBuilder()
			.append("{key=\""+key+"\", value=\""+value+"\"}")
			.toString());
	}
}
