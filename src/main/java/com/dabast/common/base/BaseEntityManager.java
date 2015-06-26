package com.dabast.common.base;

import com.mongodb.CommandResult;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collection;
import java.util.List;
@Transactional(readOnly = true)
public abstract class BaseEntityManager <E> implements IBaseEntityManager<E> {
	
//	private Log log = LogFactory.getLog(getClass());

	protected abstract EntityDao<E> getEntityDao();
	@Transactional
	public void insert(E e) {
		getEntityDao().insert(e);
	}
	public String saveFile(String fileName,byte[] file){
		return  getEntityDao().saveFile(fileName,file);
	}
	public GridFSDBFile findFileById(String id){
		return getEntityDao().findFileById(id);
	}
	@Override
	public List<E> findEquals(E e) {
		return  getEntityDao().findEquals(e);
	}

	@Override
	public List<E> findAll() {
		return getEntityDao().findAll();
	}

	@Override
	public E findById(ObjectId id) {
		return getEntityDao().findById(id);
	}

	@Override
	public List<E> findRange(String key, Object min, Object max) {
		return getEntityDao().findRange(key,min,max);
	}

	@Override
	public List<E> findNotEquals(E e) {
		return getEntityDao().findNotEquals(e);
	}

	@Override
	public List<E> findKeyIn(String key, Collection collection) {
		return getEntityDao().findKeyIn(key, collection);
	}

	@Override
	public List<E> findKeyNotIn(String key, Collection collection) {
		return getEntityDao().findKeyNotIn(key, collection);
	}

	@Override
	public List<E> findKeyIsNull(String key) {
		return getEntityDao().findKeyIsNull(key);
	}

	@Override
	public List<E> findByRegex(String key, String regex) {
		return getEntityDao().findByRegex(key, regex);
	}

	@Override
	public List<E> findInArray(String key, Object[] values) {
		return getEntityDao().findInArray(key, values);
	}

	@Override
	public List<E> findRef(String refKey, Object value) {
		return getEntityDao().findRef(refKey,value);
	}

	@Override
	public Page<E> findPage(int pageIndex) {
		return getEntityDao().findPage(pageIndex);
	}

	@Override
	public void update(E e) {
		getEntityDao().update(e);
	}

	@Override
	public CommandResult runCommand(String s) {

		return getEntityDao().runCommand(s);
	}

	@Override
	public Object find(Object parse) {
		return getEntityDao().find(parse);
	}
}
