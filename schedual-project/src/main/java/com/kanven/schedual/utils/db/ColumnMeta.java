package com.kanven.schedual.utils.db;

/**
 * 数据库字段实体类
 * 
 * @author kanven
 *
 */
public class ColumnMeta {

	private String table;

	private String filed;

	private String column;

	private String javaType;

	private String dbType;

	private String comment;

	private boolean primaryKey = false;

	public ColumnMeta() {

	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getTable() {
		return table;
	}

	public String getFiled() {
		return filed;
	}

	public String getColumn() {
		return column;
	}

	public String getJavaType() {
		return javaType;
	}

	public String getDbType() {
		return dbType;
	}

	public String getComment() {
		return comment;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	@Override
	public String toString() {
		return "ColumnMeta [table=" + table + ", filed=" + filed + ", column=" + column + ", javaType=" + javaType
				+ ", dbType=" + dbType + ", comment=" + comment + ", primaryKey=" + primaryKey + "]";
	}

}
