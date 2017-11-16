package com.count.money.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.count.money.controller.req.*;
import com.count.money.controller.resp.AuthResp;
import com.count.money.core.common.MsgResult;
import com.count.money.core.common.PageReq;
import com.count.money.core.common.PageResult;
import com.count.money.core.safe.login.SessionDataService;
import com.count.money.core.safe.userSafe.AppContext;
import com.count.money.core.safe.userSafe.SessionData;
import com.count.money.po.MoneyBill;
import com.count.money.po.MoneyMember;
import com.count.money.po.MoneyProject;
import com.count.money.service.IMoneyBillService;
import com.count.money.service.IMoneyMemberService;
import com.count.money.service.IMoneyProjectService;
import com.count.money.util.BeanConvertUtil;
import com.count.money.util.CommonUtil;
import com.count.money.util.DateUtil;
import com.count.money.util.IDUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/money")
public class MoneyBillController {
//    1、bill状态：1未结算2结算
//    2、member状态：1审核通过2待审核3审核未过4删除
//    3、project状态：1正常2删除
    @Autowired
    private IMoneyBillService moneyBillService;
    @Autowired
    private IMoneyProjectService moneyProjectService;
    @Autowired
    private IMoneyMemberService moneyMemberService;
    @Autowired
    private SessionDataService sessionDataService;
    @RequestMapping("/addBill")
    public MsgResult addBill(BillReq billReq)throws Exception{
        if(!CommonUtil.isMoney(billReq.getProjectPrice())){
            throw new Exception("金额格式出错(最高精度0.00)");
        }
        if(!CommonUtil.isNumber(billReq.getProjectCount())){
            throw new Exception("数量格式出错(只能为正整数)");
        }
        MoneyBill moneyBill = BeanConvertUtil.beanConvert(billReq,MoneyBill.class);
        moneyBill.setId(IDUtils.newID());
        SessionData sessionData = AppContext.getSession();
        moneyBill.setMemberId(sessionData.getId());
        moneyBill.setCreateTime(DateUtil.getCurrentTimeFull());
        moneyBill.setState("1");
        moneyBillService.insert(moneyBill);
        return new MsgResult();
    }

    @RequestMapping("/selectBillByMember")
    public MsgResult selectBillByMember(PageReq pageReq) throws Exception {
        SessionData sessionData = AppContext.getSession();
        MoneyBill moneyBill = new MoneyBill();
        moneyBill.setMemberId(sessionData.getId());
        Page page = new Page(pageReq.getPageNo(),pageReq.getPageSize());
        EntityWrapper ew = new EntityWrapper<>(moneyBill);
        ew.orderBy("settle_time asc,create_time desc");
        page = moneyBillService.selectPage(page,ew);
        return new MsgResult(BeanConvertUtil.pageConvert(page,MoneyBill.class));
    }
    @RequestMapping("/selectUnsettleDetByMember")
    public MsgResult selectUnsettleDetByMember(PageReq pageReq,String id) throws Exception {
        authCheck();
        MoneyBill moneyBill = new MoneyBill();
        moneyBill.setMemberId(id);
        moneyBill.setState("1");
        Page page = new Page(pageReq.getPageNo(),pageReq.getPageSize());
        EntityWrapper ew = new EntityWrapper<>(moneyBill);
        ew.orderBy("create_time",false);
        page = moneyBillService.selectPage(page,ew);
        return new MsgResult(BeanConvertUtil.pageConvert(page,MoneyBill.class));
    }

    @RequestMapping("/selectCurrentSituation")
    public MsgResult selectCurrentSituation() throws Exception {
        authCheck();
        Map map = new HashMap();
        map.put("currentDate",DateUtil.getCurrentDate10());
        return new MsgResult(filledDataSource(moneyBillService.selectCurrentSituation(map)));
    }
    private List<Map<String,Object>> filledDataSource(List<Map<String,Object>> srcList) {
        List<Map<String,Object>> filledList = new ArrayList<>();
        for (int i = 0 ; i < 24 ; i++) {
            String key = String.valueOf(i);
            if (i < 10) key = "0" + key;
            Map<String,Object> result = this.isContainKey(key,srcList);
            filledList.add(result);
        }
        return  filledList;
    }

