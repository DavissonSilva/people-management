package com.logicalis.controleponto.resource;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logicalis.controleponto.implementandoService.EmpresaServiceImpl;

@RestController
@RequestMapping(value = "logicalis/lancamentos")
@CrossOrigin(origins ="*")
public class LancamentosResource {

	private static final Logger lOG = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	
	
}
