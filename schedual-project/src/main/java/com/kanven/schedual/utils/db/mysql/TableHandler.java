package com.kanven.schedual.utils.db.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.utils.db.ColumnDesc;
import com.kanven.schedual.utils.db.ColumnMeta;
import com.kanven.schedual.utils.db.TableDesc;
import com.kanven.schedual.utils.db.TypeConvert;
import com.mysql.jdbc.Driver;

public class TableHandler {

	private static final Logger log = LoggerFactory.getLogger(TableHandler.class);

	private static boolean loaded = false;

	private String user;

	private String password;

	private String url;

	static {
		String driver = Driver.class.getName();
		try {
			Class.forName(driver);
			loaded = true;
		} catch (ClassNotFoundException e) {
			log.error("数据库驱动加载失败！", e);
		}
	}

	public TableHandler(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	private Connection getConn() throws SQLException {
		if (loaded) {
			return DriverManager.getConnection(url, user, password);
		}
		throw new RuntimeException("数据库驱动，没有加载！");
	}

	public List<String> getTables() throws SQLException {
		List<String> tables = new LinkedList<String>();
		DatabaseMetaData meta = getConn().getMetaData();
		String[] types = { "TABLE" };
		ResultSet rs = meta.getTables(null, null, null, types);
		while (rs.next()) {
			String table = rs.getString(TableDesc.NAME.value());
			tables.add(table);
		}
		return tables;
	}

	public List<ColumnMeta> getColumns(String table) throws SQLException {
		List<ColumnMeta> columns = new LinkedList<ColumnMeta>();
		if (StringUtils.isNotEmpty(table)) {
			Connection conn = getConn();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getColumns(null, null, table, null);
			while (rs.next()) {
				ColumnMeta cm = new ColumnMeta();
				cm.setTable(table);
				cm.setColumn(rs.getString(ColumnDesc.NAME.value()));
				cm.setComment(rs.getString(ColumnDesc.REMARKS.value()));
				cm.setDbType(rs.getString(ColumnDesc.DB_TYPE.value()));
				cm.setJavaType(TypeConvert.convert(rs.getInt(ColumnDesc.JAVA_TYPE.value())));
				columns.add(cm);
			}
		}
		return columns;
	}

	public static boolean isLoaded() {
		return loaded;
	}

}
