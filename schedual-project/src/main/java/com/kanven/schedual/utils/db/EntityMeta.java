package com.kanven.schedual.utils.db;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 实体元数据类
 * 
 * @author kanven
 *
 */
public class EntityMeta {

	/**
	 * 包名
	 */
	private String pkg;
	/**
	 * 类名
	 */
	private String clazz;
	/**
	 * 导入类
	 */
	private List<String> imports = new LinkedList<String>();
	/**
	 * 字段元数据集合
	 */
	private List<FieldMeta> fields;

	{
		imports.add("java.io.Serializable");
	}

	public EntityMeta() {

	}

	public EntityMeta(String pkg) {
		this.pkg = pkg;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public List<String> getImports() {
		return imports;
	}

	public void addImport(String impt) {
		if (StringUtils.isEmpty(impt)) {
			return;
		}
		this.imports.add(impt);
	}

	public List<FieldMeta> getFields() {
		return fields;
	}

	public void setFields(List<FieldMeta> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "EntityMeta [pkg=" + pkg + ", clazz=" + clazz + ", imports=" + imports + ", fields=" + fields + "]";
	}

}
