package com.kanven.schedual.dao;

import org.apache.ibatis.annotations.Param;

public interface BaseDao<T> {

	Long save(T entity);

	T get(@Param("id") Long id);

	void update(T entity);

	void delete(@Param("id") Long id);

}
