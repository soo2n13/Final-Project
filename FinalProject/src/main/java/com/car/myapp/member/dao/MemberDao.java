package com.car.myapp.member.dao;

import javax.servlet.http.HttpSession;

import com.car.myapp.member.dto.MemberDto;
import com.car.myapp.member.dto.verificationDto;

public interface MemberDao {
	public boolean insert(MemberDto dto);
	public boolean checkId(String user_id);
	public boolean verfication(verificationDto dto);
	public boolean checkVCode(verificationDto dto,HttpSession sessionV);
	public boolean deleteVCode(String userPhone);
	public void deleteVCodeMail(String userMail);
	public MemberDto isExistId(String user_id);
	public String getUserId(String user_phone);
	public MemberDto getUserInfo(String user_id);
	public boolean changePwd(MemberDto dto);
	public boolean checkPhone(String user_phone);
}
