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
@Table(name = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "rating")
    private int rating;
	
    @Column(name = "comment")
    private String comment;
	
    @Column(name = "images")
    private String images;
	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
   
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;
    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItems productItems;





}
