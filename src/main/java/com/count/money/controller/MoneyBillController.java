package com.count.money.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.count.money.controller.req.LoginReq;
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
import com.count.money.util.DateUtil;
import com.count.money.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @RequestMapping("/settleBill")
    public MsgResult settleBill(List<String> list){
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
    @RequestMapping("/login")
    public MsgResult login(LoginReq loginReq)throws Exception{
        MoneyMember moneyMember = new MoneyMember();
        moneyMember.setSn(loginReq.getSn());
        moneyMember.setPassword(loginReq.getPassword());
        Map result = sessionDataService.login(moneyMember);
        return new MsgResult(result);
    }
}
