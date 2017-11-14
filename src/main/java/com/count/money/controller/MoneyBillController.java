package com.count.money.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.count.money.controller.req.LoginReq;
import com.count.money.controller.req.RegisterReq;
import com.count.money.controller.req.VerifyReq;
import com.count.money.controller.req.VerifySelectReq;
import com.count.money.controller.resp.AuthResp;
import com.count.money.core.common.MsgResult;
import com.count.money.core.common.PageReq;
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
import com.count.money.util.DateUtil;
import com.count.money.util.IDUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/money")
public class MoneyBillController {
    @Autowired
    private IMoneyBillService moneyBillService;
    @Autowired
    private IMoneyProjectService moneyProjectService;
    @Autowired
    private IMoneyMemberService moneyMemberService;
    @Autowired
    private SessionDataService sessionDataService;
    @RequestMapping("/addBill")
    public MsgResult addBill(MoneyBill moneyBill){
        moneyBill.setId(IDUtils.newID());
        SessionData sessionData = AppContext.getSession();
        moneyBill.setMemberId(sessionData.getId());
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
        page = moneyBillService.selectPage(page,new EntityWrapper<>(moneyBill));
        return new MsgResult(BeanConvertUtil.pageConvert(page,MoneyBill.class));
    }

    @RequestMapping("/deleteBill")
    public MsgResult deleteBill(List<String> list){
        List<MoneyBill> moneyBills = new ArrayList<>();
        for(String id:list){
            MoneyBill moneyBill = new MoneyBill();
            moneyBill.setState("2");
            moneyBill.setId(id);
            moneyBills.add(moneyBill);
        }
        moneyBillService.updateBatchById(moneyBills);
        return new MsgResult();
    }
    @RequestMapping("/addProject")
    public MsgResult addProject(MoneyProject moneyProject)throws Exception{
        if(moneyProjectService.selectOne(new EntityWrapper<MoneyProject>(moneyProject))!=null){
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
    @RequestMapping("/selectProjectByPage")
    public MsgResult selectProjectByPage(PageReq pageReq) throws Exception {
        MoneyProject moneyProject = new MoneyProject();
        moneyProject.setState("1");
        Page page = new Page<MoneyProject>(pageReq.getPageNo(),pageReq.getPageSize());
        page = moneyProjectService.selectPage(page,new EntityWrapper<>(moneyProject));
        return new MsgResult(BeanConvertUtil.pageConvert(page,MoneyProject.class));
    }
    @RequestMapping("/updateProject")
    public MsgResult updateProject(MoneyProject moneyProject){
        moneyProjectService.updateById(moneyProject);
        return new MsgResult();
    }
    @RequestMapping("/deleteProject")
    public MsgResult deleteProject(MoneyProject moneyProject){
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
//        sn不能重复
        if(moneyMemberService.selectOne(new EntityWrapper<>(moneyMember))!=null){
            throw new Exception("该学号已被注册");
        }
        moneyMember.setId(IDUtils.newID());
        moneyMember.setType("1");
        moneyMember.setState("2");//待审核
        moneyMember.setCreateTime(DateUtil.getCurrentTimeFull());
        moneyMemberService.insert(moneyMember);
        return new MsgResult("注册成功，并提交审核，请稍后登入");
    }
    @RequestMapping("/selectMemberByPage")
    public MsgResult selectMemberByPage(PageReq pageReq,VerifySelectReq verifySelectReq)throws Exception{
        MoneyMember moneyMember = new MoneyMember();
        moneyMember.setType("1");//只展示普通的用户
        EntityWrapper entityWrapper = new EntityWrapper(moneyMember);
        if(verifySelectReq.getState()!="0"){
            moneyMember.setState(verifySelectReq.getState());
        }
        if(StringUtils.isEmpty(verifySelectReq.getCollege())){
            entityWrapper.like("college", verifySelectReq.getCollege());
        }
        if(StringUtils.isEmpty(verifySelectReq.getMajor())){
            entityWrapper.like("major", verifySelectReq.getMajor());
        }
        if(StringUtils.isEmpty(verifySelectReq.getSn())){
            entityWrapper.like("sn", verifySelectReq.getSn());
        }
        Page page = new Page(pageReq.getPageNo(),pageReq.getPageSize());
        return new MsgResult(moneyMemberService.selectPage(page,entityWrapper));
    }
    @RequestMapping("/verify")
    public MsgResult verify(VerifyReq verifyReq)throws Exception{
        MoneyMember moneyMember = BeanConvertUtil.beanConvert(verifyReq,MoneyMember.class);
        moneyMember.setVerifyTime(DateUtil.getCurrentTimeFull());
        moneyMemberService.updateById(moneyMember);
        return new MsgResult();
    }
}
