package com.xinhuanet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinhuanet.mapper.LoginMapper;
import com.xinhuanet.model.Login;
import com.xinhuanet.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;

	@Override
	public Login getLogin(Long id) {
		// TODO Auto-generated method stub
		return loginMapper.getLogin(id);
	}

}
