package com.dabast.common.base;

import com.dabast.common.util.MongoDocumentUtil;
import com.dabast.common.util.ReflectUtil;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 */
public abstract class BaseMongoDao<E> implements EntityDao<E> {
    @Resource
    private MongoTemplate mongoTemplate;
    private Class<E> collectionClass;

    public BaseMongoDao() {
        Class typeCls = getClass();
        Type genType = typeCls.getGenericSuperclass();
        while (true) {
            if (!(genType instanceof ParameterizedType)) {
                typeCls = typeCls.getSuperclass();
                genType = typeCls.getGenericSuperclass();
            } else {
                break;
            }
        }
        this.collectionClass = (Class<E>) ((ParameterizedType) genType).getActualTypeArguments()[0];
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
    public String saveFile(String fileName,byte[] file){
        GridFS fs=new GridFS(mongoTemplate.getDb());
        GridFSInputFile fsInputFile=fs.createFile(file);
        fsInputFile.put("uploadDate",new Date());
        fsInputFile.put("filename",fileName);
        fsInputFile.save();
        return  fsInputFile.get("_id")==null?null :fsInputFile.get("_id").toString();
    }
    public GridFSDBFile findFileById(String id){
        GridFS fs=new GridFS(mongoTemplate.getDb());
       GridFSDBFile file=fs.find(new ObjectId(id));
        return file;
    }
    @Override
    public void insert(E e) {
        System.out.println("invoke insert method......");
        mongoTemplate.insert(e);
    }

    public List<E> findEquals(E e) {
        System.out.println("invoke find method......");
        Query query = getEqualsQuery(e);
        if (query == null) return findAll();

        return mongoTemplate.find(query, collectionClass);
    }


    private Query getEqualsQuery(E e) {
        Query query = null;
        Criteria criteria = null;
        boolean firstCriteriaAdded = false;
        for (Field field : collectionClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)) continue;
            String fieldName = field.getName();
            Object fieldValue = ReflectUtil.getValue(e, fieldName);
            if (fieldValue == null) continue;
            if (fieldValue.toString().trim().equals("")) continue;
            String key = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class).value();
            if (key == null || key.equals("")) key = fieldName;
            if (firstCriteriaAdded == false) {
                criteria = Criteria.where(key).is(fieldValue);
                firstCriteriaAdded = true;
            } else {
                criteria.and(key).is(fieldValue);
            }
        }
        if (criteria == null) return null;
        query = Query.query(criteria);
        System.out.println(query);
        return query;
    }

    public List<E> findAll() {
        DB db=mongoTemplate.getDb();
        DBCollection collection=db.getCollection("users");
        DBCursor cur = collection.find();
        BasicDBList basicDBList=new BasicDBList();
        while (cur.hasNext()) {
//            System.out.println(cur.next().get("_id"));
            basicDBList.add(cur.next());
        }
//        System.out.println(cur.count());
//        System.out.println(cur.getCursorId());
        System.out.println(basicDBList.size());
       for (Object object:basicDBList){
           System.out.println(object);
       }
        System.out.println(JSON.serialize(cur));
        return mongoTemplate.findAll(collectionClass);
    }

    public E findById(ObjectId id) {
        return mongoTemplate.findById(id, collectionClass);
    }

    @Override
    public List<E> findRange(String key, Object min, Object max) {
        if (!MongoDocumentUtil.isKeyExists(collectionClass, key)) return null;
        Criteria criteria = Criteria.where(key);
        if (min != null) criteria.gte(min);
        if (max != null) criteria.lte(max);
        Query query = Query.query(criteria);
        System.out.println(query);
        return mongoTemplate.find(query, collectionClass);
    }

    @Override
    public List<E> findNotEquals(E e) {
        if (e == null) return findAll();
        Query query = getNotEqualsQuery(e);
        System.out.println(query);
        CommandResult commandResult=mongoTemplate.executeCommand("");
        commandResult.toString();
        return mongoTemplate.find(query, collectionClass);
    }

    private Query getNotEqualsQuery(E e) {
        Query query = null;
        Criteria criteria = null;
        boolean firstCriteriaAdded = false;
        for (Field field : collectionClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)) continue;
            String fieldName = field.getName();

            Object fieldValue = ReflectUtil.getValue(e, fieldName);
            if (fieldValue == null) continue;
            if (fieldValue.toString().trim().equals("")) continue;
            String key = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class).value();
            if (key == null || key.equals("")) key = fieldName;
            if (firstCriteriaAdded == false) {
                criteria = Criteria.where(key).ne(fieldValue);
                firstCriteriaAdded = true;
            } else {
                criteria.and(key).ne(fieldValue);
            }
        }
        if (criteria == null) return null;
        query = Query.query(criteria);
        System.out.println(query);
        return query;

    }

    public List<E> findKeyIn(String key, Collection collection) {
        Criteria criteria = Criteria.where(key).in(collection);
        Query query = Query.query(criteria);
        System.out.println(query);
        return mongoTemplate.find(query, collectionClass);
    }

    @Override
    public List<E> findKeyNotIn(String key, Collection collection) {
        Criteria criteria = Criteria.where(key).nin(collection);
        Query query = Query.query(criteria);
        System.out.println(query);

        return mongoTemplate.find(query, collectionClass);
    }

    @Override
    public List<E> findKeyIsNull(String key) {
        Criteria criteria = Criteria.where(key).is(null);
        Query query = Query.query(criteria);
        System.out.println(query);
        return mongoTemplate.find(query, collectionClass);
    }

    public List<E> findByRegex(String key, String regex) {
        Criteria criteria = Criteria.where(key).regex(regex);
        Query query = Query.query(criteria);
        System.out.println(query);
        return mongoTemplate.find(query, collectionClass);
    }

    @Override
    public List<E> findInArray(String key, Object[] values) {
        Criteria criteria = Criteria.where(key).all(values);
        Query query = Query.query(criteria);
        System.out.println(query);
        return mongoTemplate.find(query, collectionClass);
    }

    @Override
    public List<E> findRef(String refKey, Object value) {
//        Criteria criteria=Criteria.where("address.country").is("China").and("address.provence").is("Hunan");
        Criteria criteria = Criteria.where("address").elemMatch(Criteria.where("country").is("China").and("provence").is("Hunan"));
        Query query = Query.query(criteria);
        System.out.println(query);
        //why result is empty???
        return mongoTemplate.find(query, collectionClass);
    }

    public Page<E> findPage(int pageIndex) {
        Criteria criteria = new Criteria();
        Query query = Query.query(criteria);
        Long count = mongoTemplate.count(query, collectionClass);
        int pageSize = 2;
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        query = query.limit(pageSize).skip((pageIndex - 1) * pageSize);
        System.out.println(query);
        List<E> list = mongoTemplate.find(query, collectionClass);
        Page<E> page = new PageImpl<E>(list, pageable, count);
        return page;
    }

    public void update(E e) {
        String id = MongoDocumentUtil.getId(e);
        if (null == id || "".equals(id.trim())) {
        //如果主键为空,则不进行修改
            return;
        }
        Update update = new Update();
        //TODO
//        update.set("email", member.getEmail());

        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), update, collectionClass);
    }
    public CommandResult runCommand(String command){

        CommandResult commandResult= mongoTemplate.executeCommand(command);
        return commandResult;
    }

    @Override
    public Object find(Object parse) {
        Query query=Query.query(Criteria.where("中国名").is("谢宇星"));
        DBObject dbObject=query.getQueryObject();
        DBCollection collection=mongoTemplate.getDb().getCollection("users");

        DBCursor cursor= collection.find(dbObject);
        while (cursor.hasNext()){
            System.out.println(cursor.next());
        }
    return null;
    }
}
