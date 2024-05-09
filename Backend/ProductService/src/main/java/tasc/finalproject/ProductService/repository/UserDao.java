package tasc.finalproject.ProductService.repository;

import tasc.finalproject.ProductService.entity.Product;

import java.util.List;

public class UserDao implements DaoProductRepository{
    @Override
    public List<Product> getProductAll() {
        String sql = "SELECT p.product_id, p.product_name, p.category_id," +
                " p.avartar_image_product, p.price, p.status, p.discount FROM products p";

        return null;
    }
}
