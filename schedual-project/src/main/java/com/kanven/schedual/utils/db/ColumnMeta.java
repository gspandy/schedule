package com.kanven.schedual.utils.db;

/**
 * 数据库字段实体类
 * 
 * @author kanven
 *
 */
public class ColumnMeta {

	private String table;

	private String column;

	private String javaType;

	private String dbType;

	private String comment;

	public ColumnMeta() {

	}

	public void setTable(String table) {
		this.table = table;
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

	public String getTable() {
		return table;
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

	@Override
	public String toString() {
		return "ColumnMeta [table=" + table + ", column=" + column + ", javaType=" + javaType + ", dbType=" + dbType
				+ ", comment=" + comment + "]";
	}

}
