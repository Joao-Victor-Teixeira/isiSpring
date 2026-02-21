package br.com.joaodev.webtestapp.controller;

import br.com.joaodev.isispring.annotations.IsiController;
import br.com.joaodev.isispring.annotations.IsiGetMethod;

@IsiController
public class HelloController {

    @IsiGetMethod("/hello")
    public String sayHelloWorld(){
        return "Hello World";
    }

}