    private Map<String,Object> isContainKey(String key ,List<Map<String,Object>> orderNumsList) {
        boolean flag = false;
        Map<String,Object> containData = new HashMap<>();
        for (Map<String,Object> temp: orderNumsList) {
            String hour = (String) temp.get("HOUR");
            if (key.equals(hour)) {
                flag = true;
                containData = temp;
            }
        }
        if (!flag) {
            containData.put("HOUR",key);
            containData.put("CNT",0);
        }
        containData.put("flag",flag);
        return containData;
    }

    @RequestMapping("/selectUnsettleBillByMember")
    public MsgResult selectUnsettleBillByMember() throws Exception {
        SessionData sessionData = AppContext.getSession();
        MoneyBill moneyBill = new MoneyBill();
        moneyBill.setMemberId(sessionData.getId());
        EntityWrapper ew = new EntityWrapper<>(moneyBill);
        moneyBill.setState("1");
        ew.setSqlSelect("ifNull(sum(project_price*project_count),0) as projectPrice,count(project_price) as projectCount");
        return new MsgResult(moneyBillService.selectOne(ew));
    }
    @RequestMapping("/selectUnsettleBill")
    public MsgResult selectUnsettleBill() throws Exception {
        authCheck();
        MoneyBill moneyBill = new MoneyBill();
        EntityWrapper ew = new EntityWrapper<>(moneyBill);
        moneyBill.setState("1");
        ew.setSqlSelect("ifNull(sum(project_price*project_count),0) as projectPrice,count(project_price) as projectCount");
        return new MsgResult(moneyBillService.selectOne(ew));
    }
    @RequestMapping("/settleBillByMembers")
    public MsgResult settleBillByMembers(@RequestBody List<String> list)throws Exception{
        authCheck();
        MoneyBill moneyBill = new MoneyBill();
        moneyBill.setState("2");
        moneyBill.setSettleTime(DateUtil.getCurrentTimeFull());
        EntityWrapper ew = new EntityWrapper();
        ew.in("member_id",list);
        moneyBillService.update(moneyBill,ew);
        return new MsgResult();
    }
    @RequestMapping("/settleAllBill")
    public MsgResult settleAllBill()throws Exception{
        authCheck();
        MoneyMember moneyMember = new MoneyMember();
        moneyMember.setState("1");
        moneyMember.setType("1");
        EntityWrapper wrapper = new EntityWrapper(moneyMember);
        List<MoneyMember> memberList= moneyMemberService.selectList(wrapper);
        List<String> list = new ArrayList<>();
        for(MoneyMember tmp:memberList){
            list.add(tmp.getId());
        }
        MoneyBill moneyBill = new MoneyBill();
        moneyBill.setState("2");
        moneyBill.setSettleTime(DateUtil.getCurrentTimeFull());
        EntityWrapper ew = new EntityWrapper();
        ew.in("member_id",list);
        moneyBillService.update(moneyBill,ew);
        return new MsgResult();
    }

