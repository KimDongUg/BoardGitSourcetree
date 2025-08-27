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
	  // REST (Representational State Transfer) : ȭ���� �״�� �����ϸ鼭 �ʿ��� �����͸� ���� �޾� ������ ����� ǥ��(��: Ajax)
	  // REST API : REST ������� �����Ǵ� API (= RESTful API)
	  
	return "Hello REST!!";
  } 
  
  @RequestMapping("/member")
  public MemberVO member() {
    MemberVO vo = new MemberVO();
    vo.setId("hong");
    vo.setPwd("1234");
    vo.setName("ȫ�浿");
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
	  vo.setName("ȫ�浿"+i);
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
      vo.setName("ȫ�浿" + i);
      vo.setEmail("hong"+i+"@test.com");
      map.put(i, vo); 
    }
    return map; 
  } 	
  
  @RequestMapping(value= "/notice/{num}" , method = RequestMethod.GET)
  public int notice(@PathVariable("num") int num ) throws Exception {
	  
	  // @PathVariable : ���������� ��û URL�� ���޵� �Ű������� �����ͼ� Ȱ���� ��
	  
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
	  vo.setName("�̼���" + i);
      vo.setEmail("lee"+i+"@test.com");
	  list.add(vo);
	}
	
	// �ڵ� 200 (���� ����) : HttpStatus.OK
	// �ڵ� 201 (���� ����) : HttpStatus.CREATED   <== ���ο� ���ҽ� �����Ǿ��ٴ� �ǹ��� ����
	// �ڵ� 404 (Ŭ���̾�Ʈ ���� ����) : HttpStatus.NOT_FOUND
	// �ڵ� 500 (���� ���� ����): HttpStatus.INTERNAL_SERVER_ERROR 
	
    return new ResponseEntity(list, HttpStatus.INTERNAL_SERVER_ERROR);
  }	
  
  
	@RequestMapping(value = "/res3")
	public ResponseEntity res3() {
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	    String message = "<script>";
		message += " alert('�� ȸ���� ����մϴ�.');";
		message += " location.href='/pro29/test/membersList2'; ";
		message += " </script>";
		return  new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	}
	
}
