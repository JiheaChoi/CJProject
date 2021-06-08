package com.example.cjproject.bean;

import java.io.Serializable;


public class InfoBean implements Serializable {

   public String id;    //고유 아이디(기본키)
   public String name;  //이름
   public String pass;   //비밀번호
   public String room;   //방번호
   public String userId;     //아이디


   public InfoBean() {

   }
}
