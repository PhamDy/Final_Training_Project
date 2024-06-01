import { ProductResponse } from "./product-response";

export class Page<T> {
        data: T[];
    size: number;
    offset: number;
    totalPage: number;
    totalElements: number;
}
