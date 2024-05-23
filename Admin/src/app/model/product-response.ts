export class ProductResponse {
    productId: number;
    categoryId: number;
    productName: string;
    avatar: string;
    price: number;
    status: string;
    discount: number;
    quantity: number;

    constructor(
        productId: number,
        categoryId: number,
        productName: string,
        avatar: string,
        price: number,
        status: string,
        discount: number,
        quantity: number
      ) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.avatar = avatar;
        this.price = price;
        this.status = status;
        this.discount = discount;
        this.quantity = quantity;
      }
}
