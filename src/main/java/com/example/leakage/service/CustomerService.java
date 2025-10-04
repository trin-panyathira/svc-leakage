package com.example.leakage.service;

import com.example.leakage.dto.CAItemDto;
import com.example.leakage.dto.UserInfoDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerService {

	// Mock data storage
	private final Map<String, List<CAItemDto>> caDataByCard = new ConcurrentHashMap<>();
	private final Map<String, UserInfoDto> userInfoByCaId = new ConcurrentHashMap<>();

	public CustomerService() {
		initializeMockData();
	}

	private void initializeMockData() {
		// Mock data for ID Card "1234567890123"
		List<CAItemDto> caList1 = new ArrayList<>();
		caList1.add(new CAItemDto("123456789012345", "John", "Doe", 500000.0));
		caList1.add(new CAItemDto("123456789012346", "Jane", "Smith", 750000.0));
		caDataByCard.put("1234567890123", caList1);

		// Mock data for ID Card "9876543210987"
		List<CAItemDto> caList2 = new ArrayList<>();
		caList2.add(new CAItemDto("987654321098765", "Alice", "Johnson", 300000.0));
		caDataByCard.put("9876543210987", caList2);

		// Mock data for ID Card "1111111111111"
		List<CAItemDto> caList3 = new ArrayList<>();
		caList3.add(new CAItemDto("111111111111111", "Bob", "Wilson", 1000000.0));
		caDataByCard.put("1111111111111", caList3);

		// User info for each CA ID
		userInfoByCaId.put("123456789012345", new UserInfoDto(
			"John", "Doe", "Advisor A", "081-234-5678", 
			500000.0, 3.5, 4.0, 4.5
		));
		userInfoByCaId.put("123456789012346", new UserInfoDto(
			"Jane", "Smith", "Advisor B", "082-345-6789", 
			750000.0, 3.8, 4.2, 4.7
		));
		userInfoByCaId.put("987654321098765", new UserInfoDto(
			"Alice", "Johnson", "Advisor C", "083-456-7890", 
			300000.0, 3.2, 3.7, 4.2
		));
		userInfoByCaId.put("111111111111111", new UserInfoDto(
			"Bob", "Wilson", "Advisor D", "084-567-8901", 
			1000000.0, 4.0, 4.5, 5.0
		));
	}

	public List<CAItemDto> searchByIdCard(String idCard) {
		return caDataByCard.getOrDefault(idCard, new ArrayList<>());
	}

	public UserInfoDto getUserInfo(String caId) {
		UserInfoDto userInfo = userInfoByCaId.get(caId);
		if (userInfo == null) {
			throw new IllegalArgumentException("User info not found for CA ID: " + caId);
		}
		return userInfo;
	}
}
