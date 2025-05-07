package com.todoweb.api.todo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequestDTO {
	
	private Long id;
    private String title;
    private boolean completed;
    private String content;
    private LocalDate startAt;
    private LocalDate endAt;
	
    private String email;
    private LocalDate currentAt;
}
