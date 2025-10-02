package com.springboot.mysqlPrj.repository;

import com.springboot.mysqlPrj.entity.T_member;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

// CrudRepository는 JPA에서 제공하는 인터페이스
// 이를 상속해 엔티티를 관리(CRUD).
// t_member: 관리대상 엔티티의 클래스 타입
// Long: 관리대상 엔티티의 대표값 타입(id 의 타입)
public interface MemberRepository extends CrudRepository<T_member, String> {
  @Override
  ArrayList<T_member> findAll();   // Iterable -> ArrayList 수정
}
