package com.example.demo.authService.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-controller")
public class DemoController {

  @GetMapping
  /* Test  */
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Test");
  }

}
