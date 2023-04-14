package com.planner.godsaeng.api;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planner.godsaeng.model.DataDTO;
import com.planner.godsaeng.model.UserDAO;
import com.planner.godsaeng.model.ZepetoDAO;

@Controller
@RequestMapping("/yjserver")

public class ZepetoRequestController {
	final ZepetoDAO dao;
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public ZepetoRequestController(ZepetoDAO dao) {
		this.dao = dao;
		
	}
	@GetMapping("/list")
	public String listData(Model m) {
		List<DataDTO> list;
		try {
			list = dao.getAll();
			m.addAttribute("datalist",list);
		}catch(Exception e) {
			e.printStackTrace();
			logger.warn("데이터 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error","데이터 목록이 정상적으로 처리되지 않았습니다!!");
		}
		return "/datas/datatreat";
	}
	
	
	@PostMapping("/add")
	public String addDatas(@ModelAttribute DataDTO d, Model m) {
		try {
			dao.addData(d);
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("데이터 추가 과정에서 문제 발생!");
			m.addAttribute("error","데이터가 정상적으로 등록되지 않았습니다!!");
		}
		return "redirect:/yjserver/list";
	}
	@GetMapping("/delete/{did}")
	public String deleteDatas(@PathVariable int did, Model m) {
		try {
			dao.delData(did);
		}catch(SQLException e) {
			e.printStackTrace();
			logger.warn("데이터 삭제 과정에서 문제 발생!!");
			m.addAttribute("error","데이터가 정상적으로 삭제되지 않았습니다.");
			}
		return "redirect:/yjserver/list";
	}

}
