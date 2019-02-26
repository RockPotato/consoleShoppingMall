package dao;

import vo.AdminVO;

public class AdminDaoImpl implements AdminDao {

	@Override
	public AdminVO SelectAdmin(String key) { // 마스터키를 비교하는 메서드
		AdminVO admin = new AdminVO();
		if(key.equals(admin.getMASTER_KEY()))
		{
			return admin;
		}
		return null;
	}

}
