package com.dabast.common.base;

import com.mongodb.CommandResult;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface IBaseEntityManager<E> {
   void insert(E e);
   public String saveFile(String fileName, byte[] file);
   public GridFSDBFile findFileById(String id);
   List<E> findEquals(E e);
   List<E> findNotEquals(E e);
   List<E> findAll();
   E findById(ObjectId id);;
   List<E> findRange(String key, Object min, Object max);
   List<E> findKeyIn(String key, Collection collection);

   List<E> findKeyNotIn(String key, Collection collection);

   List<E> findKeyIsNull(String key);

   List<E> findByRegex(String key, String regex);

   List<E> findInArray(String key, Object[] values);

   List<E> findRef(String refKey, Object value);

   Page<E> findPage(int pageIndex);

   void update(E e);

   CommandResult runCommand(String s);

   Object find(Object parse);
}
