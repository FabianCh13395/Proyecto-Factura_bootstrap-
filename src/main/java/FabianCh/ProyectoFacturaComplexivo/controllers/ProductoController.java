package FabianCh.ProyectoFacturaComplexivo.controllers;


import FabianCh.ProyectoFacturaComplexivo.models.entity.Producto;
import FabianCh.ProyectoFacturaComplexivo.models.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/producto")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @RequestMapping(value = "/listar",method = RequestMethod.GET)
    public String ListarCliente(Model model){
        model.addAttribute("productos",productoService.findAll());
        return "listar_producto";
    }
//Agregar
    @GetMapping(value = "/agregar")
    public String agregarCliente(Model model) {
        model.addAttribute("titulo","Ingreso de Productos");
        model.addAttribute("producto", new Producto());
        return "form_producto";
    }
    @PostMapping(value = "/agregar")
    public String guardarCliente(@ModelAttribute @Valid Producto producto, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "form_producto";
        }
        productoService.save(producto);
        redirectAttrs
                .addFlashAttribute("mensaje", "Producto guardado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/producto/listar";
    }
    //Editar
    @PostMapping(value = "/editar/{id}")
    public String actualizarProducto(@ModelAttribute @Valid Producto producto, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (producto.getCodigop() != null) {
                return "form_producto";
            }
            return "redirect:listar_producto";
        }
        productoService.save(producto);
        redirectAttrs
                .addFlashAttribute("mensaje", "Producto editado exitosamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/producto/listar";
    }
    @GetMapping(value = "/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo","Editar Producto");
        model.addAttribute("producto", productoService.findOne(id));
        return "form_producto";
    }

    //Eliminar
    @RequestMapping(value = "/eliminar/{id}")
    public String Eliminar( @PathVariable(value = "id")Long id , RedirectAttributes attributes){
        if(id>0){
            productoService.delete(id);
        }
        attributes.addFlashAttribute("mensaje","Producto Eliminado Correctamente")
                .addFlashAttribute("clase","warning");
        return "redirect:/producto/listar";
    }

}
