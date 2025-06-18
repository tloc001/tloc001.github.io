package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Mapper.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdersAPI {
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrdersMapper ordersMapper;

	@GetMapping("/admin/Orders")
	public ResponseEntity<List<OrdersDTOS>> getAllOrdersApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<OrdersDTOS> orders = new ArrayList<OrdersDTOS>();
		ordersService.ordersFindAll(page, size).forEach(e -> {
			orders.add(ordersMapper.mapper(e));
		});
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/admin/Orders/{id}")
	public ResponseEntity<Orders> getOrdersByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Orders orders =(Orders)ordersService.ordersFindById(id).orElse(new Orders());
		return ResponseEntity.ok(orders);
	}
	@PostMapping("/admin/Orders")
	public ResponseEntity<Orders> addNewOrders(@RequestBody OrdersDTOS entity) {
	    
	    Orders saved = ordersService.ordersSave(ordersMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Orders/{id}")
	public ResponseEntity<?> updateOrders( @PathVariable int id,@RequestBody OrdersDTOS entity) {
			try {
			Orders  isExist = ordersService.ordersFindById(id).orElse(null);
			if (isExist != null) {
				Orders  update = ordersMapper.mapperToObject(entity);
				ordersService.ordersSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/Orders/{id}")
	public ResponseEntity<Void> deleteOrders(@PathVariable int id) {
	    ordersService.ordersDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
