package com.green.board.dto;

import lombok.Data;

@Data  // Getter/setter/toString/GashCodem equals 재정의, 기본생성자 BoardDto()
public class BoardDto {
	// Fields
	private  int      idx;
	private  String   menu_id;
	private  String   title;
	private  String   content;
	private  String   writer;
	private  String   regdate;
	private  int      hit;
}
