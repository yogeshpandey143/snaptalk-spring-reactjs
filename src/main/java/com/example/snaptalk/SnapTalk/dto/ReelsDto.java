package com.example.snaptalk.SnapTalk.dto;



import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ReelsDto {
	
	private Long id;
	private String title;
	private String video;
	
	private UserDto user;

}
