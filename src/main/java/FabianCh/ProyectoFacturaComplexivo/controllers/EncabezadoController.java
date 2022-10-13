package FabianCh.ProyectoFacturaComplexivo.controllers;

import FabianCh.ProyectoFacturaComplexivo.models.entity.Cliente;
import FabianCh.ProyectoFacturaComplexivo.models.entity.DetalleF;
import FabianCh.ProyectoFacturaComplexivo.models.entity.EncabezadoFactura;
import FabianCh.ProyectoFacturaComplexivo.models.entity.Producto;
import FabianCh.ProyectoFacturaComplexivo.models.services.ClienteServiceImp;
import FabianCh.ProyectoFacturaComplexivo.models.services.DetalleServiceImp;
import FabianCh.ProyectoFacturaComplexivo.models.services.EncabezadoServiceImp;
import FabianCh.ProyectoFacturaComplexivo.models.services.ProductoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/factura")
public class EncabezadoController {

    @Autowired
    private EncabezadoServiceImp encabezado;

    @Autowired
    private ClienteServiceImp clienteService;

    @Autowired
    private EncabezadoFactura factura;

    @Autowired
    private DetalleServiceImp detalleService;
    @Autowired
    private ProductoServiceImp productoService;

    @Autowired
    private Cliente cliente;


    @RequestMapping(value = "/listar",method = RequestMethod.GET)
    public String ListarFacturas(Model model){
        model.addAttribute("encabezados",encabezado.findAll());
        return "listar_facturas";
    }
    @RequestMapping(value = "/forml")
    public String mostrarForm(Map<String,Object> model){
           EncabezadoFactura factura1=new EncabezadoFactura();
        //DetalleF itemFactura = new DetalleF();
        model.put("itemfactura",new DetalleF());
        model.put("listitems",factura1.getItems());
        model.put("factura",factura1);
        model.put("cliente",factura1.getCliente());
        model.put("clientes",clienteService.findAll());
        model.put("productos",productoService.findAll());
        return "form_factura";
    }

    @RequestMapping(value = "/form")
    public String crearFactura(Map<String,Object> model){

        //DetalleF itemFactura = new DetalleF();
        model.put("itemfactura",new DetalleF());
        model.put("listitems",factura.getItems());
        model.put("factura",factura);
        model.put("cliente",factura.getCliente());
        model.put("clientes",clienteService.findAll());
        model.put("productos",productoService.findAll());
        return "form_factura";
    }
    @RequestMapping(value = "/form",method = RequestMethod.POST)
    public String guardar(@Valid EncabezadoFactura factura, BindingResult result, Model model, SessionStatus status){
        if(result.hasErrors()){
            result.getFieldErrors().forEach(System.out::println);
            DetalleF itemFactura = new DetalleF();
            model.addAttribute("itemfactura",itemFactura);
            model.addAttribute("listitems",factura.getItems());
            model.addAttribute("factura",factura);
            model.addAttribute("cliente",factura.getCliente());
            model.addAttribute("clientes",clienteService.findAll());
            model.addAttribute("productos",productoService.findAll());
            return "form_factura";
        }
        EncabezadoFactura fr= new EncabezadoFactura();
        List<DetalleF> items=new ArrayList<>();
        items=this.factura.getItems();
        factura.setCliente(cliente);
        factura.setTotal(this.factura.getTotal());
        fr=encabezado.save(factura);
        System.out.println("Factura creada"+factura.getCodigoe());
        for(int i=0;i<items.size();i++){
            DetalleF itf=new DetalleF();
            Producto p= items.get(i).getProducto();
            p.setCodigop(items.get(i).getProducto().getCodigop());
            itf.setFactura(factura);
            itf.setProducto(p);
            itf.setCantidad(items.get(i).getCantidad());
            itf.setSubtotal(items.get(i).getSubtotal());
            detalleService.save(itf);

            System.out.println("Item creado");
            p.setStock(p.getStock()- itf.getCantidad());
            productoService.save(p);
            System.out.println("Producto Creado");
        }
        status.setComplete();
        return "redirect:/factura/listar";
    }

    @RequestMapping(value = "/cliente/{id}")
    public String clienteFactura (@PathVariable(value = "id") Long id,Map<String,Object> model){


        cliente=clienteService.findOne(id);
        factura.setCliente(cliente);
        model.put("listitems",factura.getItems());
        model.put("clientes",clienteService.findAll());
        model.put("productos",productoService.findAll());
        model.put("factura",factura);
        model.put("cliente",cliente);

        System.out.println(clienteService.findOne(id).getCodigo()+"id");
        return "form_factura";
    }

    @RequestMapping(value = "/info/{id}")
    public String informacionFactura(@PathVariable(value = "id") Long id, Map<String,Object> model){

        model.put("factura",encabezado.findOne(id));
        //model.put("cliente",c);
        List<DetalleF> itf=new ArrayList<>();
        detalleService.findAll().stream().forEach(i->{
            if(i.getFactura().getCodigoe().equals(id)){
                itf.add(i);
            }
        });
        model.put("itemsfactura",itf);

        return "vista_factura";
    }

    @RequestMapping(value = "/form/{id}")
    public String editarFactura (@PathVariable(value = "id") Long id, Map<String, Object> model){

        EncabezadoFactura factura = null;

        if(id>0){
            factura= encabezado.findOne(id);
        }else{
            return "redirect:/factura/listar";
        }
        model.put("factura",factura);
        model.put("titulo","Editar Factura");

        return "form_factura";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar (@PathVariable(value = "id") Long id){
        if(id>0){
            encabezado.delete(id);
        }
        return "redirect:/factura/listar";
    }

}