    @RequestMapping("/addProject")
    public MsgResult addProject(ProjectReq projectReq)throws Exception{
        authCheck();
        if(!CommonUtil.isMoney(projectReq.getProjectPrice())){
            throw new Exception("金额格式出错(最高精度0.00)");
        }
        MoneyProject moneyProject = BeanConvertUtil.beanConvert(projectReq,MoneyProject.class);
        MoneyProject tmp = new MoneyProject();
        EntityWrapper entityWrapper = new EntityWrapper<>(tmp);
        tmp.setProjectName(moneyProject.getProjectName());
        tmp.setState("1");
        if(moneyProjectService.selectOne(entityWrapper)!=null){
            throw new Exception("项目名已存在");
        }
        moneyProject.setId(IDUtils.newID());
        moneyProject.setState("1");
        moneyProject.setCreateTime(DateUtil.getCurrentTimeFull());
        moneyProjectService.insert(moneyProject);
        return new MsgResult();
    }
    @RequestMapping("/selectProject")
    public MsgResult selectProject(){
        MoneyProject moneyProject = new MoneyProject();
        moneyProject.setState("1");
        return new MsgResult(moneyProjectService.selectList(new EntityWrapper<>(moneyProject)));
    }
    @RequestMapping("/selectProjectByID")
    public MsgResult selectProjectByID(String id)throws Exception{
        authCheck();
        return new MsgResult(moneyProjectService.selectById(id));
    }
    @RequestMapping("/selectProjectByPage")
    public MsgResult selectProjectByPage(PageReq pageReq) throws Exception {
        authCheck();
        MoneyProject moneyProject = new MoneyProject();
        moneyProject.setState("1");
        Page page = new Page<MoneyProject>(pageReq.getPageNo(),pageReq.getPageSize());
        page = moneyProjectService.selectPage(page,new EntityWrapper<>(moneyProject));
        return new MsgResult(BeanConvertUtil.pageConvert(page,MoneyProject.class));
    }
    @RequestMapping("/updateProject")
    public MsgResult updateProject(ProjectReq projectReq)throws Exception{
        authCheck();
        if(!CommonUtil.isMoney(projectReq.getProjectPrice())){
            throw new Exception("金额格式出错(最高精度0.00)");
        }
        MoneyProject moneyProject = BeanConvertUtil.beanConvert(projectReq,MoneyProject.class);

        MoneyProject tmp = new MoneyProject();
        EntityWrapper entityWrapper = new EntityWrapper<>(tmp);
        tmp.setProjectName(moneyProject.getProjectName());
        tmp.setState("1");
        tmp=moneyProjectService.selectOne(entityWrapper);
        if(tmp!=null && !(tmp.getId().equals(projectReq.getId()))){
            throw new Exception("项目名已存在");
        }

        moneyProjectService.updateById(moneyProject);
        return new MsgResult();
    }
    @RequestMapping("/deleteProject")
    public MsgResult deleteProject(MoneyProject moneyProject)throws Exception{
        authCheck();
        moneyProject.setDeleteTime(DateUtil.getCurrentTimeFull());
        moneyProject.setState("2");
        moneyProjectService.updateById(moneyProject);
        return new MsgResult();
    }
    @RequestMapping("/auth")
    public MsgResult auth(LoginReq loginReq)throws Exception{
        SessionData sessionData = AppContext.getSession();
        AuthResp authResp = BeanConvertUtil.beanConvert(sessionData,AuthResp.class);
        String type=null;
        if(authResp!=null){
            type=authResp.getType();
        }
        if(StringUtils.isEmpty(type)){
            throw new Exception("请先登入");
        }else if(type.equals("1")){//commonuser
            authResp.setPermissions(Arrays.asList(new String[]{"money1"}));
        }else if(type.equals("2")){//manager
            authResp.setPermissions(Arrays.asList(new String[]{"money2","money3","money4","money5"}));
        }else if(type.equals("3")){//superuser
            authResp.setPermissions(Arrays.asList(new String[]{"money1","money2","money3","money4","money5"}));
        }
        return new MsgResult(authResp);
    }
    @RequestMapping("/login")
    public MsgResult login(LoginReq loginReq,HttpServletRequest request)throws Exception{
        MoneyMember moneyMember = new MoneyMember();
        moneyMember.setSn(loginReq.getSn());
        moneyMember.setPassword(loginReq.getPassword());
        Map result = sessionDataService.login(moneyMember,request);
        return new MsgResult(result);
    }
    @RequestMapping("/logout")
    public MsgResult logout(HttpServletRequest request)throws Exception{
        Map result = sessionDataService.logout(request);
        return new MsgResult(result);
    }
    @RequestMapping("/register")
    public MsgResult register(RegisterReq registerReq)throws Exception{
        MoneyMember moneyMember = BeanConvertUtil.beanConvert(registerReq,MoneyMember.class);
        MoneyMember tmp = new MoneyMember();
        tmp.setSn(moneyMember.getSn());
        EntityWrapper ew = new EntityWrapper<>(tmp);
        ew.notIn("state","4");
//        sn不能重复
        if(moneyMemberService.selectOne(ew)!=null){
            throw new Exception("该学号已被注册");
        }
        moneyMember.setId(IDUtils.newID());
        moneyMember.setType("1");
        moneyMember.setState("2");//待审核
        moneyMember.setCreateTime(DateUtil.getCurrentTimeFull());
        moneyMemberService.insert(moneyMember);
        return new MsgResult("注册成功，并提交审核，请稍后登入");
    }
    @RequestMapping("/reRegister")
    public MsgResult reRegister(RegisterReq registerReq)throws Exception{
        MoneyMember moneyMember = BeanConvertUtil.beanConvert(registerReq,MoneyMember.class);

//        用户密码校验+用户状态校验
        MoneyMember moneyMember1 = new MoneyMember();
        moneyMember1.setSn(moneyMember.getSn());
        moneyMember1 = moneyMemberService.selectOne(new EntityWrapper<>(moneyMember1));
        if(moneyMember1==null || !moneyMember1.getPassword().equals(moneyMember.getPassword())){
            throw new Exception("用户名或者密码错误");
        }
        if(moneyMember1.getState().equals("1")|| !moneyMember1.getType().equals("1")){
            throw new Exception("该用户禁用申诉");
        }else if(moneyMember1.getState().equals("2")){
            throw new Exception("该用户排队审核中，禁用申诉");
        }else if(moneyMember1.getState().equals("4")){
            throw new Exception("该用户已销户，禁用申诉");
        }
        moneyMember.setId(moneyMember1.getId());
        moneyMember.setState("2");//待审核
        moneyMember.setVerifyTime("");
        moneyMemberService.updateById(moneyMember);
        return new MsgResult("申诉成功，并提交审核，请稍后登入");
    }
    @RequestMapping("/selectMemberByPage")
    public MsgResult selectMemberByPage(PageReq pageReq,VerifySelectReq verifySelectReq)throws Exception{
        authCheck();
        MoneyMember moneyMember = new MoneyMember();
        moneyMember.setType("1");//只展示普通的用户
        EntityWrapper entityWrapper = new EntityWrapper(moneyMember);
        if(!verifySelectReq.getState().equals("0")){
            moneyMember.setState(verifySelectReq.getState());
        }else{
            entityWrapper.notIn("state","4");
        }
        if(!StringUtils.isEmpty(verifySelectReq.getCollege())){
            entityWrapper.like("college", verifySelectReq.getCollege());
        }
        if(!StringUtils.isEmpty(verifySelectReq.getMajor())){
            entityWrapper.like("major", verifySelectReq.getMajor());
        }
        if(!StringUtils.isEmpty(verifySelectReq.getSn())){
            entityWrapper.like("sn", verifySelectReq.getSn());
        }
        if(!StringUtils.isEmpty(verifySelectReq.getTrueName())){
            entityWrapper.like("true_name", verifySelectReq.getTrueName());
        }
        entityWrapper.orderBy("verify_time asc,create_time",false);
        Page page = new Page(pageReq.getPageNo(),pageReq.getPageSize());
        return new MsgResult(BeanConvertUtil.pageConvert(moneyMemberService.selectPage(page,entityWrapper),MoneyMember.class));
    }
    @RequestMapping("/selectMemberBySn")
    public MsgResult selectMemberBySn(PageReq pageReq,String sn)throws Exception{
        authCheck();
        Map map = new HashMap();
        map.put("sn",sn);
        map.put("pageStart",(pageReq.getPageNo()-1)*pageReq.getPageSize());
        map.put("pageSize",pageReq.getPageSize());
        PageResult pageResult = new PageResult();
        pageResult.setRows(moneyMemberService.selectMemberBySn(map));
        pageResult.setTotal(moneyMemberService.selectMemberCountBySn(map));
        return new MsgResult(pageResult);
    }
    @RequestMapping("/verify")
    public MsgResult verify(VerifyReq verifyReq)throws Exception{
        authCheck();
        MoneyMember moneyMember = BeanConvertUtil.beanConvert(verifyReq,MoneyMember.class);
        moneyMember.setVerifyTime(DateUtil.getCurrentTimeFull());
        moneyMemberService.updateById(moneyMember);
        return new MsgResult();
    }
    @RequestMapping("/deleteMember")
    public MsgResult deleteMember(String id)throws Exception{
        authCheck();
        MoneyMember moneyMember = new MoneyMember();
        moneyMember.setId(id);
        moneyMember.setDeleteTime(DateUtil.getCurrentTimeFull());
        moneyMember.setState("4");
        moneyMemberService.updateById(moneyMember);
        return new MsgResult();
    }
    public boolean authCheck()throws Exception{
        SessionData sessionData = AppContext.getSession();
        if(sessionData.getType().equals("3")||sessionData.getType().equals("2"))
            return true;
        throw new Exception("您无权限操作！");
    }
}
