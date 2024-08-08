import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Product } from './product.class';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

    private static productslist: Product[] = null;
    private products$: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);

    constructor(private http: HttpClient) { }

    getProducts(): Observable<Product[]> {
        if( ! ProductsService.productslist )
        {
            this.http.get<any>('http://localhost:8080/products').subscribe(data => {

                ProductsService.productslist = data;

                this.products$.next(ProductsService.productslist);
            });
        }
        else
        {
            this.products$.next(ProductsService.productslist);
        }

        return this.products$;
    }

    create(prod: Product): Observable<Product[]> {

        this.http.post<Product>('http://localhost:8080/products',prod, {observe: 'response'}).subscribe(response => {
            if(response.status !== 201) return this.products$

            ProductsService.productslist.push(response.body);
            this.products$.next(ProductsService.productslist);
        })

        return this.products$;
    }

    update(prod: Product): Observable<Product[]>{

        this.http.patch<Product>(`http://localhost:8080/products/${prod.id}`,prod, {observe: 'response'}).subscribe(response => {
            if(response.status !== 200) return this.products$

            ProductsService.productslist.forEach(element => {
                if(element.id == response.body.id)
                {
                    element.name = response.body.name;
                    element.category = response.body.category;
                    element.code = response.body.code;
                    element.description = response.body.description;
                    element.image = response.body.image;
                    element.inventoryStatus = response.body.inventoryStatus;
                    element.price = response.body.price;
                    element.quantity = response.body.quantity;
                    element.rating = response.body.rating;
                }
            });
            this.products$.next(ProductsService.productslist);
        })

        return this.products$;
    }


    delete(id: number): Observable<Product[]>{
        this.http.delete(`http://localhost:8080/products/${id}`, {observe: 'response'}).subscribe(response => {
            if(response.status === 204){
                ProductsService.productslist = ProductsService.productslist.filter(value => { return value.id !== id } );

                this.products$.next(ProductsService.productslist);
            }

        })

        return this.products$;
    }
}