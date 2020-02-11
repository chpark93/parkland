package com.park.web.menu.db;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


@Repository
public class MenuDAOImpl implements MenuDAO{

	@Inject
	private SqlSession sqlsession;
	
	
	@Override
	public List<MenuVO> getMenuList() throws Exception {
		return sqlsession.selectList("menu.getMenuList");
	}

	@Override
	public int insertMenu(MenuVO menuVO) throws Exception {
		return sqlsession.insert("menu.insertMenu", menuVO);
	}

	@Override
	public int updateMenu(MenuVO menuVO) throws Exception {
		return sqlsession.update("menu.updateMenu", menuVO);
	}

	@Override
	public int deleteMenu(String bg_no) throws Exception {
		return sqlsession.delete("menu.deleteMenu", bg_no);
	}
	
	
	@Override
	public List<MenuVO> getBoardGroupList() throws Exception {
		return sqlsession.selectList("menu.getBoardGroupList");
	}
	
	
	@Override
	public int insertBoardGroup(MenuVO menuVO) throws Exception {
		return sqlsession.insert("menu.insertBoardGroup", menuVO);
	}

	@Override
	public int updateBoardGroup(MenuVO menuVO) throws Exception {
		return sqlsession.update("menu.updateBoardGroup", menuVO);
	}

	@Override
	public int deleteBoardGroup(String bg_no) throws Exception {
		return sqlsession.delete("menu.deleteBoardGroup", bg_no);
	}
	
	@Override
	public List<MenuVO> getBoardGroupFromUpperBgName(String upper_bg_name) throws Exception {
		return sqlsession.selectList("menu.getBoardGroupFromUpperBgName", upper_bg_name);
	}
	
	
	

}
