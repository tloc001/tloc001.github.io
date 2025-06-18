package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "returns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Returns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "reason")
    private String reason;
	
    @Column(name = "image1")
    private String image1;
	
    @Column(name = "image2")
    private String image2;
	
    @Column(name = "image3")
    private String image3;
	
    @Column(name = "is_returned_money")
    private String isReturnedMoney;
	
    @Column(name = "is_returned_item")
    private String isReturnedItem;
	
    @Column(name = "status")
    private String status;
	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "order_product_item_id")
    private OrderItems orderItems;





}
