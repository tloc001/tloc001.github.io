package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ReturnsService {
	@Autowired
    private ReturnsRepository returnsRepository;

    public List<Returns> returnsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return returnsRepository.findAll(pageable).getContent();
    }
    @Transactional
    public Returns returnsSave(Returns returns) {
        return returnsRepository.save(returns);
    }
    public Optional<Returns> returnsFindById(int id) {
        return returnsRepository.findById(id);
    }
    @Transactional
    public void returnsDeleteById(int id) {
        returnsRepository.deleteById(id);
    }
}