/*package com.fxb.world.service.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.tsh.usercentre.dao.security.SysSecurityAnswerDao;
import com.tsh.usercentre.dao.security.SysSecurityQuestionDao;
import com.tsh.usercentre.po.security.SysSecurityAnswerPo;
import com.tsh.usercentre.po.security.SysSecurityQuestionPo;
import com.tsh.usercentre.vo.security.SysSecurityAnswerVo;
import com.tsh.usercentre.vo.security.SysSecurityQuestionVo;
*//**
 * 
 * @author ds
 *
 *//*
@Service
public class SysSecurityQuestionService {
	@Resource
	SysSecurityQuestionDao sysSecurityQuestionDao;
	@Resource
	SysSecurityAnswerDao sysSecurityAnswerDao;

	*//**
	 * 添加问题
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result findSecurityQuestion(Result result, Long userId) throws Exception {
		List<Long> ids=null;
		if(userId!=null){
			result=sysSecurityAnswerDao.getSysSecurityAnswerList(result, userId);
			List<SysSecurityAnswerPo> anlist=result.getData();
			ids=new ArrayList<Long>();
			for (SysSecurityAnswerPo sysSecurityAnswerPo : anlist) {
				ids.add(sysSecurityAnswerPo.getQuestionId());
			}
		}
		result = sysSecurityQuestionDao.findSecurityQuestiont(result, ids);
		List<SysSecurityQuestionPo> list = result.getData();
		SysSecurityQuestionVo sysSecurityQuestionVo = null;
		List<SysSecurityQuestionVo> listVo = new ArrayList<SysSecurityQuestionVo>();
		sysSecurityQuestionVo = new SysSecurityQuestionVo();
		sysSecurityQuestionVo.setId(0L);
		sysSecurityQuestionVo.setQuestion("请选择");
		listVo.add(sysSecurityQuestionVo);
		for (SysSecurityQuestionPo sysSecurityQuestionPo : list) {
			sysSecurityQuestionVo = new SysSecurityQuestionVo();
			PropertyUtils.copyProperties(sysSecurityQuestionVo, sysSecurityQuestionPo);
			listVo.add(sysSecurityQuestionVo);
		}
		result.setData(listVo);
		return result;
	}

	*//**
	 * 设置密保
	 * @param result
	 * @param list
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result saveSecurityAnswer(Result result, List<SysSecurityAnswerVo> list, Long userId) throws Exception {
		if (list == null || userId == null) {
			result.setStatus(500);
			result.setMsg("参数为空");
			result.setData(null);
			return result;
		}
		SysSecurityAnswerPo securityAnswer = null;
		sysSecurityAnswerDao.delSysSecurityAnswer(result, userId);
		List<SysSecurityAnswerPo> listPo = new ArrayList<SysSecurityAnswerPo>();
		for (SysSecurityAnswerVo sysSecurityAnswerVo : list) {
			securityAnswer = new SysSecurityAnswerPo();
			PropertyUtils.copyProperties(securityAnswer, sysSecurityAnswerVo);
			securityAnswer.setCreateBy(userId);
			securityAnswer.setCreateDate(new Date());
			securityAnswer.setUserId(userId);
			listPo.add(securityAnswer);
		}

		result = sysSecurityAnswerDao.saveSecurityAnswerBatch(result, listPo);
		return result;
	}

	*//**
	 * 密保验证码
	 * @param result
	 * @param list
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public Result checkSecurityAnswer(Result result, List<SysSecurityAnswerVo> list, Long userId) throws Exception {

		result = sysSecurityAnswerDao.getSysSecurityAnswerMap(result, userId);
		Map<Long, String> map = result.getData();
		for (SysSecurityAnswerVo securityAnswerVo : list) {
			if (securityAnswerVo.getAnswer() == null) {
				result.setStatus(500);
				result.setData(null);
				result.setMsg("密保问题错误");
				return result;
			}
			if (!securityAnswerVo.getAnswer().equals(map.get(securityAnswerVo.getQuestionId()))) {
				result.setStatus(500);
				result.setData(null);
				result.setMsg("密保问题错误");
				return result;
			}
		}
		result.setStatus(200);
		return result;
	}

}
*/