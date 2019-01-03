/**
 * 
 */
package com.lottery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.UserMapper;
import com.lottery.model.User;
import com.lottery.model.UserExample;
import com.lottery.model.UserExample.Criteria;

/**
 * @author ws_yu
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 根据微信openid查询用户
	 * @param openid
	 * @return
	 */
	public List<User> findUserByOpenid(String openid){
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andOpenidEqualTo(openid);
		List<User> userList = userMapper.selectByExample(userExample);
		if(userList!=null && userList.size()>0){
			return userList;
		}
		return null;
	}
	
	/**
	 * 根据微信openid和type查询用户
	 * @param openid
	 * @param type
	 * @return
	 */
	public User findUserByOpenidAndType(String openid, Integer type){
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andOpenidEqualTo(openid);
		criteria.andTypeEqualTo(type);
		List<User> userList = userMapper.selectByExample(userExample);
		if(userList!=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}



	public User findValidUserByOpenid(String openid){
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andOpenidEqualTo(openid).andIsvalidEqualTo(1);
		List<User> userList = userMapper.selectByExample(userExample);
		if(userList!=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional
	public void saveUser(User user){
		if(user.getId()==null){
			userMapper.insert(user);
		}else{
			userMapper.updateByPrimaryKeySelective(user);
		}
	}
	
	/**
	 * 根据手机号码查询用户列表
	 * @param phone
	 * @return
	 */
	public List<User> findUserListByPhone(String phone){
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<User> userList = userMapper.selectByExample(userExample);
		return userList;
	}
	
}
