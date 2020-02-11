package com.park.web.menu.db;

import java.util.List;

public interface MenuDAO {
	
	List<MenuVO> getMenuList() throws Exception;
	
	int insertMenu(MenuVO menuVO) throws Exception;
	
	int updateMenu(MenuVO menuVO) throws Exception;
	
	int deleteMenu(String bg_no) throws Exception;

	int insertBoardGroup(MenuVO menuVO) throws Exception;

	int updateBoardGroup(MenuVO menuVO) throws Exception;

	int deleteBoardGroup(String bg_no) throws Exception;

	List<MenuVO> getBoardGroupList() throws Exception;

	List<MenuVO> getBoardGroupFromUpperBgName(String upper_bg_name) throws Exception;
	
}
