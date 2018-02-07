package com.flf.entity;

import java.util.List;

public class Menu {
	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private Integer parentId;
	private String icon;
	private Integer sort;

	private Menu parentMenu;
	private List<Menu> subMenu;

	private boolean hasMenu = false;
	private boolean parentFlag;
	private boolean open;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public List<Menu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}

	public boolean isHasMenu() {
		return hasMenu;
	}

	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public boolean isParentFlag() {
		return parentFlag;
	}

	public void setParentFlag(boolean parentFlag) {
		this.parentFlag = parentFlag;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
