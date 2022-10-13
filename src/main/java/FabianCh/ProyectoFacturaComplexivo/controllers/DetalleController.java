package FabianCh.ProyectoFacturaComplexivo.controllers;

import FabianCh.ProyectoFacturaComplexivo.models.entity.DetalleF;
import FabianCh.ProyectoFacturaComplexivo.models.entity.EncabezadoFactura;
import FabianCh.ProyectoFacturaComplexivo.models.entity.Producto;
import FabianCh.ProyectoFacturaComplexivo.models.services.DetalleServiceImp;
import FabianCh.ProyectoFacturaComplexivo.models.services.ProductoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/detalle")
public class DetalleController {

    @Autowired
    private DetalleServiceImp detalleService;
    @Autowired
    private ProductoServiceImp productoService;

    @Autowired
     private EncabezadoFactura factura;

    @Autowired
    private Producto producto;



    @RequestMapping(value = "/form")
    public String crear(Map<String,Object> model){

        List<Producto> productos = productosDisponibles(productoService.findAll());
        DetalleF item=new DetalleF();
        model.put("producto",producto);
        model.put("itemfactura",item);
        model.put("productos",productos);
        return "form_item";
    }
    @RequestMapping(value = "/form",method = RequestMethod.POST)
    public String guardarDetalle( @Valid DetalleF itemfactura, BindingResult result, SessionStatus status){

        if(result.hasErrors()){
            result.getFieldErrors().forEach(System.out::println);
            return "redirect:/detalle/form";
        }
        itemfactura.setCodigoD(Long.parseLong(String.valueOf(factura.getItems().size())));
        itemfactura.setProducto(producto);
        itemfactura.setSubtotal(producto.getPrecio()*itemfactura.getCantidad());
        System.out.println("Subtotal item: "+producto.getPrecio()*itemfactura.getCantidad());
        factura.setTotal(factura.getTotal()+itemfactura.getSubtotal());
        factura.getItems().add(itemfactura);
        System.out.println(itemfactura.getCodigoD()+" ");
        status.setComplete();
        return "redirect:/factura/form";
    }

    @RequestMapping(value = "/form/{id}")
    public String editarDetalle (@PathVariable(value = "id") Long id, Map<String, Object> model){

        DetalleF itemFactura = null;
        if(id>0){
            itemFactura= detalleService.findOne(id);
        }else{
            return "redirect:/detalle/listar";
        }
        model.put("item",itemFactura);

        return "form_item";
    }

    @RequestMapping(value = "/asignar/{pid}")
    private String asignarDetalle(@PathVariable(name = "pid") Long id,Model model){
        DetalleF item=new DetalleF();
        List<Producto> listp=new ArrayList<>();
        listp=productosDisponibles(productoService.findAll());

        item.setCodigoD(Long.parseLong(String.valueOf(factura.getItems().size())));
        producto=productoService.findOne(id);
        item.setProducto(producto);
        model.addAttribute("producto",producto);
        model.addAttribute("itemfactura",item);
        model.addAttribute("productos",listp);
        return "form_item";
    }
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar (@PathVariable(value = "id") Long id){
        if(id>0){
            detalleService.delete(id);
        }
        return "redirect:/detalle/listar";
    }

    @RequestMapping(value = "/eliminarpi/{id}")
    public String eliminarPi (@PathVariable(value = "id") Long id){
        DetalleF fe=new DetalleF();
        double total=0.0;
        for(int i=0;i<factura.getItems().size();i++){
            if(factura.getItems().get(i).getCodigoD().equals(id)){
                fe=factura.getItems().get(i);
                factura.getItems().remove(fe);
                total= factura.getTotal()-fe.getSubtotal();
            }
        }
     factura.setTotal(total);

        return "redirect:/factura/form";
    }
    private List<Producto> productosDisponibles(List<Producto> productos){
        List<Producto> pfinal= new ArrayList<>();
        pfinal=productos;
        for(int i=0;i<=productos.size();i++){
            for(int f=0;f<=factura.getItems().size();f++){

                try{
                    if(productos.get(i).getCodigop().equals(factura.getItems().get(f).getProducto().getCodigop())){

                        pfinal.get(i).setStock(pfinal.get(i).getStock()-factura.getItems().get(f).getCantidad());
                    }
                } catch (Exception e){

                }
            }
        }
        return pfinal;
    }
}
