package edu.xinan.demo.rest;

import lombok.*;

/**
 *
 * @author xinan
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    
    private Long id;
    private String name;
    private Integer age;
    
}
