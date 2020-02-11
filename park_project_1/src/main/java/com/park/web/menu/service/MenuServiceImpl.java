package com.park.web.menu.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.web.board.db.BoardDAO;
import com.park.web.menu.db.MenuDAO;
import com.park.web.menu.db.MenuVO;

@Service
public class MenuServiceImpl implements MenuService {

	@Inject
	private MenuDAO mmanager;
	
	@Inject
	private BoardDAO boardmanager;
	
	@Override
	public List<MenuVO> getMenuList() throws Exception {
		return mmanager.getMenuList();
	}
	
	@Override
	public List<MenuVO> getBoardGroupList() throws Exception {
		return mmanager.getBoardGroupList();
	}
	
	@Override
	public List<MenuVO> getBoardGroupFromUpperBgName(String upper_bg_name) throws Exception {
		return mmanager.getBoardGroupFromUpperBgName(upper_bg_name);
	}

	@Transactional
	@Override
	public void insertMenu(MenuVO menuVO) throws Exception {
		mmanager.insertMenu(menuVO);
		mmanager.insertBoardGroup(menuVO);
	}

	@Transactional
	@Override
	public void updateMenu(MenuVO menuVO) throws Exception {
		mmanager.updateMenu(menuVO);
		mmanager.updateBoardGroup(menuVO);
	}

	@Transactional
	@Override
	public void deleteMenu(String bg_no) throws Exception {
		mmanager.deleteMenu(bg_no);
		mmanager.deleteBoardGroup(bg_no);
		boardmanager.deleteBoardFromBgno(bg_no);
	}

	

}
