package com.park.web.menu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.park.web.menu.db.MenuVO;
import com.park.web.menu.service.MenuService;

@RestController
@RequestMapping(value="/restMenu")
public class RestMenuController {
	
	private static final Logger logger = LoggerFactory.getLogger(RestMenuController.class);
	
	@Inject
	private MenuService service;
	
	
	//메뉴 리스트
	@RequestMapping(value="/getMenuList", method=RequestMethod.POST)
	public Map<String, Object> getMenuList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.put("menuList", service.getMenuList());
			map.put("status", "OK");
			
		}catch(Exception e) {
			e.printStackTrace();
			map.put("status", "False");
		}
		return map;
	}
	
	//게시판 그룹 리스트
	@RequestMapping(value="/getBoardGroupList", method=RequestMethod.POST)
	public Map<String, Object> getBoardGroupList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.put("boardGroupList", service.getBoardGroupList());
			map.put("status", "OK");
			
			map.put("free", service.getBoardGroupFromUpperBgName("free"));
			map.put("sport", service.getBoardGroupFromUpperBgName("sport"));
			map.put("hobby", service.getBoardGroupFromUpperBgName("hobby"));
			map.put("suggest", service.getBoardGroupFromUpperBgName("suggest"));
			
			
		}catch(Exception e) {
			e.printStackTrace();
			map.put("status", "False");
		}
		return map;
	}
	
	//인서트
	@RequestMapping(value="/insertMenu", method=RequestMethod.POST)
	public Map<String, Object> insertMenu(MenuVO menuVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
				
		try {
			service.insertMenu(menuVO);
			map.put("status", "OK");
			
		}catch(Exception e) {
			e.printStackTrace();
			map.put("status", "False");
		}
		return map;
	}
	
	//업데이트
	@RequestMapping(value="/updateMenu", method=RequestMethod.POST)
	public Map<String, Object> updateMenu(MenuVO menuVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			service.updateMenu(menuVO);
			map.put("status", "OK");
			
		}catch(Exception e) {
			e.printStackTrace();
			map.put("status", "False");
		}
		return map;
	}
	
	//삭제
	@RequestMapping(value="/deleteMenu", method=RequestMethod.POST)
	public Map<String, Object> deleteMenu(String bg_no) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			service.deleteMenu(bg_no);
			map.put("status", "OK");
		}catch(Exception e) {
			e.printStackTrace();
			map.put("status", "False");
		}
		return map;
	}
	
	
	
}
