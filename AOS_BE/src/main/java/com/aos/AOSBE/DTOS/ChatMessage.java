package com.aos.AOSBE.DTOS;

import lombok.Data;

@Data
public class ChatMessage {
	private String from;
	private String to;
	private String content;
}