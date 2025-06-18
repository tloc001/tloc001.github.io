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
@Table(name = "price_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "price")
    private double price;
	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
   
    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItems productItems;





}
