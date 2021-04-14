package top.yu.mall.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yu.mall.config.properties.RedisKeyProperties;
import top.yu.mall.domain.Register;
import top.yu.mall.domain.UmsMember;
import top.yu.mall.domain.UmsMemberExample;
import top.yu.mall.exception.BusnessException;
import top.yu.mall.mapper.UmsMemberMapper;
import top.yu.mall.service.MemberService;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yu
 * @Date: 4/13/21:12:46 PM
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedisKeyProperties redisKeyProperties;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String getOptCode(String phone) throws BusnessException {
        //检查该用户是否注册
        UmsMemberExample umsMemberExample = new UmsMemberExample();
        umsMemberExample.createCriteria().andPhoneEqualTo(phone);
        List<UmsMember> umsMembers = umsMemberMapper.selectByExample(umsMemberExample);
        if (!CollectionUtils.isEmpty(umsMembers)) {
            throw new BusnessException("该手机用户已注册");
        }

        //生成redis key
        String redisKey = getOptCodeRedisKey(phone);

        //校验redis中是否存在redis key 若存在说明60s内该用户获取过校验码
        if (redisTemplate.hasKey(redisKey)) {
            throw new BusnessException("请60s后再试");
        }

        //生成校验码
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        //存储校验码并设置过期时间60s
        redisTemplate.opsForValue().set(redisKey,
                stringBuilder.toString(),
                redisKeyProperties.getExpire(), TimeUnit.SECONDS);

        return stringBuilder.toString();
    }

    @Override
    public int register(Register register) throws BusnessException {
        String phone = register.getPhone();
        String redisKey = getOptCodeRedisKey(phone);


        String optCode = (String) redisTemplate.opsForValue().get(redisKey);
        if (!StringUtils.hasText(optCode) || !optCode.equals(register.getOptCode())) {
            throw new BusnessException("动态校验码错误");
        }

        UmsMember umsMember = new UmsMember();
        BeanUtils.copyProperties(register, umsMember);
        umsMember.setStatus(1);
        umsMember.setMemberLevelId(4L);
        umsMember.setPassword(passwordEncoder.encode(register.getPassword()));

        return umsMemberMapper.insertSelective(umsMember);
    }

    @Override
    public UmsMember login(String username, String password) throws BusnessException {
        UmsMemberExample umsMemberExample = new UmsMemberExample();
        umsMemberExample.createCriteria()
                .andUsernameEqualTo(username)
                .andStatusEqualTo(1);
        List<UmsMember> umsMembers = umsMemberMapper.selectByExample(umsMemberExample);

        if (CollectionUtils.isEmpty(umsMembers)) {
            throw new BusnessException("找不到此用户");
        }

        if (umsMembers.size() > 1){
            throw new BusnessException("用户被注册多次");
        }

        UmsMember umsMember = umsMembers.get(0);

        if (!passwordEncoder.matches(password, umsMember.getPassword())) {
            throw new BusnessException("用户密码错误");
        }

        return umsMember;
    }

    public String getOptCodeRedisKey(String phone) {
        return redisKeyProperties.getPrefix() + ':' + phone;
    }
}
