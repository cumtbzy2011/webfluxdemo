/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private String title;
    private String content;
    
}
