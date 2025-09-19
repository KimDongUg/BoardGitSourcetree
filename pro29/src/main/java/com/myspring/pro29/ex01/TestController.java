package com.myspring.pro29.ex01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/test/*")
public class TestController {
  static Logger logger = LoggerFactory.getLogger(TestController.class);
	
  @RequestMapping("/hello")
  public String hello() {
	  // REST (Representational State Transfer) : 화면은 그대로 유지하면서 필요한 데이터만 전송 받아 빠르게 결과를 표시(예: Ajax)
	  // REST API : REST 방식으로 제공되는 API (= RESTful API)
	  
	return "Hello REST!!";
  } 
  
  @RequestMapping("/member")
  public MemberVO member() {
    MemberVO vo = new MemberVO();
    vo.setId("hong");
    vo.setPwd("1234");
    vo.setName("홍길동");
    vo.setEmail("hong@test.com");
    return vo;
  } 	
  
  @RequestMapping("/membersList")
  public List<MemberVO> listMembers () {
    List<MemberVO> list = new ArrayList<MemberVO>();
	for (int i = 0; i < 10; i++) {
	  MemberVO vo = new MemberVO();
	  vo.setId("hong"+i);
	  vo.setPwd("123"+i);
	  vo.setName("홍길동"+i);
	  vo.setEmail("hong"+i+"@test.com");
	  list.add(vo);
	}
    return list; 
  }	
  
  @RequestMapping("/membersMap")
  public Map<Integer, MemberVO> membersMap() {
    Map<Integer, MemberVO> map = new HashMap<Integer, MemberVO>();
    for (int i = 0; i < 10; i++) {
      MemberVO vo = new MemberVO();
      vo.setId("hong" + i);
      vo.setPwd("123"+i);
      vo.setName("홍길동" + i);
      vo.setEmail("hong"+i+"@test.com");
      map.put(i, vo); 
    }
    return map; 
  } 	
  
  @RequestMapping(value= "/notice/{num}" , method = RequestMethod.GET)
  public int notice(@PathVariable("num") int num ) throws Exception {
	  
	  // @PathVariable : 브라우저에서 요청 URL로 전달된 매개변수를 가져와서 활용할 때
	  
	  return num;
  }	

  @RequestMapping(value= "/info", method = RequestMethod.POST)
  public String modify(@RequestBody MemberVO vo, HttpServletRequest request, HttpServletResponse response ) 
		  throws Exception{
	request.setCharacterEncoding("utf-8");
	response.setContentType("html/text;charset=utf-8"); 
	  
    logger.info(vo.toString());
    
    return vo.toString();
  }
  
  @RequestMapping("/membersList2")
  public ResponseEntity<List<MemberVO>> listMembers2() {
	List<MemberVO> list = new ArrayList<MemberVO>();
	for (int i = 0; i < 10; i++) {
	  MemberVO vo = new MemberVO();
	  vo.setId("lee" + i);
	  vo.setPwd("123"+i);
	  vo.setName("이순신" + i);
      vo.setEmail("lee"+i+"@test.com");
	  list.add(vo);
	}
	
	// 코드 200 (성공 응답) : HttpStatus.OK
	// 코드 201 (성공 응답) : HttpStatus.CREATED   <== 새로운 리소스 생성되었다는 의미의 상태
	// 코드 404 (클라이언트 오류 응답) : HttpStatus.NOT_FOUND
	// 코드 500 (서버 오류 응답): HttpStatus.INTERNAL_SERVER_ERROR 
	
    return new ResponseEntity(list, HttpStatus.INTERNAL_SERVER_ERROR);
  }	
  
  
	@RequestMapping(value = "/res3")
	public ResponseEntity res3() {
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	    String message = "<script>";
		message += " alert('새 회원을 등록합니다.');";
		message += " location.href='/pro29/test/membersList2'; ";
		message += " </script>";
		return  new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	}
	
}
