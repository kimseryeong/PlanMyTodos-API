package com.todoweb.api.dto.todo;

import java.time.LocalDateTime;

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
    private LocalDateTime startAt;
    private LocalDateTime endAt;
	
}
