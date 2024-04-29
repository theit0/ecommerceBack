package Base.Ecommerce.ServiceEmail;

import Base.Ecommerce.Entity.Cliente;
import Base.Ecommerce.Entity.DetallePedido;
import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class GmailServiceImpl implements GmailService{
    @Value("${DIRECCION_GMAIL}") // Inyecta el valor de la variable de entorno MP_ACCESS_TOKEN
    private String direccionGmail;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(Pedido pedido, Cliente cliente) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();


        try {
            //obtengo la informacion del pedido
            Long nroPedido = pedido.getId();
            String receptor = cliente.getEmail();
            LocalDateTime fecha = pedido.getFecha();
            List<DetallePedido> detalles = pedido.getDetallesPedido();
            String montoTotal = pedido.getMontoTotal();
            Integer tiempoDemora = pedido.getDemora();

            //Construir el contenido del correo
            StringBuilder messageBody = new StringBuilder();
            messageBody.append("¡Pedido confirmado!\n\n");
            messageBody.append("Hola ").append(cliente.getNombre()).append(", tu pedido ya fue confirmado y está próximo a prepararse.\n\n");
            messageBody.append("Fecha: ").append(fecha).append("\n");
            messageBody.append("Pedido: ").append(nroPedido).append("\n");
            messageBody.append("Tiempo estimado: ").append(tiempoDemora).append("  Minutos").append("\n\n");
            messageBody.append("Detalles del pedido:\n");

            for (DetallePedido detalle : detalles) {

                Producto producto = detalle.getProducto();
                int cantidad = detalle.getQuantity();
                double subtotal = detalle.getSubtotalPedido();

                // Agregar detalle al cuerpo del correo electrónico
                messageBody.append("(").append(cantidad).append(") ").append(producto.getTitle()).append("    "+ subtotal +"$").append("\n");
            }
            messageBody.append("\nTotal: $").append(montoTotal).append("\n\n");
            messageBody.append("Para más información puedes llamar al 2617000018 o escribir a zandy burguer\n\n");
            messageBody.append("¡Gracias por tu compra!");
            //informacion del email
            mailMessage.setFrom(direccionGmail);
            mailMessage.setTo(receptor);
            mailMessage.setSubject("¡Pedido Confirmado!");
            mailMessage.setText(messageBody.toString());
            mailSender.send(mailMessage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    }

