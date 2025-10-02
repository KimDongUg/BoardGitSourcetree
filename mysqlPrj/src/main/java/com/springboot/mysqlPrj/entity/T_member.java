package com.springboot.mysqlPrj.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //생성자 
@NoArgsConstructor // 기본 생성자 어노테이션
@ToString
@Entity             // 엔티티 선언
@Getter             // 롬복으로 게터 추가
public class T_member {
  @Id               // 엔티티의 대표값 지정
  private String id;
  @Column
  private String pwd;
  @Column
  private String name;
  @Column
  private String email;
  @Column
  private String joindate;
}
