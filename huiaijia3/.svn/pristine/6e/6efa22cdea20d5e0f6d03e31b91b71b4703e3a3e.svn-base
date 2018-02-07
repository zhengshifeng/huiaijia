package com.flf.service.impl;

import com.flf.entity.Role;
import com.flf.mapper.RoleMapper;
import com.flf.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper roleMapper;
	
	public List<Role> listAllRoles() {
		return roleMapper.listAllRoles();
	}

	public void deleteRoleById(int roleId) {
		roleMapper.deleteRoleById(roleId);
	}

	public Role getRoleById(int roleId) {
		return roleMapper.getRoleById(roleId);
	}

	public boolean insertRole(Role role) {
		if(roleMapper.getCountByName(role)>0)
			return false;
		else{
			roleMapper.insertRole(role);
			return true;
		}
	}

	public boolean updateRoleBaseInfo(Role role) {
		if(roleMapper.getCountByName(role)>0)
			return false;
		else{
			roleMapper.updateRoleBaseInfo(role);
			return true;
		}
	}
	
	public void updateRoleRights(Role role) {
		roleMapper.updateRoleRights(role);
	}
}
