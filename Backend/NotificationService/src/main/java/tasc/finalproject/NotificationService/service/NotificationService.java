package tasc.finalproject.NotificationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasc.finalproject.NotificationService.email.EmailService;
import tasc.finalproject.NotificationService.model.CartItemResponse;
import tasc.finalproject.NotificationService.model.OrderSendMail;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    public void sendMailOrder(OrderSendMail orderSendMail){
        StringBuilder productsHtml = new StringBuilder();

        for (CartItemResponse item : orderSendMail.getCartDto().getCartItemResponseList()) {
            double subtotal;
            if (item.getDiscount()<=0){
                subtotal = item.getPrice()*item.getQuantity();
            } else {
                subtotal = item.getPrice()*item.getQuantity()*item.getDiscount();
            }
            String productHtml = "<tr>\n" +
                    "<td style=\"vertical-align: middle;padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\"><img src=\"" + item.getImg() + "\" alt=\"Product\" style=\"max-width: 50px;height: auto;margin-bottom: 10px;vertical-align: middle;\"> " + item.getProductName() + "</td>\n" +
                    "<td style=\"vertical-align: middle;padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">" + item.getQuantity() + "</td>\n" +
                    "<td style=\"vertical-align: middle;padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">" + item.getPrice() + "</td>\n" +
                    "<td style=\"vertical-align: middle;padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">" + item.getDiscount() + "</td>\n" +
                    "<td style=\"vertical-align: middle;padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">" + subtotal + "</td>\n" +
                    "</tr>\n";
            productsHtml.append(productHtml);
        }

        String htmlContent = "<div class=\"container\" style=\"font-family: Arial, sans-serif;\n" +
                "    background-color: #f4f4f4;\n" +
                "    margin: 0;\n" +
                "    padding: 0;\">\n" +
                "    <div style=\"@media screen and (max-width: 600px) { max-width: 100%; border-radius: 0; }; max-width: 600px;\n" +
                "    margin: 20px auto;\n" +
                "    background-color: #fff;\n" +
                "    padding: 20px;\n" +
                "    border-radius: 8px;\n" +
                "    box-shadow: 0 0 10px rgba(0,0,0,0.1); \">\n" +
                "        <h2 style=\"color: #333;\">Order Successfully</h2>\n" +
                "        <div class=\"customer-info\">\n" +
                "            <h3>Customer Information</h3>\n" +
                "            <p><strong>Name: </strong>" + orderSendMail.getFirst_name() + " " + orderSendMail.getLast_name() + " </p>\n" +
                "            <p><strong>Email: </strong>" + orderSendMail.getEmail() + "  </p>\n" +
                "            <p><strong>Address: </strong>" + orderSendMail.getAddress() + " " + orderSendMail.getCity() + " </p>\n" +
                "        </div>\n" +
                "        <div class=\"product-info\">\n" +
                "            <h3>Product Information</h3>\n" +
                "            <table style=\"width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin-top: 20px;\">\n" +
                "                <tr>\n" +
                "                    <th style=\" background-color: #f2f2f2; padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">Name</th>\n" +
                "                    <th style=\" background-color: #f2f2f2; padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">Color/Size</th>\n" +
                "                    <th style=\" background-color: #f2f2f2; padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">Quantity</th>\n" +
                "                    <th style=\" background-color: #f2f2f2; padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">Price</th>\n" +
                "                    <th style=\" background-color: #f2f2f2; padding: 10px;text-align: left;border-bottom: 1px solid #ddd;\">Subtotal</th>\n" +
                "                </tr>\n" +
                productsHtml.toString() +
                "            </table>\n" +
                "        </div>\n" +
                "        <div class=\"total-wrapper\" style=\"margin-top: 20px; text-align: right;\">\n" +
                "            <p style=\"margin-bottom: 0;\"><strong>Subtotal:</strong> $" + Math.round(orderSendMail.getSubtotal() * 100.0) / 100.0 + "</p>\n" +
                "            <p style=\"margin-bottom: 0;\"><strong>Shipping:</strong> $" + Math.round(orderSendMail.getPriceDelivery() * 100.0) / 100.0 + "</p>\n" +
                "            <p style=\"display: inline-block; margin-left: 20px; margin-bottom: 0;\"><strong>Total:</strong> $" + Math.round(orderSendMail.getSubtotal() * orderSendMail.getPriceDelivery() * 100.0) / 100.0 + "</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n";
        emailService.sendMail(orderSendMail.getEmail(), "Orders by " + orderSendMail.getLast_name() + " " + orderSendMail.getFirst_name(), htmlContent);
    }

}
