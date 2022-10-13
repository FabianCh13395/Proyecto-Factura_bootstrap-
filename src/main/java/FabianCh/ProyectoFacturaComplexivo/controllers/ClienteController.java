package FabianCh.ProyectoFacturaComplexivo.controllers;


import FabianCh.ProyectoFacturaComplexivo.models.entity.Cliente;
import FabianCh.ProyectoFacturaComplexivo.models.services.IClienteService;
import FabianCh.ProyectoFacturaComplexivo.models.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/cliente")
public class ClienteController {



    @Autowired
    private IClienteService service;

    @Autowired
    private IProductoService productoService;

    @RequestMapping(value = "/listar",method = RequestMethod.GET)
    public String ListarCliente(Model model){
        model.addAttribute("clientes",service.findAll());
        return "listar";
    }
    @RequestMapping(value = "/listarhome",method = RequestMethod.GET)
    public String ListarEnHome(Model model){
        model.addAttribute("clientes",service.findAll());
        model.addAttribute("productos",productoService.findAll());
        return "home";
    }
/*
    @RequestMapping(value = "/form")
    public String Crear(Map<String,Object> model,@Valid Cliente cliente ,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "form";
        }
        model.put("cliente",cliente);
        return "form";
    }
    @RequestMapping(value = "/form",method = RequestMethod.POST)
    public String Guardar( Cliente cliente,RedirectAttributes redirectAttributes){
        service.save(cliente);
        redirectAttributes.addFlashAttribute("mensaje","Cliente Agregado Correctamente").addFlashAttribute("clase","success");

        return "redirect:listar";
    }

 */

    @GetMapping(value = "/agregar")
    public String agregarCliente(Model model) {
        model.addAttribute("titulo","Ingreso de  Clientes");
        model.addAttribute("cliente", new Cliente());
        return "form";
    }
    @PostMapping(value = "/agregar")
    public String guardarCliente(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        service.save(cliente);
        redirectAttrs
                .addFlashAttribute("mensaje", "Cliente guardado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/cliente/listar";
    }
    //Editar
/*
    @RequestMapping(value = "/form/{id}")
    public String Editar(@PathVariable(value ="id") Long id,BindingResult result, Map<String,Object> model){
        Cliente cliente=null;
        if(result.hasErrors()){
            return "form";
        }
        if(id>0){
            cliente=service.findOne(id);
        }else{
            return "redirect:/cliente/listar";
        }
        model.put("cliente",cliente);
        model.put("titulo","Actualización del Cliente");
        return "form";
    }
    */
    @PostMapping(value = "/editar/{id}")
    public String actualizarProducto(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (cliente.getCodigo() != null) {
                return "form";
            }
            return "redirect:listar";
        }
        service.save(cliente);
        redirectAttrs
                .addFlashAttribute("mensaje", "Cliente editado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:listar";
    }

    @GetMapping(value = "/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo","Editar Cliente");
        model.addAttribute("cliente", service.findOne(id));
        return "form";
    }



    //eliminar
    @RequestMapping(value = "/eliminar/{id}")
    public String Eliminar( @PathVariable(value = "id")Long id,RedirectAttributes attributes){
        if(id>0){
            service.delete(id);
        }
        attributes.addFlashAttribute("mensaje","Cliente Eliminado Correctamente")
                .addFlashAttribute("clase","warning");
        return "redirect:/cliente/listar";
    }
}
