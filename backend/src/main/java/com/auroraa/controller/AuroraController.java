package com.auroraa.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aurora")
public class AuroraController {
    
    @GetMapping("/status")
    public String status() {
        return "🌟 AuroraAI está online!";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "Olá! 👋 Sou AuroraAI, seu assistente inteligente!";
    }
}
