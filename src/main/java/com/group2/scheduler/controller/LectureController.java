package com.group2.scheduler.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group2.scheduler.entity.LectureEntity;
import com.group2.scheduler.service.LectureService;
import com.group2.scheduler.service.RegisterService;

@Controller
@RequestMapping("/lecture")
public class LectureController {
	
	@Autowired
	@Qualifier("lectureService")
	private LectureService lectureService;
	
	@Autowired
	@Qualifier("registerService")
	private RegisterService registerService;
	
	//강의 리스트
	@GetMapping("/list")
	public String list(Model model) {
		
		List<LectureEntity> list = lectureService.getList();
		model.addAttribute("list", list);
		
		return "lecture/mainlist";
	}
	
	//상세 화면
	@GetMapping("/detail")
	public String detail(@RequestParam("lno") int lno, Model model) {
		
		LectureEntity lecture = lectureService.getDetail(lno);
		model.addAttribute("lecture", lecture);
		
		//수강 인원
		int fixedNum = registerService.CountUp(lecture);
		model.addAttribute("fixedNum", fixedNum);
		
		return "lecture/detail";
	}
	
	//마이 페이지
	@GetMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		
		String userId = (String)session.getAttribute("userId");
		
		List<LectureEntity> list = lectureService.getMyList();
		model.addAttribute("list", list);
		
		return "lecture/mypage";
	}
	
}
