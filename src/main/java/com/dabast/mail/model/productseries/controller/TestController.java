package com.dabast.mail.model.productseries.controller;

import com.dabast.common.base.BaseRestSpringController;
import com.dabast.entity.User;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/11.
 */
@Controller
@RequestMapping("/api")
public class TestController extends BaseRestSpringController {

    @RequestMapping(value = "/user/{id}")
    public ResponseEntity<User> get(ModelMap model, @PathVariable String id) {
        User user=new User();
        user.setName("Tom"+id);
        user.setHeight(100);
        user.setSex("f");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @RequestMapping(value = "/user/post",method = RequestMethod.POST)
    public ResponseEntity<User> post(@RequestBody User user,ModelMap model) {
        System.out.println("post");
        user.setId(new ObjectId());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user,ModelMap model) {
        System.out.println("login------");
        if (user.getName()==null) return null;
        if (user.getName().contains("a"))
        return new ResponseEntity("{token:'abc123456789'}",HttpStatus.OK);
        else {
            if (user.getName().contains("x"))
                return new ResponseEntity("{token:'abc123456789'}",HttpStatus.FORBIDDEN);//403
        }
        return new ResponseEntity("{token:'abc123456789'}",HttpStatus.UNAUTHORIZED);//401
    }

    @RequestMapping(value = "/users")
    public ResponseEntity<List<User>> all(ModelMap model,String role) {
        List<User> users=new ArrayList<User>();
        User u = new User();
        u.setId(new ObjectId());
        u.setName("Jackson");
        users.add(u);
        User u2 = new User();
        u2.setId(new ObjectId());
        u2.setName("Johnson");
        u2.setSex("m");
        users.add(u2);
        ResponseEntity<List<User>> usersE= new ResponseEntity<List<User>>(users, HttpStatus.OK);
        return usersE;
    }
    @RequestMapping(value = "/user/test1")
    public ResponseEntity<User> test1(ModelMap model) {
        System.out.println("test1");
        User user=new User();
        user.setName("Robinson");
        user.setSex("m");
        user.setHeight(180);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @RequestMapping(value = "/user/test2")
    public ResponseEntity<User> test2(ModelMap model) {
        System.out.println("test2");
        User user=new User();
        user.setName("Michale Jordan");
        user.setSex("m");
        user.setHeight(194);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @RequestMapping(value = "/user/test3")
      public ResponseEntity<User> test3(ModelMap model) {
        System.out.println("test3");
        User user=new User();
        user.setName("Alison Fisher");
        user.setSex("f");
        user.setHeight(170);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    public static void main(String[] args){
        String jsonStr="[{id:100,name:'Johnson'},{id:101,name:'Jackson'}]";
        System.out.println(JSON.parse(jsonStr));
    }
}