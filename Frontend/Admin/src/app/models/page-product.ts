import { ProductResponse } from "./product-response"


export class PageProduct {
    data: ProductResponse[];
    size: number;
    offset: number;
    totalPage: number;
    totalElements: number;
}
