package com.java110.api.listener.inspectionPlanStaff;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java110.api.bmo.inspectionPlanStaff.IInspectionPlanStaffBMO;
import com.java110.api.listener.AbstractServiceApiListener;
import com.java110.core.annotation.Java110Listener;
import com.java110.core.context.DataFlowContext;
import com.java110.entity.center.AppService;
import com.java110.event.service.api.ServiceDataFlowEvent;
import com.java110.utils.constant.CommonConstant;
import com.java110.utils.constant.ServiceCodeInspectionPlanStaffConstant;
import com.java110.utils.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


/**
 * 保存小区侦听
 * add by wuxw 2019-06-30
 */
@Java110Listener("deleteInspectionPlanStaffListener")
public class DeleteInspectionPlanStaffListener extends AbstractServiceApiListener {

    @Autowired
    private IInspectionPlanStaffBMO inspectionPlanStaffBMOImpl;

    @Override
    protected void validate(ServiceDataFlowEvent event, JSONObject reqJson) {
        //Assert.hasKeyAndValue(reqJson, "xxx", "xxx");

        Assert.hasKeyAndValue(reqJson, "ipStaffId", "ipStaffId不能为空");
        Assert.hasKeyAndValue(reqJson, "communityId", "小区信息不能为空");

    }

    @Override
    protected void doSoService(ServiceDataFlowEvent event, DataFlowContext context, JSONObject reqJson) {

        HttpHeaders header = new HttpHeaders();
        context.getRequestCurrentHeaders().put(CommonConstant.HTTP_ORDER_TYPE_CD, "D");
        JSONArray businesses = new JSONArray();

        AppService service = event.getAppService();

        //添加单元信息
        businesses.add(inspectionPlanStaffBMOImpl.deleteInspectionPlanStaff(reqJson, context));

        ResponseEntity<String> responseEntity = inspectionPlanStaffBMOImpl.callService(context, service.getServiceCode(), businesses);

        context.setResponseEntity(responseEntity);
    }

    @Override
    public String getServiceCode() {
        return ServiceCodeInspectionPlanStaffConstant.DELETE_INSPECTIONPLANSTAFF;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

}
