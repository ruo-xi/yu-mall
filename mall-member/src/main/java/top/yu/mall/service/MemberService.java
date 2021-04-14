package top.yu.mall.service;

import top.yu.mall.domain.Register;
import top.yu.mall.domain.UmsMember;
import top.yu.mall.exception.BusnessException;

/**
 * @Author: yu
 * @Date: 4/13/21:12:46 PM
 */
public interface MemberService {
    public String getOptCode(String phone) throws BusnessException;

    int register(Register register) throws BusnessException;

    UmsMember login(String username, String password) throws BusnessException;
}
