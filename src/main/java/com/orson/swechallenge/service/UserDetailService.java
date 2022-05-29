package com.orson.swechallenge.service;

import com.orson.swechallenge.entity.UserDetail;
import com.orson.swechallenge.helper.CSVHelper;
import com.orson.swechallenge.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class UserDetailService {

	@Autowired
	UserDetailRepository userDetailRepository;

	public List<UserDetail> getUserDetails (String min, String max, String offset, String limit, String sort) {
		Float parsedFloatMin = Float.parseFloat(min);
		Float parsedFloatMax = Float.parseFloat(max);
		Integer parsedIntegerOffset = Integer.parseInt(offset);
		Integer parsedIntegerLimit = Integer.parseInt(limit);
		// all default value
		if(sort.equals("")) {
			return userDetailRepository.getUserDetailWithNoSorting(
					parsedFloatMin,
					parsedFloatMax,
					parsedIntegerOffset,
					parsedIntegerLimit
			);
		} else if(sort.equalsIgnoreCase("name")) {
			return userDetailRepository.getUserDetailSortByName(
					parsedFloatMin,
					parsedFloatMax,
					parsedIntegerOffset,
					parsedIntegerLimit
			);
		} else if(sort.equalsIgnoreCase("salary")) {
			return userDetailRepository.getUserDetailSortBySalary(
					parsedFloatMin,
					parsedFloatMax,
					parsedIntegerOffset,
					parsedIntegerLimit
			);
		}
		return null;
	}

	public boolean uploadUserDetail(InputStream inputStream) throws IOException {
		try {
			List<UserDetail> user = CSVHelper.read(UserDetail.class, inputStream);
			user.forEach(u ->  {
				if(u.getSalary() >= 0.0) {
					UserDetail userDetail = userDetailRepository.findUserDetailByNameIgnoreCase(u.getName());
					if (userDetail != null) {
						userDetail.setSalary(u.getSalary());
						userDetailRepository.save(userDetail);
					} else {
						UserDetail newUser = new UserDetail();
						newUser.setName(u.getName());
						newUser.setSalary(u.getSalary());
						userDetailRepository.save(newUser);
					}
				}
			} );
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

}
