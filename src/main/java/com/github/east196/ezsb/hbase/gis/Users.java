package com.github.east196.ezsb.hbase.gis;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.east196.ezsb.hbase.HbaseFunc;

public class Users {//用户
	
	private String user;//用户
	
	private String name;//姓名
	
	private String email;//电邮
	
	private String password;//密码
	
	private Long tweetCount;//推特数
	
	public Users(){}
	
	public Users(String user,String name,String email,String password,Long tweetCount){
		this.user=user;
		this.name=name;
		this.email=email;
		this.password=password;
		this.tweetCount=tweetCount;
	}

	public String toRowKey() {
		StringBuilder sb=new StringBuilder();
		sb.append(HbaseFunc.hash(user));
		sb.append("_");
		sb.append(HbaseFunc.hash(password));
		return sb.toString();
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getTweetCount() {
		return tweetCount;
	}

	public void setTweetCount(Long tweetCount) {
		this.tweetCount = tweetCount;
	}
	
	@Override 
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override 
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@Override 
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
	
}
