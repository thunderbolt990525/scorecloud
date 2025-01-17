package com.goldskyer.gmxx.manager.controllers.workflow;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.goldskyer.core.dto.JsonData;
import com.goldskyer.gmxx.common.aop.AvoidDuplicateSubmission;
import com.goldskyer.gmxx.common.aop.RoleControl;
import com.goldskyer.gmxx.common.dtos.CreateTaskDto;
import com.goldskyer.gmxx.common.dtos.DataTableReqDto;
import com.goldskyer.gmxx.common.dtos.DataTablesRespDto;
import com.goldskyer.gmxx.common.entities.WorkFlowTask;
import com.goldskyer.gmxx.common.helpers.DataTableHelper;
import com.goldskyer.gmxx.manager.controllers.BaseManagerController;
import com.goldskyer.gmxx.workflow.entities.RepaireFlow;
import com.goldskyer.gmxx.workflow.entities.UseCarDetail;
import com.goldskyer.gmxx.workflow.entities.UseCarFlow;
import com.goldskyer.gmxx.workflow.vo.WorkflowDataVo;

/**
 * 用车流程管理
 * @author jintianfan
 *
 */
@Controller("managerUseCarFlowController")
@RequestMapping("/manager/workflow")
@SuppressWarnings(
{ "rawtypes", "unchecked", "deprecation" })
public class UseCarFlowController extends BaseManagerController
{
	@RequestMapping("/usecarflow/toApply.htm")
	@AvoidDuplicateSubmission(needSaveToken = true)
	@RoleControl("WORKFLOW_ADD")
	public ModelAndView toAdd(HttpServletRequest request, @RequestParam(required = false) String pId)
	{
		ModelAndView mv = new ModelAndView("/manager/template");
		mv.addObject("innerPage", "workflow/usecarflow_toApply");
		return mv;
	}

	@RequestMapping(value = "/usecarflow/add.json", produces = "application/json;charset=UTF-8")
	@ResponseBody
	@AvoidDuplicateSubmission(needRemoveToken = true)
	@RoleControl("WORKFLOW_ADD")
	public JsonData addJson(HttpServletRequest request, UseCarFlow flow)
	{
		String accountId = (String) request.getSession().getAttribute("accountId");
		flow.setAccountId(accountId);
		flow.setCreateDate(new Date());
		flow.setUpdateDate(new Date());
		//封装对象
		String[] carTypes = request.getParameterValues("carType[]");
		String[] carCounts = request.getParameterValues("carCount[]");
		String[] others = request.getParameterValues("other[]");
		if (carTypes != null & carTypes.length > 0)
		{
			for (int i = 0; i < carTypes.length; i++)
			{
				UseCarDetail detail = new UseCarDetail();
				if (StringUtils.isBlank(carTypes[i]) && StringUtils.isBlank(carCounts[i])
						&& StringUtils.isBlank(others[i]))
				{
					continue;
				}
				detail.setUseCarFlow(flow);
				detail.setCarType(StringUtils.trimToEmpty(carTypes[i]));
				detail.setCarCount(Integer.valueOf(StringUtils.trimToEmpty(carCounts[i])));
				detail.setOther(StringUtils.trimToEmpty(others[i]));
				flow.getUserCarDetail().add(detail);
			}
		}
		String flowId = baseDao.add(flow);
		CreateTaskDto createTaskDto = new CreateTaskDto();
		createTaskDto.setAccountId(getCurrentAccountId());
		createTaskDto.setObjectId(flowId);
		createTaskDto.setObjectType("用车流程");
		createTaskDto.setTaskName(getCurrentAccount().getNickname() + "提出用车申请");
		workFlowService.createTask(createTaskDto);
		return JsonData.success();
	}

	@RequestMapping("/usecarflow/view.htm")
	@RoleControl("WORKFLOW_VIEW")
	public ModelAndView view(HttpServletRequest request, @RequestParam String id)
	{
		ModelAndView mv = new ModelAndView("/manager/template");
		UseCarFlow flow = baseDao.query(UseCarFlow.class, id);
		mv.addObject("flow", flow);
		mv.addObject("innerPage", "workflow/usecarflow_view");
		//显示流程功能按钮
		WorkFlowTask task = workFlowService.queryTaskByObjectId(flow.getId());
		mv.addObject("task", task);
		mv.addObject("canOp", workFlowService.ifCanOperationTask(task, getCurrentAccountId()));
		//显示流程操作数据
		List<WorkflowDataVo> flowDatas = workFlowService.queryWorkFlowDataVo(flow.getId());
		mv.addObject("flowDatas", flowDatas);
		return mv;
	}

	/**
	 * 我的申请列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/usecarflow/list.htm")
	@RoleControl("WORKFLOW_VIEW")
	public ModelAndView list(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("/manager/template");
		mv.addObject("innerPage", "workflow/usecarflow_list");
		return mv;
	}

	/**
	 * 我的申请列表
	 * @param dataTableReqDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/usecarflow/list_data.json", produces = "application/json;charset=UTF-8")
	@ResponseBody
	@RoleControl("WORKFLOW_VIEW")
	public Object listData(DataTableReqDto dataTableReqDto, HttpServletRequest request)
	{
		String search = request.getParameter("search[value]");
		dataTableReqDto.setSearchKey(search);
		dataTableReqDto.setSql(
				"select a.department,a.useReason,a.fromLocation,a.toLocation,t.status,a.createDate,a.id from UseCarFlow a,WorkFlowTask t  where a.id=t.objectId  and a.accountId=:accountId ");
		dataTableReqDto.setOrderBy("order by a.createDate desc ,a.id");
		dataTableReqDto.setParam("accountId", getCurrentAccountId());
		dataTableReqDto.setSearchField("a.department,a.useReason,a.fromLocation,a.toLocation,t.status");
		DataTablesRespDto respDto = DataTableHelper.execute(dataTableReqDto, baseDao);
		return respDto;
	}

	@RequestMapping("/usecarflow/revoke.htm")
	@RoleControl("WORKFLOW_ADD")
	public ModelAndView revoke(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		RepaireFlow flow = baseDao.query(RepaireFlow.class, id);
		return new ModelAndView("redirect:list.htm");
	}

}