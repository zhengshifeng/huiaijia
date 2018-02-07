package com.flf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.flf.entity.HajFamilyMembers;
import com.flf.service.HajFamilyMembersService;

/**
 * 
 * <br>
 * <b>功能：</b>HajFamilyMembersAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/familyMembers")
public class HajFamilyMembersController {

	@Autowired(required = false)
	private HajFamilyMembersService hajFamilyMembersService;

	/**
	 * 添加家庭成员
	 * 
	 * @param HajFamilyMembers
	 */

	@RequestMapping(value = "/saveHajFamilyMembers", method = RequestMethod.POST)
	public ModelAndView saveHajFamilyMembers(HajFamilyMembers familyMembers) {
		ModelAndView mv = new ModelAndView();
		try {
			if (familyMembers.getId() == null || familyMembers.getId().intValue() == 0) {
				hajFamilyMembersService.add(familyMembers);
			} else {
				hajFamilyMembersService.update(familyMembers);
			}
			mv.setViewName("save_result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

}
