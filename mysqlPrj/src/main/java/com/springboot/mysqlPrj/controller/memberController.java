package com.springboot.mysqlPrj.controller;

import com.springboot.mysqlPrj.dto.MemberForm;
import com.springboot.mysqlPrj.entity.T_member;
import com.springboot.mysqlPrj.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
public class memberController {

  @Autowired  // 스프링부트가 미리 생성해 놓은 리파지터리 객체 주입(DI)
  private MemberRepository memberRepository;

  @GetMapping("/members/new")
  public String newMembersForm() {
    return "members/new";
  }

  @GetMapping("/members")  // 목록 조회
  public String index(Model model){
    // 1. DB에서 모든 Article 데이터 가져오기
    ArrayList<T_member> memberEntityList = memberRepository.findAll();
    log.info("memberEntityList.toString() : "+ memberEntityList.toString());
    // 2. 가져온 Article 묶음을 모델에 등록하기
    model.addAttribute("memberList", memberEntityList);

    // 3. 사용자에게 보여 줄 뷰페이지 설정하기
    return "members/index";
  }

  @PostMapping("/members/create")
  public String createMember(MemberForm form) {
    log.info("form.toString() :: " + form.toString());
    // 1. DTO 를 엔티티로 변환
    T_member member = form.toEntity();
    log.info("member.toString() ::: " + member.toString());

    // 2. 리파지터리로 엔티티를 DB에 저장
    T_member saved = memberRepository.save(member);
    log.info("saved.toString() ::: " + saved.toString());

    return "redirect:/members";
  }

  @GetMapping("/members/{id}/edit")
  public String edit(@PathVariable String id, Model model){
    log.info("id == "+ id);
    // 수정할 데이터 가져오기
    Optional<T_member> memberEntity = Optional.ofNullable(memberRepository.findById(id).orElse(null));
    log.info("memberEntity.toString() :: "+ memberEntity.toString());
    // 가져온 Article 모델에 등록하기
    model.addAttribute("member", memberEntity.get());
    // 뷰페이지 설정하기
    return "members/edit";
  }

  @PostMapping("/members/update")
  public String update(MemberForm form){
    log.info("form.toString() :: "+ form.toString());
    // 1. DTO를 엔티티로 변환 하기
    T_member memberEntity = form.toEntity();
    // 2-1 DB에서 기존 데이터 가져오기
    T_member target = memberRepository.findById(memberEntity.getId()).orElse(null);
    // 2-2 기존 데이터 값을 갱신하기
    if(target != null){
      memberRepository.save(memberEntity); // 엔티티를 DB에 저장(갱신)
    }
    // 3. 수정결과 리스트 페이지로 리다이렉트 하기
    return "redirect:/members";
  }

  @GetMapping("/members/{id}/delete")
  public String delete(@PathVariable String id, RedirectAttributes rttr){
    log.info("삭제 요청이 들어왔습니다!!");
    // 1. 삭제할 대상 가져오기
    T_member target = memberRepository.findById(id).orElse(null);
    log.info("target.toString() : "+ target.toString());
    if(target != null){
      memberRepository.delete(target);
      rttr.addAttribute("msg", "삭제됐습니다.");
    }
    return "redirect:/members";

  }

}
