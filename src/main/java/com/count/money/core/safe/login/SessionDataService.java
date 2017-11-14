package com.count.money.core.safe.login;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.count.money.core.safe.userSafe.AppContext;
import com.count.money.core.safe.userSafe.SessionData;
import com.count.money.po.MoneyMember;
import com.count.money.service.IMoneyMemberService;
import com.count.money.util.BeanConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Service
public class SessionDataService {
    @Autowired
    private IMoneyMemberService moneyMemberService;
    public Map login(MoneyMember moneyMember,HttpServletRequest request)throws Exception{
        MoneyMember moneyMember1 = new MoneyMember();
        moneyMember1.setSn(moneyMember.getSn());
        moneyMember1 = moneyMemberService.selectOne(new EntityWrapper<>(moneyMember1));
        if(moneyMember1==null || !moneyMember1.getPassword().equals(moneyMember.getPassword())){
            throw new Exception("用户名或者密码错误");
        }
        Map map = new HashMap();
        if(moneyMember1.getState().equals("1")){
            map.put("state","ok");
            SessionData sessionData = BeanConvertUtil.beanConvert(moneyMember1,SessionData.class);
            request.getSession().setAttribute("token_money",sessionData);
//            AppContext.putSession(sessionData);
        }else if(moneyMember1.getState().equals("2")){
            map.put("state","fail");
            map.put("msg","该用户待审核中，请稍后登入");
        }
        return map;
    }
    public Map logout(HttpServletRequest httpServletRequest)throws Exception{
        Map map = new HashMap();
        SessionData sessionData = AppContext.getSession();
        if(sessionData==null){
            map.put("state","warning");
            map.put("msg","非法操作，即将自动退出");
        }else{
            AppContext.putSession(null);
            httpServletRequest.getSession().invalidate();//.setAttribute("token_money",);
            map.put("state","success");
            map.put("msg","注销成功");
        }
        return map;
    }
}
