/**
 * Job.java 3:14:14 PM Mar 30, 2012
 *
 * Copyright(c) 2000-2012 HC360.COM, All Rights Reserved.
 */
package org.cc.demo.domain;

import java.util.Date;

import org.cc.core.cache.Cached;
import org.cc.core.db.CachedDao;
import org.cc.core.db.annotation.Table;
import org.cc.core.db.annotation.Transient;
import org.cc.demo.cache.McClient;
import org.cc.demo.json.DateDeserializer;
import org.cc.demo.json.DateSerializer;
import org.cc.demo.json.JsonUtils;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
 * @author dixingxing	
 * @date Mar 30, 2012
 */
@SuppressWarnings("serial")
@Table("memo")
@Cached
public class Memo extends CachedDao<Memo>  {
	/** 设置一个实例便于泛型查询 Memo.DB.queryList(...)  */
	public static final Memo DB = new Memo();
	
	static {
		DB.setClient(new McClient());
	}
	
	private Long id;
	
	private String name;
	
	private String createTime;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	@Transient
	private Date aDate = new Date();
	
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public Date getADate() {
		return aDate;
	}
	public void setADate(Date date) {
		aDate = date;
	}
	
	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
