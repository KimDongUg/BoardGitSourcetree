package com.springboot.mysqlPrj.dto;

import com.springboot.mysqlPrj.entity.T_member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {
  private String id;
  private String pwd;
  private String name;
  private String email;
  private String joindate;

  public T_member toEntity() {
    return new T_member(id, pwd, name, email, joindate);
  }
}
