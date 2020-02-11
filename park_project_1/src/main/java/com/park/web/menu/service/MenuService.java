package com.park.web.menu.service;

import java.util.List;

import com.park.web.menu.db.MenuVO;

public interface MenuService {
	
	List<MenuVO> getMenuList() throws Exception;
	
	void insertMenu(MenuVO menu) throws Exception;
	
	void updateMenu(MenuVO menu) throws Exception;
	
	void deleteMenu(String bg_no) throws Exception;

	List<MenuVO> getBoardGroupList() throws Exception;

	List<MenuVO> getBoardGroupFromUpperBgName(String upper_bg_name) throws Exception;

}
