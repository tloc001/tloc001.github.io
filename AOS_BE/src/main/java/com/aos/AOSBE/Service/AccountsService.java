package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.DTOS.ChangePasswordDTOS;
import com.aos.AOSBE.DTOS.RegisterRequestDTO;
import com.aos.AOSBE.DTOS.UpdateProfileDTO;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Entity.UserAddresses;
import com.aos.AOSBE.Repository.AccountsRepository;
import com.aos.AOSBE.Repository.AuthoritiesRepository;
import com.aos.AOSBE.Repository.RolesRepository;
import com.aos.AOSBE.Repository.UserAddressesRepository;

@Service
public class AccountsService {
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private AuthoritiesRepository authorityRepository;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private UserAddressesRepository addressRepository;
	@Autowired
	private GenericSpecificationBuilder specBuilder;

	public List<Accounts> accountsFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return accountsRepository.findAll(pageable).getContent();
	}

	public List<Accounts> accountsFindWithFilter(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Accounts> spec = specBuilder.buildFilter(filters);
		return accountsRepository.findAll(spec, pageable).getContent();
	}

	public Accounts accountsSave(Accounts accounts) {
		return accountsRepository.save(accounts);
	}

	public Optional<Accounts> accountsFindById(int id) {
		return accountsRepository.findById(id);
	}

	public void accountsDeleteById(int id) {
		accountsRepository.deleteById(id);
	}

	public Optional<Accounts> accountsFindByEmail(String email) {
		return accountsRepository.findByEmail(email);
	}

	@Transactional
	public Accounts registerByEmail(RegisterRequestDTO registerRequestDTO) {
		Accounts accounts = new Accounts();
		accounts.setEmail(registerRequestDTO.getEmail());
		accounts.setPassword(new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword()));
		accounts.setPhone(registerRequestDTO.getPhone());
		accounts.setFullname(registerRequestDTO.getFullname());
		Authorities authority = new Authorities();
		accounts = accountsRepository.save(accounts);
		authority.setAccounts(accounts);
		authority.setRoles(rolesRepository.findByName("USER").get());
		authorityRepository.save(authority);
		return accountsRepository.save(accounts);
	}

	@Transactional
	public void changePassword(ChangePasswordDTOS dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Accounts account = accountsRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

		if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
			throw new RuntimeException("Mật khẩu mới và xác nhận không khớp");
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		account.setPassword(encoder.encode(dto.getNewPassword()));
		accountsRepository.save(account);
	}

	@Transactional
	public void updateProfile(UpdateProfileDTO dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Accounts account = accountsRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
		account.setFullname(dto.getFullname());
		account.setEmail(dto.getEmail());
		account.setPhone(dto.getPhone());
		account.setAvatar(dto.getAvatar());
		accountsRepository.save(account);
		Optional<UserAddresses> optionalAddress = addressRepository.findByAccountsIdAndIsDefaultTrue(account.getId());
		UserAddresses address = optionalAddress.orElse(new UserAddresses());

		address.setRecipientName(dto.getFullname());
		address.setPhone(dto.getPhone());
		address.setProvince(dto.getProvinceName() + "");
		address.setDistrict(dto.getDistrictName() + "");
		address.setWard(dto.getWardName());
		address.setStreet(dto.getAddress());
		address.setLabel("Nhà riêng");
		address.setDefault(true);
		address.setAccounts(account);

		addressRepository.save(address);
	}

}