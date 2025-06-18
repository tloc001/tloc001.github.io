package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ShippingMethodsMapper {
	
	public ShippingMethodsDTOS mapper(ShippingMethods entity) {
		return new ShippingMethodsDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getDescription(),
				    entity.isActive(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public ShippingMethods mapperToObject(ShippingMethodsDTOS entity) {
		return new ShippingMethods(
					entity.getId(),
					entity.getName(),
					entity.getDescription(),
					entity.isActive(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
