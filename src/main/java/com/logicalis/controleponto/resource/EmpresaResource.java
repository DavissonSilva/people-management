package com.logicalis.controleponto.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logicalis.controleponto.entity.Empresa;
import com.logicalis.controleponto.repository.EmpresaRepository;

@RestController
@RequestMapping(value = "logicalis")
@CrossOrigin(origins = "*")
public class EmpresaResource {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@PostMapping
	public Empresa savetest(@RequestBody Empresa empresa) {
		System.out.println(empresa);
		return this.empresaRepository.save(empresa);
	}
	@GetMapping
	public List<Empresa> getTest() {		
		return (List<Empresa>) this.empresaRepository.findAll();
	}

}
