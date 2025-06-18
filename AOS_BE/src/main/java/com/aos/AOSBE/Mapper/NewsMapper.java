package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class NewsMapper {
	
	public NewsDTOS mapper(News entity) {
		return new NewsDTOS(
				    entity.getId(),
				    entity.getImage(),
				    entity.isHome(),
				    entity.getDescription(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public News mapperToObject(NewsDTOS entity) {
		return new News(
					entity.getId(),
					entity.getImage(),
					entity.isHome(),
					entity.getDescription(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
